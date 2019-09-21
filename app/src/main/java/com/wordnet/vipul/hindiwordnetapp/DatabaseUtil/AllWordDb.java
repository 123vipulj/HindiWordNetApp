package com.wordnet.vipul.hindiwordnetapp.DatabaseUtil;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class AllWordDb extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String wordName;
}
