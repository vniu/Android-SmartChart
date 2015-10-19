package com.lecast.smartchart.api;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class ChartModel
{

    private String name;

    private List<SeriesModel> seriesModels = new ArrayList<SeriesModel>();

    private int paddingLeft = 30;

    private int paddingRight = 30;

    private int paddingTop = 30;

    private int paddingBottom = 30;

    private String chartLegendName;

    private AxisModel horizonalAxisModel;

    private AxisModel verticalAxisModel;

    private int fontSize;

    private float radius;

    private String titleName;

    private int titleColor = Color.BLACK;

    private AxisModel dialAxisModel;

    public String getName()
    {
        return name;
    }

    public List<SeriesModel> getSeriesModels()
    {
        return seriesModels;
    }

    public int getPaddingLeft()
    {
        return paddingLeft;
    }

    public int getPaddingRight()
    {
        return paddingRight;
    }

    public int getPaddingTop()
    {
        return paddingTop;
    }

    public int getPaddingBottom()
    {
        return paddingBottom;
    }

    public String getChartLegendName()
    {
        return chartLegendName;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addSeriesModel(SeriesModel model)
    {
        if (seriesModels.contains(model))
            return;
        seriesModels.add(model);
    }

    public void setSeriesModels(List<SeriesModel> seriesModels)
    {
        this.seriesModels = seriesModels;
    }

    public void setPaddingLeft(int paddingLeft)
    {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight)
    {
        this.paddingRight = paddingRight;
    }

    public void setPaddingTop(int paddingTop)
    {
        this.paddingTop = paddingTop;
    }

    public void setPaddingBottom(int paddingBottom)
    {
        this.paddingBottom = paddingBottom;
    }

    public void setChartLegendName(String chartLegendName)
    {
        this.chartLegendName = chartLegendName;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public AxisModel getHorizonalAxisModel()
    {
        return horizonalAxisModel;
    }

    public AxisModel getVerticalAxisModel()
    {
        return verticalAxisModel;
    }

    public void setHorizonalAxisModel(AxisModel horizonalAxisModel)
    {
        this.horizonalAxisModel = horizonalAxisModel;
    }

    public void setVerticalAxisModel(AxisModel verticalAxisModel)
    {
        this.verticalAxisModel = verticalAxisModel;
    }

    public String getTitleName()
    {
        return titleName;
    }

    public void setTitleName(String titleName)
    {
        this.titleName = titleName;
    }

    public int getTitleColor()
    {
        return titleColor;
    }

    public void setTitleColor(int titleColor)
    {
        this.titleColor = titleColor;
    }

    public AxisModel getDialAxisModel()
    {
        return dialAxisModel;
    }

    public void setDialAxisModel(AxisModel dialAxisModel)
    {
        this.dialAxisModel = dialAxisModel;
    }

}
