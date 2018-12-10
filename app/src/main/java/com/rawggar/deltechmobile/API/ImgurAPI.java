package com.rawggar.deltechmobile.API;


import com.rawggar.deltechmobile.Models.ImageModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by maxim on 21/02/2017.
 */

public interface ImgurAPI {
    @Multipart
    @Headers({
            "Authorization: Client-ID 764f39dcbeb221b" //client id daalni padegi
    })
    @POST("image")
    Call<ImageModel> postImage(
            @Part MultipartBody.Part file);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
