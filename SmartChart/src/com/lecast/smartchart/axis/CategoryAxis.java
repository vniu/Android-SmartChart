package com.lecast.smartchart.axis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lecast.smartchart.chart.CartesianChart;
import com.lecast.smartchart.utils.ReflectUtils;

/**
 * @author vincent
 * 
 */
public class CategoryAxis extends AxisBase implements IAxis
{

    private Map<Integer, Object> categoryValues = null;

    private String categoryField;

    public CategoryAxis()
    {
        super();
        categoryValues = new HashMap<Integer, Object>();
    }

    public void setCategoryField(String categoryFiled)
    {
        this.categoryField = categoryFiled;
    }

    public void update()
    {
        if (dataProvider == null)
            return;
        if (categoryField == null)
            return;
        categoryValues.clear();
        String suffixStr = suffixName == null ? "" : suffixName;
        if (isJsonFormat)
        {
            onJSONProvider(suffixStr);
        }
        else
        {
            onCollectionProvider(suffixStr);
        }
        generateTicks();
    }

    protected void onJSONProvider(String suffixStr)
    {
        axisLabels.clear();
        categoryValues.clear();
        JSONArray sources = (JSONArray) dataProvider;
        itemCount = sources.size();
        double min = baseZero ? 0 : -padding;
        double max = itemCount - 1 + padding;
        double alen = max - min;
        for (int index = 0; index < itemCount; index++)
        {
            try
            {
                JSONObject data = sources.getJSONObject(index);
                categoryValues.put(Integer.valueOf(index), data.get(categoryField) + suffixStr);
                generateAxisLables((index - min) / alen, data.get(categoryField), categoryValues.get(index).toString());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void onCollectionProvider(String suffixStr)
    {
        axisLabels.clear();
        categoryValues.clear();
        List souces = (List) dataProvider;
        itemCount = souces.size();
        double min = baseZero ? 0 : -padding;
        double max = itemCount - 1;// + padding;
        double alen = max - min;
        for (int index = 0; index < itemCount; index++)
        {
            Object object = souces.get(index);
            categoryValues.put(Integer.valueOf(index), ReflectUtils.getProperty(object, categoryField) + suffixStr);
            generateAxisLables((index - min) / alen, ReflectUtils.getProperty(object, categoryField), categoryValues
                        .get(index).toString());
        }
    }

    public void generateAxisLables(double position, Object value, String text)
    {
        AxisLabel axisLabel = new AxisLabel(position, value, text);
        axisLabels.add(axisLabel);
    }

    public List<AxisLabel> getAxisLabels()
    {
        return axisLabels;
    }

    private void generateTicks()
    {
        int n;
        double min;
        double max;
        double alen;
        int i;
        n = categoryValues.size();
        if (ticksBetweenLabels)
        {
            min = -padding;
            max = n - 1 + padding;
            alen = max - min;
            for (i = 0; i < n; i++)
            {
                ticks.add((i - min) / alen);
            }
            return;
        }
        min = -padding;
        max = n - 1 + padding;
        alen = max - min;
        double start = padding < 0.5 ? 0.5 : -0.5;
        double end = padding < 0.5 ? n - 1.5 : n - 0.5;
        for (i = (int) start; i <= end; i += 1)
        {
            ticks.add((i - min) / alen);
        }
    }

    public void accept(CartesianChart chart)
    {
        chart.updateCategoryAxis(this);
    }
}
