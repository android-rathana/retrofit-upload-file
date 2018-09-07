package com.example.androidhrd.upload_image_retrofit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.androidhrd.upload_image_retrofit.util.CheckRuntimePermission;
import com.example.androidhrd.upload_image_retrofit.util.MultiPartHelper;

import okhttp3.MultipartBody;

public class MainActivity extends AppCompatActivity {

    ImageView btnPickImage, image;
    static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPickImage=findViewById(R.id.btnPickImage);
        image=findViewById(R.id.imageView);

        new CheckRuntimePermission().grantWriteExternalStorage(this);

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
                uploadImage(uri);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Uri uri) {
        MultipartBody.Part part =new MultiPartHelper().createPart(this,"file",uri);

    }
}
