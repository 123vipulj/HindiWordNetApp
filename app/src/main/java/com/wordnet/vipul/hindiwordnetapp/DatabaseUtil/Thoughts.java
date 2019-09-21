package com.wordnet.vipul.hindiwordnetapp.DatabaseUtil;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = ThoughtDatabase.class)
public class Thoughts extends BaseModel {

    @PrimaryKey
    int id;

    @Column
    public String thought_things;

}
