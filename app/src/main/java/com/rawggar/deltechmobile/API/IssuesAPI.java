package com.rawggar.deltechmobile.API;

import com.rawggar.deltechmobile.Models.IssuesModel;
import com.rawggar.deltechmobile.Models.NewsModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IssuesAPI{
    @GET("issues")
    Call<ArrayList<IssuesModel>> getIssues();

    @POST("issues")
    Call<ResponseBody> postIssues(@Body IssuesModel issuesModel);
}

