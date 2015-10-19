package com.lecast.smartchart.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author vincent
 * 
 */
public class ScreenUtils
{

    public static int getScreenHeight(Context context)
    {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScreenWidth(Context context)
    {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
