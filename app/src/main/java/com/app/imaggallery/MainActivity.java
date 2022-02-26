package com.app.imaggallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.app.imaggallery.adapters.GalleryImageAdapter;
import com.app.imaggallery.datamodels.GalleryItem;
import com.app.imaggallery.viewmodels.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView imageRecyclerView;
    private GalleryImageAdapter galleryImageAdapter;
    private LinearLayout progressbarContainer;
    private GalleryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRecyclerView = findViewById(R.id.image_recyclerview);
        progressbarContainer = findViewById(R.id.container_progressbar);

        imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        galleryImageAdapter = new GalleryImageAdapter(new ArrayList<>());
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        //check if data exists in database, load it
        showHideProgressBar(true);
        viewModel.loadImagesFromDatabase().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItems) {
                showHideProgressBar(false);
                if (galleryItems != null && galleryItems.size() > 0) {
                    galleryImageAdapter.setData(galleryItems);
                    imageRecyclerView.setAdapter(galleryImageAdapter);
                }
            }
        });

        loadImagesFromServer();
    }

    private void loadImagesFromServer() {
        viewModel.loadImagesFromServer().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> galleryItems) {
                if (galleryItems != null) {
                    viewModel.storeDataInDatabase(galleryItems);
                    if (galleryImageAdapter.getItemCount() <= 0) {
                        galleryImageAdapter.setData(galleryItems);
                        imageRecyclerView.setAdapter(galleryImageAdapter);
                    }
                }
            }
        });
    }

    private void showHideProgressBar(boolean isShowProgress) {
        if (isShowProgress) {
            progressbarContainer.setVisibility(View.VISIBLE);
        } else {
            progressbarContainer.setVisibility(View.GONE);
        }
    }
}