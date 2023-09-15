package com.example.assignment1_emailassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfoReadingAdapter extends RecyclerView.Adapter<InfoReadingAdapter.InfoViewHolder> {
    //the context uses this adapter, which here should be the instance of ReadingActivity
    private Context myContext;
    //an Array of Strings that represent the email information
    private String[] info;

    //the constructor
    public InfoReadingAdapter(Context myContext, String[] info){
        this.myContext = myContext;
        this.info = info;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //inflate the 'myContext' with 'layout_emailinfo_item'
        return new InfoViewHolder(LayoutInflater.from(myContext).inflate(R.layout.layout_emailinfo_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(InfoReadingAdapter.InfoViewHolder holder, int position) {
        switch (position){
            case 0:
                setTextForItem(holder, "From: ", info[position]);
                break;
            case 1:
                setTextForItem(holder, "To: ", info[position]);
                break;
            case 2:
                setTextForItem(holder, "cc: ", info[position]);
                break;
            case 3:
                setTextForItem(holder, "Subject: ", info[position]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return info.length;
    }

    private void setTextForItem(InfoViewHolder holder, String label, String data){
        holder.mTvLabel.setText(label);
        holder.mTvData.setText(data);
    }

    //an inner class for declaring and holding the views in this recyclerView
    class InfoViewHolder extends RecyclerView.ViewHolder{

        //the email information
        private TextView mTvLabel;
        private TextView mTvData;

        public InfoViewHolder(View itemView) {
            super(itemView);
            //initialize the email information
            mTvLabel = itemView.findViewById(R.id.tv_label);
            mTvData = itemView.findViewById(R.id.tv_data);
        }
    }
}
