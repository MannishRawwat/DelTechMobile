package com.rawggar.deltechmobile.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rawggar.deltechmobile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Home");

    }

    public void ShareApp(View v){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"DelTech Mobile");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey! I found this great app for DTU students, " +
                "Here's where you can download it: https://play.google.com/store/apps/details?id=com.rawggar.deltechmobile");
        startActivity(Intent.createChooser(shareIntent,"Share Via"));
    }

    public void OpenWebsite(View v){
        String url="http://www.dtu.ac.in";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void CalendarClick(View v){
        ImageButton btn = (ImageButton)v;
        Intent i = new Intent(this,CalendarActivity.class);
        startActivity(i);
    }

    public void IssuesClick(View v){
        Intent i = new Intent(this, IssuesActivity.class);
        startActivity(i);
    }

    public void NewsClick(View v){
        ImageButton btn = (ImageButton)v;
        Intent i = new Intent(this,NewsActivity.class);
        startActivity(i);

    }
    public void MenuClick(View v){
        ImageButton btn = (ImageButton)v;
        Intent i = new Intent(this,MessMenuActivity.class);
        startActivity(i);

    }
    public void ServicesClick(View v){
        ImageButton btn = (ImageButton)v;
        Intent i = new Intent(this,ServicesActivity.class);
        startActivity(i);

    }
}
