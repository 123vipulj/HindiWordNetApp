package com.wordnet.vipul.hindiwordnetapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordnet.vipul.hindiwordnetapp.DatabaseUtil.AllWordDb;
import com.wordnet.vipul.hindiwordnetapp.R;

import java.io.IOException;
import java.util.List;

public class AdapterSearchedText extends RecyclerView.Adapter<AdapterSearchedText.MyViewHolder> {

    Context context;
    List<AllWordDb> textString;
    List<String> wordDbSelected;
    CustomItemClickListener listener;

    public AdapterSearchedText(Context context, List<AllWordDb> textString) {
        this.context = context;
        this.textString = textString;

    }

    public AdapterSearchedText(Context context, List<AllWordDb> textString, CustomItemClickListener listener) {
        this.context = context;
        this.textString = textString;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seleted_text_lay,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder,  int i) {
        myViewHolder.textView.setText(textString.get(i).wordName);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/lailabold.ttf");
        myViewHolder.textView.setTypeface(typeface);
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onItemClick(v,myViewHolder.getAdapterPosition());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textString.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

    }
}
