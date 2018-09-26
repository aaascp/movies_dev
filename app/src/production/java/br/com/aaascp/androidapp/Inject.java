package br.com.aaascp.androidapp;

import br.com.aaascp.androidapp.presentation.util.ImageUtils;
import br.com.aaascp.androidapp.presentation.util.ImageUtilsWithPicasso;

public class Inject {

    public static ImageUtils provideImageUtils() {
        return new ImageUtilsWithPicasso();
    }
}
