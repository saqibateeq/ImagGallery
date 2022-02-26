package com.app.imaggallery.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.imaggallery.datamodels.GalleryItem;

import java.util.List;

@Dao
public interface GalleryDao {

    @Query("SELECT * FROM galleryitementity group by album_id")
    LiveData<List<GalleryItem>> getAllDataGroupByAlbumId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GalleryItem> galleryItems);

    @Delete
    void delete(GalleryItem galleryItem);
}
