package com.example.mrx.visionboardapp.Helpers;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class ResourceGetter {

    public static Drawable getDrawable(int id){
        return ContextCompat.getDrawable(AppContextGetter.getContext(), id);
    }

    public static String getString(int id){
        return AppContextGetter.getContext().getString(id);
    }
}
