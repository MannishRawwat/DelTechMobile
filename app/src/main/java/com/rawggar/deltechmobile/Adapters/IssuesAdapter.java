package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.IssuesModel;
import com.rawggar.deltechmobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.MyViewHolder> {
    private ArrayList<IssuesModel> issuesList;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public IssuesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.issues_row, viewGroup, false);

        final IssuesAdapter.MyViewHolder mViewHolder = new IssuesAdapter.MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String issuesDescription = issuesList.get(i).getDesc();
        myViewHolder.issuesDesc.setText(issuesDescription);
        String issuesUrl = issuesList.get(i).getUrl();
        Date tempDate = issuesList.get(i).getCreatedAt();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(tempDate);
        String issuesPosted = date.toString();
        myViewHolder.issuesPostDate.setText((issuesPosted));
        if(issuesList.get(i).getResolved()){
            myViewHolder.issuesResolve.setVisibility(View.VISIBLE);
        }
        else
        {
            myViewHolder.issuesResolve.setVisibility(View.INVISIBLE);
        }
        //Log.d("ISSUE", issuesUrl);


        if(issuesUrl != null && !issuesUrl.equals("")) {
            //Glide.with(mContext).clear(myViewHolder.issuesImage);
            //myViewHolder.issuesImage.setImageDrawable(null);
            Glide.with(mContext).load(issuesUrl).apply(new RequestOptions()
                    .placeholder(R.drawable.ic_image_loading_colour).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()).into(myViewHolder.issuesImage);
            Log.d("ISSUE1", issuesUrl);
        }
        else{

            Glide.with(mContext).clear(myViewHolder.issuesImage);
            // remove the placeholder (optional); read comments below
            myViewHolder.issuesImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_image_unavailable));
            Log.d("ISSUE", "no");

        }

    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView issuesDesc;
        public TextView issuesPostDate;
        public ImageView issuesImage;
        public View issuesResolve;

        public MyViewHolder(View view) {
            super(view);
            issuesDesc =  view.findViewById(R.id.issues_desc);
            issuesImage = view.findViewById(R.id.issues_image);
            issuesResolve = view.findViewById(R.id.issues_resolved);
            issuesPostDate = view.findViewById(R.id.issues_post_date);
        }
    }


    public IssuesAdapter(Context mContext,ArrayList<IssuesModel> issuesList,CustomClickListener listener) {
        this.issuesList = issuesList;
        this.mContext = mContext;
        this.listener = listener;
    }
}
