package com.app.imaggallery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.network.GalleryRequestInterceptor;
import com.app.imaggallery.service.GalleryService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class GalleryRepository {

    private static final String BASE_URL_IMAGE_GALLERY = "https://jsonplaceholder.typicode.com/photos";

    private GalleryService galleryService;
    private Call<List<GalleryItem>> galleryCall;

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new GalleryRequestInterceptor())
            .build();

    public GalleryRepository() {
        galleryService = new Retrofit.Builder().baseUrl(BASE_URL_IMAGE_GALLERY)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GalleryService.class);
    }

    public MutableLiveData<List<GalleryItem>> getImagesFromServer() {
        MutableLiveData<List<GalleryItem>> galleryItemLiveData = new MutableLiveData<>();

        galleryCall = galleryService.fetchImagesFromServer();
        galleryCall.enqueue(new Callback<List<GalleryItem>>() {

            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                if(!call.isCanceled() && response.isSuccessful()){
                    galleryItemLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {

            }
        });

        return galleryItemLiveData;
    }
}
