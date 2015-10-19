package com.lecast.smartchart.api;

import android.content.Context;
import android.util.Log;

import com.lecast.smartchart.series.PieSeries;
import com.lecast.smartchart.series.Series;
import com.lecast.smartchart.utils.ColorUtils;

public class SeriesModel
{

    private String valueField;

    private int color;

    private String name;

    private String displayName;

    private int strokeWidth = 1;

    private String labelField;

    private String suffixName;

    public String getValueField()
    {
        return valueField;
    }

    public int getColor()
    {
        return color;
    }

    public String getName()
    {
        return name;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setValueField(String valueField)
    {
        this.valueField = valueField;
    }

    public void setColor(String color)
    {
        if (color != null)
            this.color = ColorUtils.getColorFromHex(color);
        Log.v("Color", color + "-------");

    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public Series getSeries(Context context)
    {
        Series series = SeriesCreator.getSeries(this, context);
        series.setValueField(valueField);
        series.setDisplayName(displayName);
        if (series instanceof PieSeries)
        {
            PieSeries pieSeries = (PieSeries) series;
            pieSeries.setLabelField(labelField);
            pieSeries.setSuffixName(suffixName);
        }
        return series;
    }

    public int getStrokeWidth()
    {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }

    public String getLabelField()
    {
        return labelField;
    }

    public void setLabelField(String labelField)
    {
        this.labelField = labelField;
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
