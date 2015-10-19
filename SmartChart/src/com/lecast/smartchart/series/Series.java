package com.lecast.smartchart.series;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.common.PointMapping;
import com.lecast.smartchart.graphics.stroke.IStroke;
import com.lecast.smartchart.listener.IChartRepaintHandler;
import com.lecast.smartchart.utils.ReflectUtils;

public class Series implements ISeries
{

    protected IStroke stroke;

    protected String valueField;

    protected Context context;

    protected Object dataProvider;

    protected List dataSources;

    protected JSONArray jsonArraySources;

    protected boolean isJsonFormat = false;

    protected PointMapping pointMapping;

    protected int fontSize = 14;

    protected String displayName;

    protected ChartBase chart;

    protected float offsetX = 0, offsetY = 0;

    protected float scale = 1;

    protected int dataSize;

    protected Object[] propertyValues;

    protected IChartRepaintHandler repaintHandler;

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public Series(Context context)
    {
        this.context = context;
    }

    public void setPadding(int paddingLeft, int paddingTop, int paddingBottom, int paddingRight)
    {
    }

    public String getValueField()
    {
        return valueField;
    }

    public void setValueField(String valueField)
    {
        this.valueField = valueField;
    }

    public void setDataProvider(Object dataProvider)
    {
        this.dataProvider = dataProvider;
        if (dataProvider == null || valueField == null)
            return;
        filterValue();
    }

    public void onDrawHandler(Canvas canvas)
    {

    }

    protected void filterValue()
    {
        propertyValues = null;
        if (dataProvider instanceof JSONArray || dataProvider instanceof org.json.JSONArray)
        {
            isJsonFormat = true;
            jsonArraySources = (JSONArray) dataProvider;
            dataSize = jsonArraySources.size();
            propertyValues = new Object[dataSize];
            for (int index = 0; index < dataSize; index++)
            {
                try
                {
                    JSONObject jsonObject = jsonArraySources.getJSONObject(index);
                    propertyValues[index] = jsonObject.get(valueField);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return;
        }
        isJsonFormat = false;
        dataSources = (List) dataProvider;
        dataSize = dataSources.size();
        propertyValues = new Object[dataSize];
        for (int index = 0; index < dataSize; index++)
        {
            Object object = dataSources.get(index);
            propertyValues[index] = ReflectUtils.getProperty(object, valueField);
        }
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public IStroke getStroke()
    {
        return stroke;
    }

    public void setStroke(IStroke stroke)
    {
        this.stroke = stroke;
    }

    public void setPiontMapping(PointMapping piontMapping)
    {
        this.pointMapping = piontMapping;
    }

    public ChartBase getChart()
    {
        return chart;
    }

    public void setChart(ChartBase chart)
    {
        this.chart = chart;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public float getScale()
    {
        return scale;
    }

    public void setOffset(float offsetX, float offsetY)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public float getOffsetX()
    {
        return offsetX;
    }

    public float getOffsetY()
    {
        return offsetY;
    }

    public void setChartRepaintHandler(IChartRepaintHandler repaintHandler)
    {
        this.repaintHandler = repaintHandler;
    }

}
