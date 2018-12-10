package com.rawggar.deltechmobile.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rawggar.deltechmobile.R;

public class MessMenuDialogAdapter extends RecyclerView.Adapter<MessMenuDialogAdapter.MyViewHolder> {
    private String[][] messMenuDialog;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mess;
        public TextView item;
        public TextView price;

        public MyViewHolder(View view) {
            super(view);
            mess = (TextView) view.findViewById(R.id.mess_name);
            item = (TextView) view.findViewById(R.id.item_name);
            price = (TextView) view.findViewById(R.id.item_price);
        }
    }

    @NonNull
    @Override
    public MessMenuDialogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mess_menu_row, viewGroup, false);

        final MessMenuDialogAdapter.MyViewHolder mViewHolder = new MessMenuDialogAdapter.MyViewHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessMenuDialogAdapter.MyViewHolder myViewHolder, int i) {
        String messName = messMenuDialog[i][0];
        String itemName = messMenuDialog[i][1];
        String itemPrice = messMenuDialog[i][2];

        myViewHolder.mess.setText(messName);
        myViewHolder.item.setText(itemName);
        myViewHolder.price.setText(itemPrice);
    }

    @Override
    public int getItemCount() {
        return messMenuDialog.length;
    }

    public MessMenuDialogAdapter(Context mContext,String[][] messMenuDialog) {
        this.messMenuDialog = messMenuDialog;
        this.mContext = mContext;
    }
}
