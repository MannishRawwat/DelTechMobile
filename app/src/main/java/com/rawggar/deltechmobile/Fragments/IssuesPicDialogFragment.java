package com.rawggar.deltechmobile.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rawggar.deltechmobile.R;

public class IssuesPicDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_issues_pic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getArguments();
        String picUrl = bundle.getString("pic_url");
        String desc = bundle.getString("desc");

        ImageView ivPic = view.findViewById(R.id.large_picture);
        TextView tvDesc = view.findViewById(R.id.large_issues_desc);

        if(picUrl.equals("")){
            ivPic.setVisibility(View.GONE);
        }
        else {

            Log.d("Picurl", picUrl);
            Glide.with(this.getActivity()).load(picUrl).apply(new RequestOptions()
                    .placeholder(R.drawable.ic_image_placeholder)
                    .fitCenter()).into(ivPic);
        }

        tvDesc.setText(desc);
    }
}
