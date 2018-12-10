package com.rawggar.deltechmobile.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rawggar.deltechmobile.Adapters.NewsAdapter;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Models.NewsModel;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.R;
import com.rawggar.deltechmobile.API.ServiceAPI;
import com.rawggar.deltechmobile.Adapters.ServicesAdapter;
import com.rawggar.deltechmobile.Fragments.ServicesDialogFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rawggar.deltechmobile.Constants.BASE_URL;

public class ServicesActivity extends AppCompatActivity {

    private List<ServiceModel> serviceData = new ArrayList<ServiceModel>();
    private RecyclerView recyclerView;
    private ServicesAdapter mAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String servicedata = "serviceData";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_back);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.services_recycler_view);
        //recyclerView.setHasFixedSize(true);
        AskPermissions();


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAdapter = new ServicesAdapter(this, serviceData, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Do nothing
            }
        });




        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<ServiceModel>> call = serviceAPI.getServices();


        //if internet is not working
        if(!haveNetworkConnection()) {
            //here newsdata will be obtained by shared preferences

            //newsData = Gson convert to object form from shared preferences if not present in
            //shared preferences then do nothing.
            if(sharedpreferences.contains(servicedata)) {
                String json = sharedpreferences.getString(servicedata, "");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ServiceModel>>() {
                }.getType();
                serviceData = gson.fromJson(json, listType);

                mAdapter = new ServicesAdapter(getApplicationContext(), serviceData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", serviceData.get(position).getTitle());
                        bundle.putParcelableArrayList("ServicePersonList",serviceData.get(position).getList());
                        ServicesDialogFragment dialog = new ServicesDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"MyCustomDialogService");

                    }
                });
                recyclerView.setAdapter(mAdapter);

            }
        }
        else{
        call.enqueue(new Callback<List<ServiceModel>>() {
            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {
                final List<ServiceModel> serviceList = response.body();
                serviceData = serviceList;
                Log.d("service",serviceData.toString());

                //update shared preferences of newsData and convert it into json and save.
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                String jsonData = gson.toJson(serviceData);
                editor.putString(servicedata, jsonData);
                editor.commit();

                mAdapter = new ServicesAdapter(getApplicationContext(), serviceData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", serviceData.get(position).getTitle());
                        bundle.putParcelableArrayList("ServicePersonList",serviceData.get(position).getList());
                        ServicesDialogFragment dialog = new ServicesDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"MyCustomDialogService");

                    }
                });
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Problem with Network",Toast.LENGTH_SHORT);
            }
        });}



        recyclerView.setAdapter(mAdapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void PoliceClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:100"));

        if (ActivityCompat.checkSelfPermission(ServicesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void FireClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:101"));

        if (ActivityCompat.checkSelfPermission(ServicesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void AmbulanceClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:102"));

        if (ActivityCompat.checkSelfPermission(ServicesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void WomenHelpClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:1091"));

        if (ActivityCompat.checkSelfPermission(ServicesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void StalkClick(View v){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:1091"));

        if (ActivityCompat.checkSelfPermission(ServicesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void AskPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        3);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }
}
