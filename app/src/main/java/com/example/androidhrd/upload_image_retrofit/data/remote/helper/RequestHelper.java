package com.example.androidhrd.upload_image_retrofit.data.remote.helper;

import com.example.androidhrd.upload_image_retrofit.data.remote.helper.callback.DataResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHelper <T> {

    public void execute(Call<T> call, DataResponseCallback<T> callback){

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(null !=callback)
                    callback.onSuccess(response.body());
                else
                    callback.onError("call back is null object reference");
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if(null !=callback)
                    callback.onFailure(t);
            }
        });

    }
}
