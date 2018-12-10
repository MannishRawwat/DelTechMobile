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
import com.rawggar.deltechmobile.Models.ServicePerson;
import com.rawggar.deltechmobile.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesDialogAdapter extends RecyclerView.Adapter<ServicesDialogAdapter.MyViewHolder>{
    private List<ServicePerson> servicePersonList;
    Context mContext;
    CustomClickListener listener;

    @NonNull
    @Override
    public ServicesDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.service_person_row, viewGroup, false);

        final ServicesDialogAdapter.MyViewHolder mViewHolder = new ServicesDialogAdapter.MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesDialogAdapter.MyViewHolder myViewHolder, int i) {
        String personName = servicePersonList.get(i).getName();
        String personContact = servicePersonList.get(i).getContact();
        myViewHolder.Name.setText(personName);
        myViewHolder.Contact.setText(personContact);
    }

    @Override
    public int getItemCount() {
        return servicePersonList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Contact;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.person_name);
            Contact = (TextView) view.findViewById(R.id.person_contact);
        }
    }


    public ServicesDialogAdapter(Context mContext, ArrayList<ServicePerson> servicePersonList, CustomClickListener listener) {
        this.servicePersonList = servicePersonList;
        this.mContext=mContext;
        this.listener=listener;
    }
}

