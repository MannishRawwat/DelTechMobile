package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.CalendarEventModel;
import com.rawggar.deltechmobile.R;

import java.util.List;

public class CalendarEventsAdapter extends RecyclerView.Adapter<CalendarEventsAdapter.MyViewHolder> {
    private List<CalendarEventModel> calendarList;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public CalendarEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.calendar_event_row, viewGroup, false);

        final CalendarEventsAdapter.MyViewHolder mViewHolder = new CalendarEventsAdapter.MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventsAdapter.MyViewHolder myViewHolder, int i) {
        String event = calendarList.get(i).getDesc();
        myViewHolder.eventDesc.setText(event);

        if(!calendarList.get(i).getUrl().equals("")){
            myViewHolder.linkEnabled.setVisibility(View.VISIBLE);
        }
        else{
            myViewHolder.linkEnabled.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventDesc;
        public ImageView linkEnabled;

        public MyViewHolder(View view) {
            super(view);
            eventDesc = (TextView) view.findViewById(R.id.event_desc);
            linkEnabled = (ImageView) view.findViewById(R.id.link_enabled);
        }
    }


    public CalendarEventsAdapter(Context mContext,List<CalendarEventModel> calendarList,CustomClickListener listener) {
        this.calendarList = calendarList;
        this.mContext=mContext;
        this.listener=listener;
    }
}
