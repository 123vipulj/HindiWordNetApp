package com.wordnet.vipul.hindiwordnetapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.About.MoreInformation;
import com.wordnet.vipul.hindiwordnetapp.About.SupportMe;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.Thoughts;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.Thoughts_Table;
import com.wordnet.vipul.hindiwordnetapp.Util.TypeFaceUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import in.ac.iitb.cfilt.jhwnl.JHWNL;
import in.ac.iitb.cfilt.jhwnl.dictionary.Dictionary;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {
    final String zipFilePath = "/data/data/com.wordnet.vipul.hindiwordnetapp/files";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_MEMORY = 101;


    Button searchClickBtn, recentBtn, feedBackBtn, supportBTn, moreInfoBtn;
    TextView thought_txt;

    Toolbar toolbar;

    List<Thoughts> thoughtDBS;

    int count =1;

    Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        FlowManager.init(this);

        Log.d("pacakgeNNAme: ",this.getPackageName() + "");
        thoughtDBS = SQLite.select(Thoughts_Table.thought_things).from(Thoughts.class).queryList();
        int randInt = random.nextInt(thoughtDBS.size());

        searchClickBtn = findViewById(R.id.clickHomeBtn);
        recentBtn      = findViewById(R.id.recentBtn);
        feedBackBtn    = findViewById(R.id.for_error);
        moreInfoBtn    = findViewById(R.id.more_info);
        supportBTn     = findViewById(R.id.support_info);
        thought_txt    = findViewById(R.id.thought_text);

        askPermisssion();

        thought_txt.setText("\""+thoughtDBS.get(randInt).thought_things+ "\"");
        thought_txt.setTypeface(TypeFaceUtil.TypeFaceSet(this,"khandbold.ttf"));

        thought_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (count < thoughtDBS.size()){
                        thought_txt.setText("\""+thoughtDBS.get(random.nextInt(count)).thought_things+ "\"");

                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,01);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float value = (float) animation.getAnimatedValue();

                                thought_txt.setAlpha(value);
                            }
                        });
                        valueAnimator.setInterpolator(new LinearInterpolator());
                        valueAnimator.setDuration(300L);
                        valueAnimator.start();
                        count++;
                    }
            }
        });





        recentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Recents.class);
                startActivity(intent);
            }
        });

        moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoreInformation.class);
                startActivity(intent);
            }
        });


        supportBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SupportMe.class);
                startActivity(intent);
            }
        });

        searchClickBtn.setClickable(false);
        searchClickBtn.setEnabled(false);

        searchClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"123.vipulj@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "प्रतिक्रिया");
                intent.putExtra(Intent.EXTRA_TEXT, "एप्प से सम्बंधित खामियां या  नए फीचर के अनुरोध बारे में लिखे");
                startActivity(Intent.createChooser(intent, ""));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_MEMORY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Completable.fromRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Decompress.unzipFromAssets(HomeActivity.this, "datab.zip", zipFilePath);
                        }
                    }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            //initialize neccessary serializable things
                           // Toast.makeText(HomeActivity.this, "कृपया १०  सेकंड इंतजार करें  !!!", Toast.LENGTH_SHORT).show();
                            Toasty.error(HomeActivity.this,"कृपया १०  सेकंड इंतजार करें  !!!",Toasty.LENGTH_LONG).show();
                            Completable.fromRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    JHWNL.initialize(zipFilePath + "/config/HindiWN.properties");
                                    Dictionary.getInstance();
                                }
                            }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                                @Override
                                public void run() throws Exception {
                                    searchClickBtn.setClickable(true);
                                    searchClickBtn.setEnabled(true);
                                   // Toast.makeText(HomeActivity.this, "शब्दकोश इस्तेमाल करने के लिए तैयार है !!!", Toast.LENGTH_SHORT).show();
                                    Toasty.success(HomeActivity.this,"शब्दकोश इस्तेमाल करने के लिए तैयार है !!!",Toasty.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }

    public void askPermisssion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("अनुमति की आवश्यकता है !!!");
                alertBuilder.setMessage("डेटाबेस को एक्सटर्नल मेमोरी में रखने के लिए अनुमति की आवश्यकता है। कृपया अनुमति दे। ");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_MEMORY);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("अनुमति की आवश्यकता है !!!");
                alertBuilder.setMessage("डेटाबेस को एक्सटर्नल मेमोरी में रखने के लिए अनुमति की आवश्यकता है। कृपया अनुमति दे। ");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_MEMORY);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
                // No explanation needed; request the permission
                // MY_PERMISSIONS_REQUEST_WRITE_MEMORY is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            Toasty.error(HomeActivity.this,"कृपया १०  सेकंड इंतजार करें  !!!",Toasty.LENGTH_LONG).show();
            Completable.fromRunnable(new Runnable() {
                @Override
                public void run() {
                    JHWNL.initialize(zipFilePath + "/config/HindiWN.properties");
                    Dictionary.getInstance();
                }
            }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() {
                @Override
                public void run() throws Exception {
                    searchClickBtn.setClickable(true);
                    searchClickBtn.setEnabled(true);
                    // Toast.makeText(HomeActivity.this, "शब्दकोश इस्तेमाल करने के लिए तैयार है !!!", Toast.LENGTH_SHORT).show();
                    Toasty.success(HomeActivity.this,"शब्दकोश इस्तेमाल करने के लिए तैयार है !!!",Toasty.LENGTH_SHORT).show();
                }
            });
        }

    }
}




