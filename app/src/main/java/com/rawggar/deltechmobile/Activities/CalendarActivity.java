package com.rawggar.deltechmobile.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NavUtils;
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

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rawggar.deltechmobile.API.CalendarAPI;
import com.rawggar.deltechmobile.API.ServiceAPI;
import com.rawggar.deltechmobile.Adapters.CalendarEventsAdapter;
import com.rawggar.deltechmobile.Adapters.NewsAdapter;
import com.rawggar.deltechmobile.Adapters.ServicesAdapter;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Fragments.ServicesDialogFragment;
import com.rawggar.deltechmobile.Models.CalendarEventModel;
import com.rawggar.deltechmobile.Models.CalendarModel;
import com.rawggar.deltechmobile.Models.NewsModel;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rawggar.deltechmobile.Constants.BASE_URL;

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    TextView monthYear;
    private List<CalendarEventModel> calendarEventData = new ArrayList<CalendarEventModel>();
    private List<CalendarModel> calendarData = new ArrayList<CalendarModel>();
    private RecyclerView recyclerView;
    private CalendarEventsAdapter mAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String calendardata = "calendarData";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_back);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Calendar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        monthYear = (TextView) findViewById(R.id.month_year);

        recyclerView = (RecyclerView) findViewById(R.id.calendar_events_recycler_view);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAdapter = new CalendarEventsAdapter(this, calendarEventData, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Do nothing
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CalendarAPI calendarAPI = retrofit.create(CalendarAPI.class);
        Call<List<CalendarModel>> call = calendarAPI.getCalendar();

        //if internet is not working
        if(!haveNetworkConnection()) {
            //here newsdata will be obtained by shared preferences

            //newsData = Gson convert to object form from shared preferences if not present in
            //shared preferences then do nothing.
            if(sharedpreferences.contains(calendardata)) {
                String json = sharedpreferences.getString(calendardata, "");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CalendarModel>>() {
                }.getType();
                calendarData = gson.fromJson(json, listType);

                //Set an event for Holiday.
                for(int i=0;i<calendarData.size();i++){
                    Calendar c = Calendar.getInstance();
                    c.set(calendarData.get(i).getYear(),
                            calendarData.get(i).getMonth()-1,
                            calendarData.get(i).getDay());
                    Log.d("Calendar",c.getTimeInMillis()+"");
                    if(calendarData.get(i).isHoliday()) {


                        Event ev1 = new Event(Color.rgb(140,22,22), c.getTimeInMillis(), "Event Name");
                        compactCalendarView.addEvent(ev1);
                    }
                    else{
                        Event ev1 = new Event(Color.LTGRAY,  c.getTimeInMillis(), "Event Name");
                        compactCalendarView.addEvent(ev1);
                    }
                }

            }

            calendarEventData = new ArrayList<CalendarEventModel>();

            Calendar date = Calendar.getInstance();
            Log.d("Calendar",""+date.get(Calendar.DAY_OF_MONTH)+" "+date.get(Calendar.MONTH)+" "+date.get(Calendar.YEAR));
            for(int i=0;i<calendarData.size();i++){
                if(calendarData.get(i).getDay()==(date.get(Calendar.DAY_OF_MONTH))
                        &&calendarData.get(i).getMonth()==(date.get(Calendar.MONTH)+1)
                        &&calendarData.get(i).getYear()==(date.get(Calendar.YEAR))){
                    calendarEventData = calendarData.get(i).getList();
                    Log.d("Calendar","successful");
                    break;
                }
            }

            if(calendarEventData.size()!=0)
                Log.d("Calendar",calendarEventData.get(0).getDesc());
            mAdapter = new CalendarEventsAdapter(getApplicationContext(), calendarEventData, new CustomClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    String url=calendarEventData.get(position).getUrl();
                    if(!url.equals("")) {
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                    else{}
                }
            });

            recyclerView.setAdapter(mAdapter);

        }
        else{
        call.enqueue(new Callback<List<CalendarModel>>() {
            @Override
            public void onResponse(Call<List<CalendarModel>> call, Response<List<CalendarModel>> response) {
                final List<CalendarModel> calendarList = response.body();
                calendarData = calendarList;


                //update shared preferences of newsData and convert it into json and save.
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                String jsonData = gson.toJson(calendarData);
                editor.putString(calendardata, jsonData);
                editor.commit();

                //Set an event for Holiday.
                for(int i=0;i<calendarData.size();i++){
                    Calendar c = Calendar.getInstance();
                    c.set(calendarData.get(i).getYear(),
                            calendarData.get(i).getMonth()-1,
                            calendarData.get(i).getDay());
                    Log.d("Calendar",c.getTimeInMillis()+"");
                    if(calendarData.get(i).isHoliday()) {


                        Event ev1 = new Event(Color.rgb(140,22,22), c.getTimeInMillis(), "Event Name");
                        compactCalendarView.addEvent(ev1);
                    }
                    else{
                        Event ev1 = new Event(Color.LTGRAY,  c.getTimeInMillis(), "Event Name");
                        compactCalendarView.addEvent(ev1);
                    }
                }

                calendarEventData = new ArrayList<CalendarEventModel>();

                Calendar date = Calendar.getInstance();
                Log.d("Calendar",""+date.get(Calendar.DAY_OF_MONTH)+" "+date.get(Calendar.MONTH)+" "+date.get(Calendar.YEAR));
                for(int i=0;i<calendarData.size();i++){
                    if(calendarData.get(i).getDay()==date.get(Calendar.DAY_OF_MONTH)
                            &&calendarData.get(i).getMonth()==(date.get(Calendar.MONTH)+1)
                            &&calendarData.get(i).getYear()==(date.get(Calendar.YEAR))){
                        calendarEventData = calendarData.get(i).getList();
                        Log.d("Calendar","successful");
                        break;
                    }
                }

                if(calendarEventData.size()!=0)
                    Log.d("Calendar",calendarEventData.get(0).getDesc());
                mAdapter = new CalendarEventsAdapter(getApplicationContext(), calendarEventData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String url=calendarEventData.get(position).getUrl();
                        if(!url.equals("")) {
                            if (!url.startsWith("http://") && !url.startsWith("https://"))
                                url = "http://" + url;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                        else{}
                    }
                });

                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<CalendarModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Problem with Network",Toast.LENGTH_SHORT);
            }
        });}


        monthYear.setText(dateFormatMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        //Clicklistener
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                calendarEventData = new ArrayList<CalendarEventModel>();

                for(int i=0;i<calendarData.size();i++){
                    if(calendarData.get(i).getDay()==(dateClicked.getDate())
                            &&calendarData.get(i).getMonth()==(dateClicked.getMonth()+1)
                            &&calendarData.get(i).getYear()==(dateClicked.getYear()+1900)){
                        calendarEventData = calendarData.get(i).getList();
                        Log.d("Calendar","successful");
                        break;
                    }
                }

                if(calendarEventData.size()!=0)
                Log.d("Calendar",calendarEventData.get(0).getDesc());

                mAdapter = new CalendarEventsAdapter(getApplicationContext(), calendarEventData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String url=calendarEventData.get(position).getUrl();
                        if(!url.equals("")) {
                            if (!url.startsWith("http://") && !url.startsWith("https://"))
                                url = "http://" + url;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                        else{}
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                monthYear.setText(dateFormatMonth.format(firstDayOfNewMonth));
                calendarEventData = new ArrayList<CalendarEventModel>();

                for(int i=0;i<calendarData.size();i++){
                    if(calendarData.get(i).getDay()==(firstDayOfNewMonth.getDate())
                            &&calendarData.get(i).getMonth()==((firstDayOfNewMonth.getMonth()+1))
                            &&calendarData.get(i).getYear()==((firstDayOfNewMonth.getYear()+1900))){
                        calendarEventData = calendarData.get(i).getList();
                        Log.d("Calendar","successful");
                        break;
                    }
                }

                if(calendarEventData.size()!=0)
                    Log.d("Calendar",calendarEventData.get(0).getDesc());

                mAdapter = new CalendarEventsAdapter(getApplicationContext(), calendarEventData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String url=calendarEventData.get(position).getUrl();
                        if(!url.equals("")) {
                            if (!url.startsWith("http://") && !url.startsWith("https://"))
                                url = "http://" + url;
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                        else{}
                    }
                });
                recyclerView.setAdapter(mAdapter);

            }
        });

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
