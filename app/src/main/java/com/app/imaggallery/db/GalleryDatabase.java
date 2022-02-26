package com.app.imaggallery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.app.imaggallery.datamodels.GalleryItem;

@Database(entities = {GalleryItem.class}, version = 1)
public abstract class GalleryDatabase extends RoomDatabase {

    public abstract GalleryDao galleryDao();
}
