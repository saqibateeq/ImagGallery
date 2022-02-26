package com.app.imaggallery.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.app.imaggallery.AppApplication;
import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.db.GalleryDao;
import com.app.imaggallery.db.GalleryDatabase;
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

    private static final String BASE_URL_IMAGE_GALLERY = "https://jsonplaceholder.typicode.com/";

    private final GalleryService galleryService;
    private final GalleryDao galleryDao;

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new GalleryRequestInterceptor())
            .build();

    public GalleryRepository() {
        galleryService = new Retrofit.Builder().baseUrl(BASE_URL_IMAGE_GALLERY)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GalleryService.class);

        GalleryDatabase database = Room.databaseBuilder(AppApplication.appInstance, GalleryDatabase.class, "gallery_database").allowMainThreadQueries().build();
        galleryDao = database.galleryDao();
    }

    public MutableLiveData<List<GalleryItem>> getImagesFromServer() {
        MutableLiveData<List<GalleryItem>> galleryItemLiveData = new MutableLiveData<>();

        Call<List<GalleryItem>> galleryCall = galleryService.fetchImagesFromServer();
        galleryCall.enqueue(new Callback<List<GalleryItem>>() {

            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                if (!call.isCanceled() && response.isSuccessful()) {
                    galleryItemLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                //we can post error response here and handle on UI accordingly
            }
        });

        return galleryItemLiveData;
    }

    public LiveData<List<GalleryItem>> getImagesFromDatabase() {
        return galleryDao.getAllDataGroupByAlbumId();
    }

    public void insertDataInDatabase(List<GalleryItem> itemsList){
        galleryDao.insertAll(itemsList);
    }
}
