package com.wordnet.vipul.hindiwordnetapp;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.wordnet.vipul.hindiwordnetapp.Adapter.AdapterSearchedText;
import com.wordnet.vipul.hindiwordnetapp.Adapter.AdapterSelectedWord;
import com.wordnet.vipul.hindiwordnetapp.Adapter.CustomItemClickListener;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.AllWordDb;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.AllWordDb_Table;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.RecentDB;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.RecentDB_Table;
import com.wordnet.vipul.hindiwordnetapp.Helper.HelperDict;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.ac.iitb.cfilt.jhwnl.JHWNLException;
import in.ac.iitb.cfilt.jhwnl.JHWNLRuntimeException;
import in.ac.iitb.cfilt.jhwnl.data.IndexWord;
import in.ac.iitb.cfilt.jhwnl.data.IndexWordSet;
import in.ac.iitb.cfilt.jhwnl.data.Pointer;
import in.ac.iitb.cfilt.jhwnl.data.PointerType;
import in.ac.iitb.cfilt.jhwnl.data.Synset;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {



    final String zipFilePath = "/data/data/com.wordnet.vipul.hindiwordnetapp/files";

    SearchView searchText;
    RecyclerView recyclerView,recyclerViewRes;

    AllWordDb allWordDb;

    List<AllWordDb> wordDbList;
    List<HelperDict> helperDictList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlowManager.init(this);

        recyclerView = findViewById(R.id.searchedTextResult);
        searchText = findViewById(R.id.searchView);


        searchText.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(wordDbList!=null){
                    wordDbList.clear();
                }
                recyclerView.setAdapter(null);
                return false;
            }
        });

        RxSearchObservable.fromView(searchText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if (s.isEmpty()){
                            return false;
                        }else {
                            return true;
                        }
                    }
                }).distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    public void demonstration(String lemma) throws IOException {
        long[] synsetOffsets;

        try {
            //	 Look up the word for all POS tags

            IndexWordSet demoIWSet = Dictionary.getInstance().lookupAllIndexWords(lemma);
            //	 Note: Use lookupAllMorphedIndexWords() to look up morphed form of the input word for all POS tags
            IndexWord[] demoIndexWord = new IndexWord[demoIWSet.size()];
            demoIndexWord  = demoIWSet.getIndexWordArray();

            //make sure its clear before adapter populate data
            helperDictList.clear();

            int size = demoIndexWord[0].getSenseCount();

            synsetOffsets = demoIndexWord[0].getSynsetOffsets();
            Synset[] synsetArray = demoIndexWord[0].getSenses();


            for ( int k = 0;k < size;k++ ) {
                String nouns = String.valueOf(synsetArray[k]);

                SpannableString finalNouns = highlightString(lemma,nounsExtracter(nouns));
                SpannableString finalGloss = highlightString(lemma,splitGloss(synsetArray[k].getGloss()));
                SpannableString finalExamples = highlightString(lemma,splitExample(synsetArray[k].getGloss()).replace("/","\n"));



                helperDictList.add(new HelperDict(finalNouns,finalGloss,finalExamples));

                recyclerViewRes = findViewById(R.id.resultRecycler);
                AdapterSelectedWord adapterSelectedWord = new AdapterSelectedWord(MainActivity.this,helperDictList);
                recyclerViewRes.setAdapter(adapterSelectedWord);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerViewRes.setLayoutManager(layoutManager);
                adapterSelectedWord.notifyDataSetChanged();

                Pointer[] pointers = synsetArray[k].getPointers();
                System.out.println("Synset Num Pointers:" + pointers.length);

                for (int j = 0; j < pointers.length; j++) {
                    if(pointers[j].getType().equals(PointerType.ONTO_NODES)) {	// For ontology relation
                        System.out.println(pointers[j].getType() + " : "  + Dictionary.getInstance().getOntoSynset(pointers[j].getOntoPointer()).getOntoNodes());
                    } else {
                        System.out.println(pointers[j].getType() + " : "  + pointers[j].getTargetSynset());

                        /*
                        * null pointer happen when hyponym is null whatever the case such type rakam in hindi
                        * */
                       // System.out.println(pointers[j].getType() + " : "  + pointers[j].getTargetSynset().getGloss().replace(":", "\n"));

                    }
                }

            }

        } catch (JHWNLException e) {
            System.err.println("Internal Error raised from API.");
            e.printStackTrace();
        } catch (JHWNLRuntimeException e) {
            e.getMessage();
        }

    }


    public int checkIfWordExist(){
        List<AllWordDb> siteList = SQLite.select().from(AllWordDb.class).where(AllWordDb_Table.id.greaterThan(1)).queryList();
        return siteList.size();
    }

    public void addAllWordsToDb(){
        String[] indexNoun = Dictionary.getInstance().getNounWordList();
        String[] indexVerb = Dictionary.getInstance().getVerbWordList();
        String[] indexAdj = Dictionary.getInstance().getAdjWordList();
        String[] indexAdv = Dictionary.getInstance().getAdvWordList();

        for (int i=0;i<indexNoun.length;i++){
            allWordDb = new AllWordDb();
            allWordDb.wordName = indexNoun[i];
            Log.d("noun ::",i + ":: " +indexNoun[i] + "");
            allWordDb.save();
        }

        Log.d("Verb ::","finished");
        for (int i=0;i<indexVerb.length;i++){
            allWordDb = new AllWordDb();
            allWordDb.wordName = indexVerb[i];
            Log.d("Verb ::",i + ":: " +indexVerb[i] + "");
            allWordDb.save();
        }
        Log.d("Verb ::","finished");
        for (int i=0;i<indexAdj.length;i++){
            allWordDb = new AllWordDb();
            allWordDb.wordName = indexAdj[i];
            Log.d("adj ::",i + ":: " +indexAdj[i] + "");
            allWordDb.save();
        }
        Log.d("Verb ::","finished");
        for (int i=0;i<indexAdv.length;i++){
            allWordDb = new AllWordDb();
            allWordDb.wordName = indexAdv[i];
            Log.d("adv ::",i + ":: " +indexAdv[i] + "");
            allWordDb.save();
        }
        Log.d("Verb ::","finished");
    }

    private void hideKeyboardSearch() {
        if (searchText == null) return;

        InputMethodManager imm = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        searchText.clearFocus();
    }

    private void showKeyboardSearch() {
        if (searchText == null) return;

        if (searchText.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public String nounsExtracter(String nouns){
        int firstSubStrings = nouns.indexOf("[");
        int secondSubStrings = nouns.indexOf("]");
        String getSubString = nouns.substring(firstSubStrings+1,secondSubStrings-1);
        String tmp = getSubString;
        return tmp;
    }

    public String splitGloss(String gloss){
        int firstStringpos  = gloss.indexOf(":");
        String tmp = gloss.substring(0,firstStringpos );
        return tmp;
    }

    public String splitExample(String example){
        int firstStringpos  = example.indexOf(":");
        String tmp = example.substring(firstStringpos+2,example.length()-1);
        return tmp;
    }

    private SpannableString highlightString(String input,String orgStr) {
        //Get the text from text view and create a spannable string
        SpannableString spannableString = new SpannableString(orgStr);
        //Get the previous spans and remove them
//        BackgroundColorSpan[] backgroundSpans = spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);
//
//        for (BackgroundColorSpan span: backgroundSpans) {
//            spannableString.removeSpan(span);
//        }

        //Search for all occurrences of the keyword in the string
        int indexOfKeyword = spannableString.toString().indexOf(input);

        while (indexOfKeyword != -1) {
            //Create a background color span on the keyword
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfKeyword, indexOfKeyword + input.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //Get the next index of the keyword
            indexOfKeyword = spannableString.toString().indexOf(input, indexOfKeyword + input.length());
        }
        return spannableString;
        }

    public Observer<String> getObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                wordDbList = SQLite.select(AllWordDb_Table.wordName)
                        .from(AllWordDb.class)
                        .where(AllWordDb_Table.wordName.like(s+"%"))
                        .limit(15)
                        .queryList();

                        AdapterSearchedText adapterSearchedText = new AdapterSearchedText(MainActivity.this, wordDbList, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) throws IOException {

                        SQLite.insert(RecentDB.class)
                                .columns(RecentDB_Table.recent_word)
                                .values(wordDbList.get(pos).wordName)
                                .execute();

                        demonstration(wordDbList.get(pos).wordName);
                        searchText.setQueryHint(wordDbList.get(pos).wordName);
                        wordDbList.clear();

                        hideKeyboardSearch();

                        AdapterSearchedText adapterSearchedText1 = new AdapterSearchedText(MainActivity.this,wordDbList);
                        recyclerView.setLayoutManager(null);
                        recyclerView.setAdapter(adapterSearchedText1);

                    }
                });


                    recyclerView.setAdapter(adapterSearchedText);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                    recyclerView.setLayoutManager(layoutManager);
                    adapterSearchedText.notifyDataSetChanged();


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        };
    }


}
