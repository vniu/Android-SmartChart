package com.lecast.smartchart.utils;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import com.lecast.smartchart.common.LocalPoint;

/**
 * @author vincent
 * 
 */
public class DrawUtils
{

    public static void drawText(Canvas canvas, String text, float x, float y, Paint paint, float angle)
    {

        if (angle != 0)
        {
            canvas.rotate(angle, x, y);
        }
        drawString(canvas, text, x, y, paint);
        if (angle != 0)
        {
            canvas.rotate(-angle, x, y);
            // canvas.scale(mScale, 1 / mScale);
        }
    }

    protected static void drawString(Canvas canvas, String text, float x, float y, Paint paint)
    {
        if (text != null)
        {
            String[] lines = text.split("\n");
            Rect rect = new Rect();
            int yOff = 0;
            for (int i = 0; i < lines.length; ++i)
            {
                canvas.drawText(lines[i], x, y + yOff, paint);
                paint.getTextBounds(lines[i], 0, lines[i].length(), rect);
                yOff = yOff + rect.height() + 5; // space between lines is 5
            }
        }
    }

    public static Rect getTextRect(String value, int fontSize)
    {
        Paint font = new Paint();
        Rect rect = new Rect();
        font.setTextSize(fontSize);
        font.getTextBounds(value, 0, value.length(), rect);
        return rect;
    }

    public static int getRandomColor()
    {
        Random rnd = new Random();
        rnd.nextInt(256);
        rnd.nextInt(256);
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int getRandomRGBColor()
    {
        Random rnd = new Random();
        rnd.nextInt(256);
        rnd.nextInt(256);
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int adjustBrightness(int rgb, int brite)
    {
        int r = Math.max(Math.min(((rgb >> 16) & 0xFF) + brite, 255), 0);
        int g = Math.max(Math.min(((rgb >> 8) & 0xFF) + brite, 255), 0);
        int b = Math.max(Math.min((rgb & 0xFF) + brite, 255), 0);
        return (r << 16) | (g << 8) | b;
    }

    public static int adjustBrightness2(int rgb, int brite)
    {
        int r;
        int g;
        int b;

        if (brite == 0)
            return rgb;

        if (brite < 0)
        {
            brite = (100 + brite) / 100;
            r = ((rgb >> 16) & 0xFF) * brite;
            g = ((rgb >> 8) & 0xFF) * brite;
            b = (rgb & 0xFF) * brite;
        }
        else
        // bright > 0
        {
            brite /= 100;
            r = ((rgb >> 16) & 0xFF);
            g = ((rgb >> 8) & 0xFF);
            b = (rgb & 0xFF);
            r += ((0xFF - r) * brite);
            g += ((0xFF - g) * brite);
            b += ((0xFF - b) * brite);
            r = Math.min(r, 255);
            g = Math.min(g, 255);
            b = Math.min(b, 255);
        }
        return Color.argb(255, r, g, b);
        // return (r << 16) | (g << 8) | b;
    }

    public static LocalPoint getPointByAngle(float angle, float radius, LocalPoint centerPoint)
    {
        while (angle >= 360)
        {
            angle = angle - 360;
        }
        LocalPoint point;
        if (angle == 0)
        {
            return new LocalPoint(radius + centerPoint.getX(), centerPoint.getY());
        }
        if (angle == 90)
        {
            return new LocalPoint(centerPoint.getX(), radius + centerPoint.getY());
        }
        if (angle == 180)
        {
            return new LocalPoint(-radius + centerPoint.getX(), centerPoint.getY());
        }
        if (angle == 270)
        {
            return new LocalPoint(centerPoint.getX(), -radius + centerPoint.getY());
        }
        float t = Float.valueOf(String.valueOf(Math.tan(angle * Math.PI / 180.0)));
        float x = Float.valueOf(String.valueOf(radius / Math.sqrt(1 + Math.pow(t, 2.0))));
        float y = t * x;
        point = (angle < 90 || angle > 270) ? new LocalPoint(x, y) : new LocalPoint(-x, -y);
        return new LocalPoint(point.getX() + centerPoint.getX(), point.getY() + centerPoint.getY());

    }

    /**
     * Drawable ת bitmap
     * 
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        else if (drawable instanceof NinePatchDrawable)
        {
            Bitmap bitmap =
                        Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable
                                    .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        else
        {
            return null;
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
    // private void transform(Canvas canvas, float angle, boolean inverse)
    // {
    // if (inverse)
    // {
    // canvas.scale(1 / mScale, mScale);
    // canvas.translate(mTranslate, -mTranslate);
    // canvas.rotate(-angle, mCenter.getX(), mCenter.getY());
    // }
    // else
    // {
    // canvas.rotate(angle, mCenter.getX(), mCenter.getY());
    // canvas.translate(-mTranslate, mTranslate);
    // canvas.scale(mScale, 1 / mScale);
    // }
    // }
}
