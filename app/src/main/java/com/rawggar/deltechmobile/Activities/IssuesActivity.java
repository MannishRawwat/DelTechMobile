package com.rawggar.deltechmobile.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rawggar.deltechmobile.MyBounceInterpolator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rawggar.deltechmobile.API.ImgurAPI;
import com.rawggar.deltechmobile.API.IssuesAPI;
import com.rawggar.deltechmobile.API.ServiceAPI;
import com.rawggar.deltechmobile.Adapters.IssuesAdapter;
import com.rawggar.deltechmobile.Adapters.ServicesAdapter;
import com.rawggar.deltechmobile.CustomClickListener;
import com.rawggar.deltechmobile.DocumentHelper;
import com.rawggar.deltechmobile.Fragments.IssuesPicDialogFragment;
import com.rawggar.deltechmobile.Fragments.MessMenuDialogFragment;
import com.rawggar.deltechmobile.Fragments.ServicesDialogFragment;
import com.rawggar.deltechmobile.Models.ImageModel;
import com.rawggar.deltechmobile.Models.IssuesModel;
import com.rawggar.deltechmobile.Models.IssuesModel;
import com.rawggar.deltechmobile.Models.ServiceModel;
import com.rawggar.deltechmobile.R;
import com.rawggar.deltechmobile.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rawggar.deltechmobile.Constants.BASE_URL;

public class IssuesActivity extends AppCompatActivity {

    public ArrayList<IssuesModel> issuesData = new ArrayList<IssuesModel>();
    private RecyclerView recyclerView;
    private IssuesAdapter mAdapter;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private int semaphore = 0;

    private File chosenFile;
    private Uri returnUri;
    private Bitmap bitmap;
    String imgurUrl="";

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String issuesdata = "issuesData";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_back);
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.tvTitle)).setText("Issues");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AskPermissions();
        recyclerView = (RecyclerView) findViewById(R.id.issues_recycler_view);
        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAdapter = new IssuesAdapter(this, issuesData, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Do nothing
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IssuesAPI issuesAPI = retrofit.create(IssuesAPI.class);
        Call<ArrayList<IssuesModel>> call = issuesAPI.getIssues();

        //if internet is not working
        if(!haveNetworkConnection()) {
            //here issuesdata will be obtained by shared preferences

            //issuesData = Gson convert to object form from shared preferences if not present in
            //shared preferences then do nothing.
            if(sharedpreferences.contains(issuesdata)) {
                String json = sharedpreferences.getString(issuesdata, "");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<IssuesModel>>() {
                }.getType();
                issuesData = gson.fromJson(json, listType);

                // Descending Order
                Collections.sort(issuesData, new Comparator<IssuesModel>() {

                    @Override
                    public int compare(IssuesModel o1, IssuesModel o2) {

                        if(o1.getResolved() && !o2.getResolved()){
                                return 1;
                        }
                        else if(!o1.getResolved() && o2.getResolved()){
                             return -1;
                        }

                        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                    }
                });

                mAdapter = new IssuesAdapter(getApplicationContext(), issuesData, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        //just show the enlarged picture.
                        Bundle bundle = new Bundle();
                        bundle.putString("pic_url",issuesData.get(position).getUrl());
                        bundle.putString("desc", issuesData.get(position).getDesc());
                        IssuesPicDialogFragment dialog = new IssuesPicDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(),"MyCustomDialog");
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }
        }
        else{
            call.enqueue(new Callback<ArrayList<IssuesModel>>() {
                @Override
                public void onResponse(Call<ArrayList<IssuesModel>> call, Response<ArrayList<IssuesModel>> response) {
                    final ArrayList<IssuesModel> issuesList = response.body();
                    issuesData = issuesList;
                    // Descending Order
                    Collections.sort(issuesData, new Comparator<IssuesModel>() {

                        @Override
                        public int compare(IssuesModel o1, IssuesModel o2) {

                            if(o1.getResolved() && !o2.getResolved()){
                                return 1;
                            }
                            else if(!o1.getResolved() && o2.getResolved()){
                                return -1;
                            }

                            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                        }
                    });

                    //update shared preferences of issuesData and convert it into json and save.
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(issuesData);
                    editor.putString(issuesdata, jsonData);
                    editor.commit();

                    for(int i = 0; i<issuesData.size();i++){
                        Log.d("Check",issuesData.get(i).getUrl());
                    }

                    Log.d("Issues",issuesData.toString());

                    if(response.isSuccessful()){
                        Log.d("Issues",issuesData.toString());
                    }

                    mAdapter = new IssuesAdapter(getApplicationContext(), issuesData, new CustomClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            //show enlarged image
                            Bundle bundle = new Bundle();
                            bundle.putString("pic_url",issuesData.get(position).getUrl());
                            bundle.putString("desc", issuesData.get(position).getDesc());
                            IssuesPicDialogFragment dialog = new IssuesPicDialogFragment();
                            dialog.setArguments(bundle);
                            dialog.show(getSupportFragmentManager(),"MyCustomDialog");
                        }
                    });
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<ArrayList<IssuesModel>> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(IssuesActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        // logging probably not necessary
                    }
                    else {
                        Toast.makeText(IssuesActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                        // todo log to some central bug tracking service
                    }
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

    public void UploadPost(View v){

        if(semaphore!=0){
            return;
        }

        Log.d("Manish","Inside Upload Post");
        ImgurAPI imgurAPI = ImgurAPI.retrofit.create(ImgurAPI.class);

        final EditText edt_issue = (EditText)findViewById(R.id.edt_issue);

        String descTextTemp = edt_issue.getText().toString();
        descTextTemp = descTextTemp.trim();
        if(descTextTemp.equals("")){
            Toast.makeText(IssuesActivity.this, "Issue Description cannot be blank!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        else{
            semaphore++;
        }

        final String descText=descTextTemp;
        edt_issue.setText("");
        Log.d("Manish",descText+"; :: ;" +descTextTemp);

        if(chosenFile == null){
            //here just call and send only text, no imgur
            Log.d("Manish","chosen file is null");

            //here do API call for posting in backend
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IssuesAPI issuesAPI = retrofit.create(IssuesAPI.class);
            IssuesModel issuesModel2 = new IssuesModel(descText,"",false);
            Call<ResponseBody> call2 = issuesAPI.postIssues(issuesModel2);
            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Log.d("Manish","image loaded to backend");
                        Toast.makeText(IssuesActivity.this, "Upload successful to backend!", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Log.d("Manish","backend load failed");
                        Toast.makeText(IssuesActivity.this, "Upload unsuccessful to backend!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Manish","backend load failed");
                    Toast.makeText(IssuesActivity.this, "Upload unsuccessful to backend!", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            return;
        }

        Log.d("Manish","This is file "+chosenFile.getName()+" "+chosenFile.getPath());

        Call<ImageModel> call =
                imgurAPI.postImage(
                        MultipartBody.Part.createFormData(
                                "image",
                                chosenFile.getName(),
                                RequestBody.create(MediaType.parse("image/*"), chosenFile)
                        ));

        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                if (response == null) {
                    Toast.makeText(IssuesActivity.this, "Upload unsuccessful !", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("Manish","Upload to Imgur unsuccessful");
                    return;
                }
                if (response.isSuccessful()) {
                    Toast.makeText(IssuesActivity.this, "Upload successful !", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("Manish", "Imgur URL http://imgur.com/" + response.body().data.id);
                    imgurUrl = "http://imgur.com/" + response.body().data.id+".png";

                    //here do API call for posting in backend
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    IssuesAPI issuesAPI = retrofit.create(IssuesAPI.class);
                    IssuesModel issuesModel2 = new IssuesModel(descText,imgurUrl,false);
                    Call<ResponseBody> call2 = issuesAPI.postIssues(issuesModel2);
                    call2.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                Log.d("Manish","image loaded to backend");
                                Toast.makeText(IssuesActivity.this, "Upload successful to backend!", Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                            else{
                                Log.d("Manish","backend load failed");
                                Toast.makeText(IssuesActivity.this, "Upload unsuccessful to backend!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("Manish","backend load failed");
                            Toast.makeText(IssuesActivity.this, "Upload unsuccessful to backend!", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(IssuesActivity.this, "An unknown error has occured.", Toast.LENGTH_SHORT)
                        .show();
                t.printStackTrace();
            }
        });




    }

    public void UploadImage(View v){



        //here we will upload image
        final CharSequence[] items = { "Take Photo", "Choose from Library"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(IssuesActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result= Utility.checkPermission(IssuesActivity.this);
//                if (items[item].equals("Take Photo")) {
//
//                    userChoosenTask="Take Photo";
//                    if(result) {
//                        Log.d("Manish","Camera Intent");
//                        cameraIntent();
//                    }
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask="Choose from Library";
//                    if(result) {
//                        Log.d("Manish","Gallery Intent");
//                        galleryIntent();
//                    }
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();

        new MaterialDialog.Builder(this)
                .title("Add a Photo!")
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        boolean result= Utility.checkPermission(IssuesActivity.this);
                        if (items[which].equals("Take Photo")) {

                            userChoosenTask="Take Photo";
                            if(result) {
                                Log.d("Manish","Camera Intent");
                                cameraIntent();
                            }
                        } else if (items[which].equals("Choose from Library")) {
                            userChoosenTask="Choose from Library";
                            if(result) {
                                Log.d("Manish","Gallery Intent");
                                galleryIntent();
                            }
                        }
                    }
                })
                .negativeText("Cancel")
                .show();

    }


    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("Manish","Inside camera Intent");
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        Log.d("Manish","Inside Gallery Intent");
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Manish","inside onActivityResult");

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            {       returnUri = data.getData();

                ImageButton photo_upload = (ImageButton)findViewById(R.id.btn_add_image);
                photo_upload.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_accept));
                // Use bounce interpolator with amplitude 0.2 and frequency 20

                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 30);
                myAnim.setInterpolator(interpolator);
                photo_upload.startAnimation(myAnim);
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Log.d("Manish","Inside onselectfromgalleryresult");
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String filePath = DocumentHelper.getPath(this, data.getData());
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;

        chosenFile = null;
        chosenFile = new File(filePath);

        Log.d("Manish","file "+chosenFile.getName()+" "+chosenFile.getPath());
        Log.d("Manish", "filepath "+filePath);


    }

    private void onCaptureImageResult(Intent data) {
        Log.d("Manish","inside oncaptureimageresult");
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        chosenFile = null;
        chosenFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            chosenFile.createNewFile();
            fo = new FileOutputStream(chosenFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = thumbnail;

        Log.d("Manish","file "+chosenFile.getName()+" "+chosenFile.getPath());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Log.d("Manish","Permission denied");
                }
                break;
        }
    }

    public void AskPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }




    }

}
