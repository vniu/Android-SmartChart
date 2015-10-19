package com.lecast.smartchart.api;

public class AxisModel
{

    private String name;

    private String displayName;

    private String valueField;

    private String categoryField;

    private int interval;

    private int textAngle;

    private int titleAngle;

    private String suffixName;

    private float maxValue = 100;

    private float minValue = 0;

    public String getName()
    {
        return name;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public String getValueField()
    {
        return valueField;
    }

    public String getCategoryField()
    {
        return categoryField;
    }

    public int getInterval()
    {
        return interval;
    }

    public int getTextAngle()
    {
        return textAngle;
    }

    public int getTitleAngle()
    {
        return titleAngle;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public void setValueField(String valueField)
    {
        this.valueField = valueField;
    }

    public void setCategoryField(String categoryField)
    {
        this.categoryField = categoryField;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public void setTextAngle(int textAngle)
    {
        this.textAngle = textAngle;
    }

    public void setTitleAngle(int titleAngle)
    {
        this.titleAngle = titleAngle;
    }

    public String getSuffixName()
    {
        return suffixName;
    }

    public void setSuffixName(String suffixName)
    {
        this.suffixName = suffixName;
    }

    public float getMaxValue()
    {
        return maxValue;
    }

    public float getMinValue()
    {
        return minValue;
    }

    public void setMaxValue(float maxValue)
    {
        this.maxValue = maxValue;
    }

    public void setMinValue(float minValue)
    {
        this.minValue = minValue;
    }
}
