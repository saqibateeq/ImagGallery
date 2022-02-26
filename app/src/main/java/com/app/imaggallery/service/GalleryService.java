package com.app.imaggallery.service;

import com.app.imaggallery.datamodels.GalleryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GalleryService {

    @GET("photos")
    Call<List<GalleryItem>> fetchImagesFromServer();
}
