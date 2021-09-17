package ru.realityfamily.partyapp.Presentation.View.Adapters;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.realityfamily.partyapp.databinding.ImageElementBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    List<String> images;
    ActivityResultRegistry mRegistry;

    public ImageSliderAdapter(List<String> images, boolean adding, ActivityResultRegistry registry) {
        this.images = images;

        if (adding) {
            this.images.add(null);
            mRegistry = registry;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ImageSliderViewHolder(ImageElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageSliderViewHolder holder, int position) {
        holder.mBinding.addLayout.getBackground().setAlpha(128);
        if (images.get(position) == null) {
            holder.mBinding.imageContent.setVisibility(View.GONE);
            holder.mBinding.addLayout.setVisibility(View.VISIBLE);
            holder.mBinding.addButton.setOnClickListener((View v) -> {
                if (mRegistry != null) {
                    mRegistry.register("key", new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            images.add(images.size() - 1, result.toString());
                            notifyDataSetChanged();
                        }
                    }).launch("image/*");
                }
            });
        } else {
            holder.mBinding.addLayout.setVisibility(View.GONE);
            holder.mBinding.imageContent.setVisibility(View.VISIBLE);
            holder.mBinding.imageContent.setImageURI(Uri.parse(images.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageSliderViewHolder extends RecyclerView.ViewHolder{

        ImageElementBinding mBinding;

        public ImageSliderViewHolder(ImageElementBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }
    }
}
