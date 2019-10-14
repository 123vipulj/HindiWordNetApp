package com.wordnet.vipul.hindiwordnetapp.Helper;

import android.text.SpannableString;

public class HelperDict {
    private SpannableString txtNouns,txtGLoss,txtExamples, txtHypernym, txtHyponym,txtHolonym, txtAttribute, txtOntoNode;

    public HelperDict(SpannableString txtNouns, SpannableString txtGLoss, SpannableString txtExamples, SpannableString txtHyponym, SpannableString txtOntoNode) {
        this.txtNouns = txtNouns;
        this.txtGLoss = txtGLoss;
        this.txtExamples = txtExamples;
        this.txtHyponym = txtHyponym;
        this.txtOntoNode = txtOntoNode;
    }

    public HelperDict(SpannableString txtNouns, SpannableString txtGLoss, SpannableString txtExamples, SpannableString txtHypernym, SpannableString txtHyponym, SpannableString txtHolonym, SpannableString txtAttribute, SpannableString txtOntoNode) {
        this.txtNouns = txtNouns;
        this.txtGLoss = txtGLoss;
        this.txtExamples = txtExamples;
        this.txtHypernym = txtHypernym;
        this.txtHyponym = txtHyponym;
        this.txtHolonym = txtHolonym;
        this.txtAttribute = txtAttribute;
        this.txtOntoNode = txtOntoNode;
    }

    public SpannableString getTxtHypernym() {
        return txtHypernym;
    }

    public void setTxtHypernym(SpannableString txtHypernym) {
        this.txtHypernym = txtHypernym;
    }

    public SpannableString getTxtHolonym() {
        return txtHolonym;
    }

    public void setTxtHolonym(SpannableString txtHolonym) {
        this.txtHolonym = txtHolonym;
    }

    public SpannableString getTxtAttribute() {
        return txtAttribute;
    }

    public void setTxtAttribute(SpannableString txtAttribute) {
        this.txtAttribute = txtAttribute;
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
