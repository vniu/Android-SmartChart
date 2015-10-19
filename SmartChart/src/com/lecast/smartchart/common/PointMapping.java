package com.lecast.smartchart.common;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;
import android.util.Log;

/**
 * @author vincent
 * 
 */
public class PointMapping
{

    protected List<LocalPoint> points = new ArrayList<LocalPoint>();

    public void addPoint(LocalPoint point)
    {
        if (points.contains(point))
            return;
        points.add(point);
    }

    public void clear()
    {
        points.clear();
    }

    public LocalPoint getClickPoint(LocalPoint screenPoint)
    {
        // Log.v("Screen PointMapping ", screenPoint.getX() + "+++++++" +
        // screenPoint.getY());
        for (LocalPoint localPoint : points)
        {
            Rect rectangle = localPoint.getRect();
            // Log.v("Rect PointMapping ",
            // "================================================");
            // Log.v("Rect PointMapping ", rectangle.left +
            // "++left++++++++++right++" + rectangle.right);
            // Log.v("Rect PointMapping ", rectangle.top +
            // "++top+++++++++bottom++" + rectangle.bottom);
            // Log.v("Rect PointMapping ",
            // "================================================");
            if (rectangle != null && rectangle.contains((int) screenPoint.getX(), (int) screenPoint.getY()))
            {
                // Log.v("Click PointMapping ", localPoint.getX() + "--------" +
                // localPoint.getY());
                return localPoint;
            }
        }
        return null;
    }
}
