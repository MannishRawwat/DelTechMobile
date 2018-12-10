package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.NewsModel;
import com.rawggar.deltechmobile.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private ArrayList<NewsModel> newsList;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_row, viewGroup, false);

        final NewsAdapter.MyViewHolder mViewHolder = new NewsAdapter.MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder myViewHolder, int i) {
        String newsHeadline = newsList.get(i).getTitle();
        myViewHolder.newsHead.setText(newsHeadline);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newsHead;

        public MyViewHolder(View view) {
            super(view);
            newsHead = (TextView) view.findViewById(R.id.news_headline);
        }
    }


    public NewsAdapter(Context mContext,ArrayList<NewsModel> newsList,CustomClickListener listener) {
        this.newsList = newsList;
        this.mContext = mContext;
        this.listener = listener;
    }
}
