package com.lecast.smartchart.axis;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.lecast.smartchart.chart.CartesianChart;

/**
 * @author vincent
 * 
 */
public class AxisBase
{

    private String displayName;

    private String title;

    protected Object dataProvider;

    protected String suffixName;

    protected Boolean isJsonFormat = false;

    protected AxisLabelSetting axisLabelSetting;

    protected List<AxisLabel> axisLabels = null;

    protected Boolean ticksBetweenLabels = true;

    protected List<Double> ticks;

    protected int itemCount = 0;

    protected double padding = 1;

    protected int fontSize = 12;

    protected boolean baseZero = true;

    protected int textAngle = 0;

    protected String unitName;

    public AxisBase()
    {
        axisLabels = new ArrayList<AxisLabel>();
        axisLabelSetting = new AxisLabelSetting();
        axisLabelSetting.setLabels(axisLabels);
        ticks = new ArrayList<Double>();
        axisLabelSetting.setTicks(ticks);
    }

    public void setDataProvider(Object dataProvider)
    {
        if (dataProvider == null)
            return;
        this.dataProvider = dataProvider;
        if (dataProvider instanceof JSONArray || dataProvider instanceof org.json.JSONArray)
        {
            isJsonFormat = true;
        }
        else if (dataProvider instanceof List)
        {
            isJsonFormat = false;
        }
        update();

    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public void dataChanged()
    {

    }

    public void update()
    {

    }

    public void setSuffixName(String suffixName)
    {
        this.suffixName = suffixName;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getTextAngle()
    {
        return textAngle;
    }

    public void setTextAngle(int textAngle)
    {
        this.textAngle = textAngle;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public AxisLabelSetting getAxisLabelSetting()
    {
        return axisLabelSetting;
    }

    public void setTicksBetweenLabels(Boolean ticksBetweenLabels)
    {
        this.ticksBetweenLabels = ticksBetweenLabels;
    }

    public Object getDataProvider()
    {
        return null;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public void setBaseZero(boolean baseZero)
    {
        this.baseZero = baseZero;
    }

    public boolean isBaseZero()
    {
        return baseZero;
    }

    public void accept(CartesianChart chart)
    {

    }
}
