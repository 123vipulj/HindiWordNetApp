package com.wordnet.vipul.hindiwordnetapp.About;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wordnet.vipul.hindiwordnetapp.R;
import com.wordnet.vipul.hindiwordnetapp.Util.TypeFaceUtil;

public class SupportMe extends AppCompatActivity {

    TextView upiText,supportText;
    Button copyBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_me);

        upiText = findViewById(R.id.upi_txt);
        supportText = findViewById(R.id.support_text_client);
        copyBtn = findViewById(R.id.copyBtn);

        supportText.setTypeface(TypeFaceUtil.TypeFaceSet(this,"kohoregular.ttf"));

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copiUPI", upiText.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(SupportMe.this,"कॉपी हुआ !!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
