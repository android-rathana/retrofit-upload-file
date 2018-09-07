package com.example.androidhrd.upload_image_retrofit.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class CheckRuntimePermission {
    public void grantWriteExternalStorage(AppCompatActivity context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
    }
}
