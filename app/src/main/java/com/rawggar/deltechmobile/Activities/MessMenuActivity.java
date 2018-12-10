package com.rawggar.deltechmobile.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.Adapters.MessDayAdapter;
import com.rawggar.deltechmobile.Fragments.MessMenuDialogFragment;
import com.rawggar.deltechmobile.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessMenuActivity extends AppCompatActivity {

    private List<String> days = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessDayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_back);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Mess");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.day_recycler_view);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        days = Arrays.asList(getResources().getStringArray(R.array.days_of_week));
        mAdapter = new MessDayAdapter(this, days, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Toast.makeText(getApplicationContext(),"HELLO "+position,Toast.LENGTH_SHORT).show();
//                Log.d("Rishabh","HELLO");
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                MessMenuDialogFragment dialog = new MessMenuDialogFragment();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"MyCustomDialog");

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

}
