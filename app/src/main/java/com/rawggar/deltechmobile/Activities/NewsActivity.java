package com.rawggar.deltechmobile.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.rawggar.deltechmobile.API.NewsAPI;
import com.rawggar.deltechmobile.API.ServiceAPI;
import com.rawggar.deltechmobile.Adapters.NewsAdapter;
import com.rawggar.deltechmobile.Adapters.ServicesAdapter;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Fragments.ServicesDialogFragment;
import com.rawggar.deltechmobile.Models.NewsModel;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

public class NewsActivity extends AppCompatActivity {

    public ArrayList<NewsModel> newsData = new ArrayList<NewsModel>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String newsdata = "newsData";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_back);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        mAdapter = new NewsAdapter(this, newsData, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Do nothing
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<ArrayList<NewsModel>> call = newsAPI.getNews();

        //if internet is not working
        if(!haveNetworkConnection()) {
            //here newsdata will be obtained by shared preferences

            //newsData = Gson convert to object form from shared preferences if not present in
            //shared preferences then do nothing.
            if(sharedpreferences.contains(newsdata)) {
                String json = sharedpreferences.getString(newsdata, "");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<NewsModel>>() {
                }.getType();
                newsData = gson.fromJson(json, listType);

                //Descending order
                Collections.sort(newsData, new Comparator<NewsModel>() {

                    @Override
                    public int compare(NewsModel o1, NewsModel o2) {
                        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                    }
                });

                mAdapter = new NewsAdapter(getApplicationContext(), newsData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        //use the data newsdata which is actually



                        String url = newsData.get(position).getUrl();
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }
        }
        else{
        call.enqueue(new Callback<ArrayList<NewsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsModel>> call, Response<ArrayList<NewsModel>> response) {
                final ArrayList<NewsModel> newsList = response.body();
                newsData = newsList;


                //update shared preferences of newsData and convert it into json and save.
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                String jsonData = gson.toJson(newsData);
                editor.putString(newsdata, jsonData);
                editor.commit();

                // Descending Order
                Collections.sort(newsData, new Comparator<NewsModel>() {

                    @Override
                    public int compare(NewsModel o1, NewsModel o2) {
                        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                    }
                });
                Log.d("NEWS",newsData.toString());
                mAdapter = new NewsAdapter(getApplicationContext(), newsData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String url=newsData.get(position).getUrl();
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<NewsModel>> call, Throwable t) {
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
}
