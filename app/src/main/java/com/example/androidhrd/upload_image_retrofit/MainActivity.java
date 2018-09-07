package com.example.androidhrd.upload_image_retrofit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.androidhrd.upload_image_retrofit.data.remote.ServiceGenerator;
import com.example.androidhrd.upload_image_retrofit.data.remote.helper.RequestHelper;
import com.example.androidhrd.upload_image_retrofit.data.remote.helper.callback.DataResponseCallback;
import com.example.androidhrd.upload_image_retrofit.data.remote.service.UploadService;
import com.example.androidhrd.upload_image_retrofit.entity.BaseResponse;
import com.example.androidhrd.upload_image_retrofit.util.CheckRuntimePermission;
import com.example.androidhrd.upload_image_retrofit.util.MultiPartHelper;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView btnPickImage, image;
    static final int PICK_IMAGE=1;
    private UploadService uploadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPickImage=findViewById(R.id.btnPickImage);
        image=findViewById(R.id.imageView);

        new CheckRuntimePermission().grantWriteExternalStorage(this);

        //generate service
        uploadService= ServiceGenerator.createService(UploadService.class);

        btnPickImage.setOnClickListener(v->{
            pickImageIntent();
        });
    }


    protected void pickImageIntent(){
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){

            Uri uri=data.getData();
            try{
                String[] filePathColumns={MediaStore.Images.Media.DATA};
                Cursor cursor=getContentResolver().query(
                    uri,filePathColumns,null,null,null
                );
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumns[0]);
                String filePath=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(filePath);
                image.setImageBitmap(bitmap);

                //upload image
                uploadImage(filePath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static String TAG=MainActivity.class.getName();
    private void uploadImage(String filePath) {

        MultipartBody.Part part =new MultiPartHelper().createPart(this,"file",filePath);


       /*uploadService.uploadImage(part).enqueue(
                new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.e(TAG, "onResponse: "+response.body().getMsg());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                }
        );*/


        new RequestHelper<BaseResponse<String>>().execute(
                uploadService.uploadImage(part),
                new DataResponseCallback<BaseResponse<String>>(){
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        Log.e(TAG, "onSuccess: "+response);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: "+t.toString());

                    }

                    @Override
                    public void onError(String string) {


                    }
                }
        );
    }
}
