package com.rawggar.deltechmobile.API;

import com.rawggar.deltechmobile.Models.CalendarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CalendarAPI {
    @GET("calendar")
    Call<List<CalendarModel>> getCalendar();
}
