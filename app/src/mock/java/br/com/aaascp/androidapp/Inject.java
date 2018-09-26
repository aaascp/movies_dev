package br.com.aaascp.androidapp;

import br.com.aaascp.androidapp.presentation.util.ImageUtils;
import br.com.aaascp.androidapp.presentation.util.ImageUtilsMock;

public class Inject {

    public static ImageUtils provideImageUtils() {
        return new ImageUtilsMock();
    }
}
