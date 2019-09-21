package com.wordnet.vipul.hindiwordnetapp.About;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.R;
import com.wordnet.vipul.hindiwordnetapp.Util.TypeFaceUtil;

public class MoreInformation extends AppCompatActivity {

    TextView textView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.licence);

        textView = findViewById(R.id.textView2);
        String styledText = getResources().getString(R.string.hello_worldRed);
        textView.setText(Html.fromHtml(styledText));
        textView.setTypeface( TypeFaceUtil.TypeFaceSet(this,"kohoregular.ttf"));
    }
}
