package com.example.androidhrd.upload_image_retrofit.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    static final String BASE_URL="http://110.74.194.125:15000";
    static Retrofit.Builder builder=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> service){

        return builder.build().create(service);

    }
}
