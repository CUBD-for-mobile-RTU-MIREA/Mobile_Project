package ru.realityfamily.partyapp.Presentation.View.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
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

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.databinding.ImageElementBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    List<String> images;
    MainActivity mActivity;

    public ImageSliderAdapter(List<String> images, boolean adding, MainActivity activity) {
        this.images = images;
        mActivity = activity;

        if (adding) {
            this.images.add(null);
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
            holder.mBinding.addButton.setVisibility(View.VISIBLE);
            holder.mBinding.addButton.setOnClickListener((View v) -> {
                if (mActivity != null) {
                    mActivity.getActivityResultRegistry().register("key", new ActivityResultContracts.OpenDocument(), new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            mActivity.getApplicationContext().getContentResolver().takePersistableUriPermission(result, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            images.add(images.size() - 1, result.toString());
                            notifyDataSetChanged();
                        }
                    }).launch(new String[]{"image/*"});
                }
            });
        } else {
            holder.mBinding.addButton.setVisibility(View.GONE);
            holder.mBinding.imageContent.setVisibility(View.VISIBLE);

            if (mActivity != null) {
                try {


                    holder.mBinding.imageContent.setImageBitmap(
                            BitmapFactory.decodeFileDescriptor(
                                    mActivity.getApplicationContext().getContentResolver().openFileDescriptor(
                                            Uri.parse(images.get(position)), "r").getFileDescriptor()
                            )
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
