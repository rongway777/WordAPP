package com.example.wordapp;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<WordEntity> wordsList  = new ArrayList<>();

    public void setWordsList(List<WordEntity> wordsList){
        this.wordsList = wordsList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemvView = layoutInflater.inflate(R.layout.item_nomal,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(itemvView);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.count%2 == 0){
                    myViewHolder.textViewChinesemean.setVisibility(View.VISIBLE);
                    myViewHolder.count++;
                }else{
                    myViewHolder.textViewChinesemean.setVisibility(View.GONE);
                    myViewHolder.count++;
                }
            }
        });

        myViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + myViewHolder.textViewWord.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                myViewHolder.itemView.getContext().startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewWord.setText(wordsList.get(position).getWord());
        holder.textViewNumber.setText(String.valueOf(position+1));
        holder.textViewChinesemean.setText(wordsList.get(position).getChineseMeaning());
        holder.textViewChinesemean.setVisibility(View.GONE);
        holder.count = 0;
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNumber,textViewWord,textViewChinesemean;
        ImageButton imageButton;
        int count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewChinesemean = itemView.findViewById(R.id.textViewChineseMean);
            imageButton = itemView.findViewById(R.id.imageButton);
            count = 0;
        }
    }
}
