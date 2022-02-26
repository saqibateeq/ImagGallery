package com.app.imaggallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.viewmodels.GalleryViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView imageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRecyclerView = findViewById(R.id.image_recyclerview);

        GalleryViewModel viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        viewModel.loadImagesFromServer().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItem) {
                //populate response data into recycler view
            }
        });
    }
}