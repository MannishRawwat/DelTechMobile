package com.rawggar.deltechmobile.API;

import com.rawggar.deltechmobile.Models.ServiceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceAPI {
    @GET("services")
    Call<List<ServiceModel>> getServices();
}
