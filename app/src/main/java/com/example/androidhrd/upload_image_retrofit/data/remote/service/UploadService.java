package com.example.androidhrd.upload_image_retrofit.data.remote.service;

import com.example.androidhrd.upload_image_retrofit.entity.BaseResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    @POST("/api/v1/urls/upload/web-icon")
    @Multipart
    Call<BaseResponse<String>> uploadImage(@Part MultipartBody.Part part);
}
