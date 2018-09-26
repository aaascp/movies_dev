package br.com.aaascp.androidapp.presentation.util;

import java.util.List;

public class ListUtils {

    public static String join(List<String> list) {
        String result = "";

        for (String item : list) {
            result = item + ", ";
        }

        return result.substring(0, result.length() - 2);
    }
}
