package br.com.aaascp.androidapp.presentation.util

import android.util.Log
import android.widget.ImageView
import br.com.aaascp.androidapp.BuildConfig
import com.squareup.picasso.Picasso

fun loadImageFromApi(
        imageView: ImageView,
        path: String,
        width: Int = 500) {
    Log.d("Andre", BuildConfig.API_IMAGE_URL + "/w" + width + path)
    Picasso.get()
            .load(BuildConfig.API_IMAGE_URL + "/w" + width + path)
            .into(imageView)
}