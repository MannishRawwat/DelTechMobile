package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.R;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder>{
    private List<ServiceModel> serviceList;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mess_menu_day_row, viewGroup, false);

        final ServicesAdapter.MyViewHolder mViewHolder = new ServicesAdapter.MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.MyViewHolder myViewHolder, int i) {
        String serviceName = serviceList.get(i).getTitle();
        myViewHolder.btn.setText(serviceName);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn;

        public MyViewHolder(View view) {
            super(view);
            btn = (TextView) view.findViewById(R.id.mess_day_button);
        }
    }


    public ServicesAdapter(Context mContext,List<ServiceModel> serviceList,CustomClickListener listener) {
        this.serviceList = serviceList;
        this.mContext=mContext;
        this.listener=listener;
    }
}
