package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.R;

import java.util.List;

public class MessDayAdapter extends RecyclerView.Adapter<MessDayAdapter.MyViewHolder> {
    private List<String> days;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mess_menu_day_row, viewGroup, false);

        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
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
        String day = days.get(i);
        myViewHolder.btn.setText(day);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn;

        public MyViewHolder(View view) {
            super(view);
            btn = (TextView) view.findViewById(R.id.mess_day_button);
        }
    }


    public MessDayAdapter(Context mContext,List<String> days,CustomClickListener listener) {
        this.days = days;
        this.mContext=mContext;
        this.listener=listener;
    }
}
