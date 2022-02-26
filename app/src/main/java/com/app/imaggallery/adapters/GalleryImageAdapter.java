package com.app.imaggallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.imaggallery.R;
import com.app.imaggallery.datamodels.GalleryItem;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GalleryItem> mItemsList = Collections.emptyList();

    public GalleryImageAdapter(List<GalleryItem> listItems) {
        this.mItemsList = listItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ItemViewHolder(view.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ItemViewHolder) viewHolder).bindData(mItemsList.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemsList != null ? mItemsList.size() : 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivImage;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
            ivImage = itemView.findViewById(R.id.item_image);
        }

        void bindData(GalleryItem galleryItem) {
            if (galleryItem != null) {
                tvTitle.setText(galleryItem.getTitle());
                //load image URL here
                Picasso.get()
                        .load(galleryItem.getUrl())
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(ivImage);
            }
        }
    }

    public void setData(List<GalleryItem> itemsList) {
        mItemsList = itemsList;
        notifyDataSetChanged();
    }

}