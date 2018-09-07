package com.example.androidhrd.upload_image_retrofit.data.remote.helper.callback;

public interface DataResponseCallback<T> {
    void onSuccess(T t);
    void onError(String string);
    void onFailure(Throwable t);
}
