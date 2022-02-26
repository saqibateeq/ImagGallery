package com.app.imaggallery.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.repositories.GalleryRepository;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private GalleryRepository galleryRepository;

    public GalleryViewModel(){
        galleryRepository = new GalleryRepository();
    }

    public MutableLiveData<List<GalleryItem>> loadImagesFromServer(){
        return galleryRepository.getImagesFromServer();
    }
}
