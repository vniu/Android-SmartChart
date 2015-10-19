package com.lecast.smartchart.utils;

import android.graphics.Color;

public class ColorUtils
{

    public static int getColorFromHex(String color)
    {
        if (color.contains("0x"))
            color = color.replace("0x", "");
        // if (color.contains("#"))
        // color = color.replace("#", "");
        // return Integer.parseInt(color, 16) + 0xFF000000;
        if (!color.contains("#"))
        {
            StringBuffer colorBuffer = new StringBuffer("#");
            colorBuffer.append(color);
            return Color.parseColor(colorBuffer.toString());
        }
        //
        return Color.parseColor(color);
        // StringBuffer colorBuffer = new StringBuffer("0xff");
        // colorBuffer.append(color);
        // int value = Integer.parseInt(colorBuffer.toString(), 16);
        // int red = ((value >> 24) & 0xFF) / 255;
        // int green = ((value >> 16) & 0xFF) / 255;
        // int blue = ((value >> 8) & 0xFF) / 255;
        // int alpha = ((value >> 0) & 0xFF) / 255;
        // Color.parseColor(colorString);
        // return Color.argb(alpha, red, green, blue);
    }
}
