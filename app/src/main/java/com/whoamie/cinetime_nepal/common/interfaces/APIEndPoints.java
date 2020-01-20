package com.whoamie.cinetime_nepal.common.interfaces;

import com.whoamie.cinetime_nepal.common.models.Notification;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIEndPoints {
//    @Headers({
//            "Content-Type:application/json"
//    })
//    @POST("notification")
//    Call<Notification> retriveNotifications(@Body Map<String, String> versionId);

    @GET("notification")
    Call<Notification> getNotifications();

//    @GET("users/logout")
//    Call<Void> logout(@Header("token") String token);
}
