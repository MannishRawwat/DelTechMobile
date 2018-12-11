package com.rawggar.deltechmobile.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.rawggar.deltechmobile.Activities.ServicesActivity;
import com.rawggar.deltechmobile.Adapters.ServicesDialogAdapter;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.Models.ServicePerson;
import com.rawggar.deltechmobile.R;

import java.util.ArrayList;
import java.util.List;

public class ServicesDialogFragment extends DialogFragment{

    private RecyclerView recyclerView;
    private ServicesDialogAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_services, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        final ArrayList<ServicePerson> servicePersonList= bundle.getParcelableArrayList("ServicePersonList");

        TextView serviceTitle = (TextView) view.findViewById(R.id.service_title);
        serviceTitle.setText(title);

        recyclerView = (RecyclerView) view.findViewById(R.id.service_dialog_recycler_view);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new ServicesDialogAdapter(getActivity(), servicePersonList, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Log.d("MAnish","inside function ");

                callIntent.setData(Uri.parse("tel:"+servicePersonList.get(position).getContact()));

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
