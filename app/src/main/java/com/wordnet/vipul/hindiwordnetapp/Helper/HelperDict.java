package com.wordnet.vipul.hindiwordnetapp.Helper;

import android.text.SpannableString;

public class HelperDict {
    SpannableString txtNouns,txtGLoss,txtExamples;

    public HelperDict(SpannableString txtNouns, SpannableString txtGLoss, SpannableString txtExamples) {
        this.txtNouns = txtNouns;
        this.txtGLoss = txtGLoss;
        this.txtExamples = txtExamples;
    }

    public SpannableString getTxtNouns() {
        return txtNouns;
    }

    public void setTxtNouns(SpannableString txtNouns) {
        this.txtNouns = txtNouns;
    }

    public SpannableString getTxtGLoss() {
        return txtGLoss;
    }

    public void setTxtGLoss(SpannableString txtGLoss) {
        this.txtGLoss = txtGLoss;
    }

    public SpannableString getTxtExamples() {
        return txtExamples;
    }

    public void setTxtExamples(SpannableString txtExamples) {
        this.txtExamples = txtExamples;
    }
}
