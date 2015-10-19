package com.lecast.smartchart.series;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.common.RoundLocalPoint;
import com.lecast.smartchart.common.RoundPointMapping;
import com.lecast.smartchart.utils.DrawUtils;

public class RoundSeries extends Series
{

    protected float centerX, centerY;

    protected float radius = 40;

    protected String labelField;

    protected int[] colorsCache;

    protected String suffixName;

    public int[] getColors()
    {
        return colorsCache;
    }

    public RoundSeries(Context context)
    {
        super(context);
    }

    public void setSeriesColor(int[] colors)
    {
        this.colorsCache = colors;
    }

    public void onDrawHandler(Canvas canvas)
    {
        if (colorsCache == null)
            createColor();
    }

    public void setCenter(float centerX, float centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public String getLabelField()
    {
        return labelField;
    }

    public void setLabelField(String labelField)
    {
        this.labelField = labelField;
    }

    protected void createColor()
    {
        colorsCache = new int[dataSize];
        for (int index = 0; index < dataSize; index++)
        {
            colorsCache[index] = DrawUtils.getRandomRGBColor();
        }
    }

    protected void setCenterHandler(float lastCenterX, float lastCenterY, float lastRadius)
    {
        RoundPointMapping roundPointMapping = (RoundPointMapping) pointMapping;
        roundPointMapping.setCenter(lastCenterX, lastCenterY);
        roundPointMapping.setRadius(lastRadius);
    }

    protected void addClickPoint(int index, float angle)
    {
        try
        {
            Object object = isJsonFormat ? jsonArraySources.get(index) : dataSources.get(index);
            RoundPointMapping roundPointMapping = (RoundPointMapping) pointMapping;
            RoundLocalPoint touchPoint = new RoundLocalPoint(angle, object);
            roundPointMapping.addPoint(touchPoint);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String getSuffixName()
    {
        return suffixName;
    }

    public void setSuffixName(String suffixName)
    {
        this.suffixName = suffixName;
    }
}