package br.com.aaascp.androidapp.presentation.util;

import android.widget.ImageView;

public interface ImageUtils {

    void loadImageFromApi(
            ImageView imageView,
            String path,
            int width);
}
