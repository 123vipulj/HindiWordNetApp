package com.wordnet.vipul.hindiwordnetapp.Helper;

import android.text.SpannableString;

public class HelperDict {
    SpannableString txtNouns,txtGLoss,txtExamples,txtHyponym, txtOntoNode;

    public HelperDict(SpannableString txtNouns, SpannableString txtGLoss, SpannableString txtExamples, SpannableString txtHyponym, SpannableString txtOntoNode) {
        this.txtNouns = txtNouns;
        this.txtGLoss = txtGLoss;
        this.txtExamples = txtExamples;
        this.txtHyponym = txtHyponym;
        this.txtOntoNode = txtOntoNode;
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

    public SpannableString getTxtHyponym() {
        return txtHyponym;
    }

    public void setTxtHyponym(SpannableString txtHyponym) {
        this.txtHyponym = txtHyponym;
    }

    public SpannableString getTxtOntoNode() {
        return txtOntoNode;
    }

    public void setTxtOntoNode(SpannableString txtOntoNode) {
        this.txtOntoNode = txtOntoNode;
    }
}
