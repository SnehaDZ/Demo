package com.example.demo;

/**
 * Created by Naushad on 10/30/2017.
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("GetContacts")
    Call<JsonArray> GetContacts(@Body JsonObject jsonObject);




}
