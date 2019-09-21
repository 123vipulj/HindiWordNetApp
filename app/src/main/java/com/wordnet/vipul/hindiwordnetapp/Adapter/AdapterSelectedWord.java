package com.wordnet.vipul.hindiwordnetapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.Helper.HelperDict;
import com.wordnet.vipul.hindiwordnetapp.R;
import com.wordnet.vipul.hindiwordnetapp.Util.TypeFaceUtil;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AdapterSelectedWord extends RecyclerView.Adapter<AdapterSelectedWord.MyViewHolder> {


    Context context;
    List<HelperDict> helperDicts;

    public AdapterSelectedWord(Context context, List<HelperDict> helperDicts) {
        this.context = context;
        this.helperDicts = helperDicts;
    }

    @NonNull
    @Override
    public AdapterSelectedWord.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(context).inflate(R.layout.result_text,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectedWord.MyViewHolder myViewHolder, int i) {
            myViewHolder.txtNoun.setText(helperDicts.get(i).getTxtNouns());
            myViewHolder.txtGloss.setText(helperDicts.get(i).getTxtGLoss());
            myViewHolder.txtExample.setText("\"" +helperDicts.get(i).getTxtExamples() + "\"" );
            myViewHolder.show_moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toasty.warning(context, "फीचर जल्द आएगा !!!",Toasty.LENGTH_SHORT).show();
                }
            });

            //set fonts
            myViewHolder.txtNoun.setTypeface(TypeFaceUtil.TypeFaceSet(context,"lailabold.ttf"));
            myViewHolder.txtGloss.setTypeface(TypeFaceUtil.TypeFaceSet(context,"lailabold.ttf"));
            myViewHolder.txtExample.setTypeface(TypeFaceUtil.TypeFaceSet(context,"notoitalic.ttf"));
    }

    @Override
    public int getItemCount() {
        return helperDicts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNoun,txtGloss,txtExample;
        Button show_moreBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNoun = itemView.findViewById(R.id.txtNouns);
            txtGloss = itemView.findViewById(R.id.txtGloss);
            txtExample = itemView.findViewById(R.id.txtExamples);
            show_moreBtn = itemView.findViewById(R.id.show_moreBtn);
        }
    }
}
