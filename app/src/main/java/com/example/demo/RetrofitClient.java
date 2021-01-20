package com.example.demo;

/**
 * Created by Naushad on 10/30/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static ConnectionPool pool = new ConnectionPool(15, 10, TimeUnit.MINUTES);

    private static Retrofit retrofit = null;
    static OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectionPool(pool)
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .build();

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
