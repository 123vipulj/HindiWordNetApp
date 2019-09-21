package com.wordnet.vipul.hindiwordnetapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.AllWordDb;
import com.wordnet.vipul.hindiwordnetapp.R;
import com.wordnet.vipul.hindiwordnetapp.Util.TypeFaceUtil;

import java.util.List;

public class AdapterMarequee extends RecyclerView.Adapter<AdapterMarequee.MyViewHolder> {

    Context context;
    List<AllWordDb> allWordDbs;

    public AdapterMarequee(Context context, List<AllWordDb> allWordDbs) {
        this.context = context;
        this.allWordDbs = allWordDbs;
    }

    @NonNull
    @Override
    public AdapterMarequee.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mar_text,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMarequee.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(allWordDbs.get(i).wordName);
        myViewHolder.textView.setTypeface(TypeFaceUtil.TypeFaceSet(context,"khandregular.ttf"));
    }

    @Override
    public int getItemCount() {
        return allWordDbs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView  = itemView.findViewById(R.id.marText);
        }
    }
}
