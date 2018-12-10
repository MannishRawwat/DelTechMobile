package com.rawggar.deltechmobile.API;

import com.rawggar.deltechmobile.Models.NewsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI{
    @GET("news")
    Call<ArrayList<NewsModel>> getNews();
}

