package com.lecast.smartchart.common;

import android.util.Log;

public class RoundPointMapping extends PointMapping
{

    protected float mCenterX, mCenterY;

    private float radius;

    protected double getAngle(LocalPoint screenPoint)
    {
        double dx = screenPoint.getX() - mCenterX;
        double dy = -(screenPoint.getY() - mCenterY);
        double inRads = Math.atan2(dy, dx);
        inRads = inRads < 0 ? Math.abs(inRads) : 2 * Math.PI - inRads;
        return Math.toDegrees(inRads);
    }

    public void setCenter(float centerX, float centerY)
    {
        this.mCenterX = centerX;
        this.mCenterY = centerY;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    protected int getEventAngle(LocalPoint eventPoint)
    {
        double x = eventPoint.getX() - mCenterX;// x轴方向的偏移量
        double y = eventPoint.getY() - mCenterY; // y轴方向的偏移量
        double z = Math.hypot(Math.abs(x), Math.abs(y)); // 求直角三角形斜边的长度
        double sinA = (double) Math.abs(y) / z;
        double asin = Math.asin(sinA); // 求反正玄，得到当前点和x轴的角度,是最小的那个
        int degree = (int) (asin / 3.14f * 180f);
        int realDegree = 0;
        if (x <= 0 && y <= 0)
        {
            realDegree = 180 + degree;
        }
        else if (x >= 0 && y <= 0)
        {
            realDegree = 360 - degree;
        }
        else if (x <= 0 && y >= 0)
        {
            realDegree = 180 - degree;
        }
        else
        {
            realDegree = degree;
        }
        return realDegree;
    }

    private boolean isOnRoundChart(LocalPoint screenPoint)
    {
        // Log.v("Touch Point", screenPoint.getX() + "  " + screenPoint.getY());
        double sqValue = (Math.pow(mCenterX - screenPoint.getX(), 2) + Math.pow(mCenterY - screenPoint.getY(), 2));
        double radiusSquared = radius * radius;
        boolean isOnPieChart = sqValue <= radiusSquared;
        return isOnPieChart;
    }

    public LocalPoint getClickPoint(LocalPoint screenPoint)
    {
        if (isOnRoundChart(screenPoint))
        {
            double angleFromRoundCenter = getEventAngle(screenPoint);
            // Log.v("Point Agnle", angleFromRoundCenter + "");
            int currentSum = 0;
            for (LocalPoint point : points)
            {
                RoundLocalPoint roundPoint = (RoundLocalPoint) point;
                currentSum += roundPoint.getAngle();
                if (currentSum >= angleFromRoundCenter)
                {
                    return roundPoint;
                }
            }
        }
        return null;
    }

}
