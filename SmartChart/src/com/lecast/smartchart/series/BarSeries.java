package com.lecast.smartchart.series;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.lecast.smartchart.axis.AxisLabel;
import com.lecast.smartchart.axis.AxisLabelSetting;
import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.graphics.stroke.IStroke;

public class BarSeries extends ColumnSeries
{

    public BarSeries(Context context, IStroke stroke)
    {
        super(context, stroke);
    }

    public BarSeries(Context context)
    {
        super(context);
    }

    protected void drawColumnBarHandler(Canvas canvas)
    {
        AxisLabelSetting horAxisLabelSetting = categoryAxis.getAxisLabelSetting();
        List<AxisLabel> axisLables = horAxisLabelSetting.getLabels();
        int size = axisLables.size();
        float stopX, stopY;
        double position = categoryAxis.isBaseZero() ? axisLables.get(1).getPosition() : axisLables.get(0).getPosition();
        float perUnitWidth = (float) (position * areaRect.height());
        float totalSpaceWidth = 2 * barSpaceWidth;
        float perColumnWidth = (perUnitWidth - totalSpaceWidth) / seriesSize;
        float top, right, bottom;
        for (int index = 0; index < size; index++)
        {
            stopX =
                        (float) (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect.width() / verAxis
                                    .getMaxValue())) + paddingLeft;

            stopY =
                        areaRect.bottom
                                    - Float.parseFloat(String.valueOf(axisLables.get(index).getPosition()
                                                * areaRect.height()));
            bottom =
                        areaRect.bottom
                                    - ((barSpaceWidth * (2 * index + 1) + (seriesIndex + seriesSize * index)
                                                * perColumnWidth));
            top = bottom - perColumnWidth;
            right = stopX;// left + perColumnWidth;
            canvas.drawRect(areaRect.left, top, right, bottom, paint);
            int rectLeft = (int) ((areaRect.left + offsetX) * scale);
            int rectRight = (int) ((right + offsetX) * scale);
            int rectTop = (int) Math.abs((top + offsetY) * scale);
            int rectBottom = (int) (rectTop + perColumnWidth * scale);
            addClickPoint(index, new Rect(rectLeft, rectTop, rectRight, rectBottom), stopX, stopY);
        }
    }

    protected void addClickPoint(int index, Rect rect, float x, float y)
    {
        Object object = null;
        try
        {
            object = isJsonFormat ? jsonArraySources.get(index) : dataSources.get(index);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        LocalPoint touchPoint =
                    new LocalPoint(Math.abs((x + offsetX) * scale), Math.abs((y + offsetY) * scale), object);
        touchPoint.setRect(rect);
        Log.v("Click Point add", touchPoint.getX() + "+++++++++" + touchPoint.getY());
        Log.v("Click Point add", scale + "----scale----++" + offsetX + "---offsetX----++" + offsetY + "---offsetY-----");
        pointMapping.addPoint(touchPoint);
    }
}
