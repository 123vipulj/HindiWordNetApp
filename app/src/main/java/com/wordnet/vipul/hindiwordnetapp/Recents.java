package com.wordnet.vipul.hindiwordnetapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.wordnet.vipul.hindiwordnetapp.Adapter.AdapterRecents;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.RecentDB;
import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.RecentDB_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class Recents extends AppCompatActivity {

    RecyclerView recyclerViewRecents;
    AdapterRecents adapterRecents;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recents_word);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("हाल ही में खोजे गए");
        toolbar.setTitleTextColor(Color.YELLOW);
        setSupportActionBar(toolbar);

        List<RecentDB> wordDbList = SQLite.select(RecentDB_Table.recent_word).from(RecentDB.class).queryList();

        adapterRecents = new AdapterRecents(wordDbList);
        recyclerViewRecents = findViewById(R.id.recentRecyler);
        recyclerViewRecents.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecents.setAdapter(adapterRecents);

    }
}
