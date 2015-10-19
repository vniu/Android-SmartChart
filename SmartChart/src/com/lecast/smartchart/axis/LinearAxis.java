package com.lecast.smartchart.axis;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lecast.smartchart.chart.CartesianChart;
import com.lecast.smartchart.utils.MathHelper;
import com.lecast.smartchart.utils.ReflectUtils;

/**
 * @author vincent
 * 
 */
public class LinearAxis extends AxisBase implements IAxis
{

    private double minAxisValue;

    private double maxAxisValue;

    private int interval = 5;

    private String[] valueFields;

    private Object[] describeData;

    private double labelMinimum = 0;

    private List<AxisLabel> labelCache;

    private int offset = 0;

    private Boolean alignLabelsToInterval = false;

    public LinearAxis()
    {
        super();
        labelCache = new ArrayList<AxisLabel>();
        axisLabelSetting.setLabels(labelCache);
    }

    public Object getDataProvider()
    {
        return dataProvider;
    }

    public int getInterval()
    {
        return interval;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public void setValueFields(String[] valueFields)
    {
        this.valueFields = valueFields;
    }

    public void setAlignLabelsToInterval(Boolean alignLabelsToInterval)
    {
        this.alignLabelsToInterval = alignLabelsToInterval;
    }

    public void update()
    {
        if (valueFields == null || valueFields.length == 0)
            return;
        offset = 0;
        if (isJsonFormat)
        {
            onJSONProvider();
        }
        else
        {
            onCollectionProvider();
        }
        initRange();
        generateLables();
    }

    protected void onJSONProvider()
    {
        labelCache.clear();
        JSONArray sources = (JSONArray) dataProvider;
        itemCount = sources.size();
        describeData = new Object[itemCount * valueFields.length];
        for (int index = 0; index < itemCount; index++)
        {
            try
            {
                JSONObject data = sources.getJSONObject(index);
                for (int i = 0; i < valueFields.length; i++)
                {
                    describeData[offset++] = data.get(valueFields[i]);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public double getMaxValue()
    {
        return maxAxisValue;
    }

    private void onCollectionProvider()
    {
        labelCache.clear();
        List souces = (List) dataProvider;
        itemCount = souces.size();
        describeData = new Object[itemCount * valueFields.length];
        for (int index = 0; index < itemCount; index++)
        {
            Object object = souces.get(index);
            for (int i = 0; i < valueFields.length; i++)
            {
                describeData[offset++] = ReflectUtils.getProperty(object, valueFields[i]);
            }
        }
    }

    private void initRange()
    {
        minAxisValue = MathHelper.NULL_VALUE;
        maxAxisValue = -MathHelper.NULL_VALUE;
        for (int k = 0; k < describeData.length; k++)
        {
            double y = Double.parseDouble(String.valueOf((describeData[k])));
            updateRange(y);
        }
        if (baseZero)
            minAxisValue = 0;
    }

    private void updateRange(double y)
    {
        minAxisValue = Math.min(minAxisValue, y);
        maxAxisValue = Math.max(maxAxisValue, y);
    }

    private void generateLables()
    {
        double r = maxAxisValue - minAxisValue;
        double labelBase = labelMinimum - Math.floor((labelMinimum - minAxisValue) / interval) * interval;
        if (alignLabelsToInterval)
            labelBase = Math.ceil(labelBase / interval) * interval;
        double labelTop = maxAxisValue;
        int i = 0;
        double precision = 0;
        double decimal = Math.abs(interval) - Math.floor(Math.abs(interval));
        precision = decimal == 0 ? 1 : -Math.floor(Math.log(decimal) / Math.log(10));
        decimal = Math.abs(minAxisValue) - Math.floor(Math.abs(minAxisValue));
        precision = Math.max(precision, decimal == 0 ? 1 : -Math.floor(Math.log(decimal) / Math.log(10)));
        double roundBase = Math.pow(10, precision);
        double roundedValue = 0.0;
        for (i = (int) labelBase; i <= labelTop; i += interval)
        {
            roundedValue = Math.round(i * roundBase) / roundBase;
            labelCache.add(new AxisLabel((i - minAxisValue) / r, roundedValue, String.valueOf(roundedValue)));
        }
    }

    public void accept(CartesianChart chart)
    {
        chart.updateLinearAxis(this);
    }
}
