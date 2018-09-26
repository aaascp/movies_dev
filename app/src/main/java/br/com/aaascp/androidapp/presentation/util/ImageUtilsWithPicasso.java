package br.com.aaascp.androidapp.presentation.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.aaascp.androidapp.BuildConfig;

public class ImageUtilsWithPicasso implements ImageUtils {

    public void loadImageFromApi(
            ImageView imageView,
            String path,
            int width) {

        Picasso.get()
                .load(BuildConfig.API_IMAGE_URL + "/w" + width + path)
                .into(imageView);
    }
}
