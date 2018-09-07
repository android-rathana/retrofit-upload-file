package com.example.androidhrd.upload_image_retrofit.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MultiPartHelper {
    public MultipartBody.Part createPart(Context context, String param, String filePath){
        Uri uri= Uri.parse(filePath);
        File file=new File(uri.getPath());
        RequestBody requestBody=getRequestBody(context,file);
        MultipartBody.Part part= MultipartBody.Part.createFormData(param,file.getName(),requestBody);
        return part;
    }
    public RequestBody getRequestBody(Context  context,File file){
        if(null!= file)
            return RequestBody.create(MediaType.parse("multipart/form-data"),file);
        else
            return null;
    }

}
