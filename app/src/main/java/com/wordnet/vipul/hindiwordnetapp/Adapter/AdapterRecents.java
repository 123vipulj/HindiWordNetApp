package com.wordnet.vipul.hindiwordnetapp.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.RecentDB;
import com.wordnet.vipul.hindiwordnetapp.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class AdapterRecents extends RecyclerView.Adapter<AdapterRecents.MyViewHolder> {

    List<RecentDB> list;
    Context context;

    public AdapterRecents(List<RecentDB> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_single_text,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(list.get(i).recent_word);
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.warning(context, "यह फीचर जल्द आएगा !!!", Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recentWords);
        }
    }
}
