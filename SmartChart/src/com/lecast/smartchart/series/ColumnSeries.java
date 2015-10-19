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
import com.lecast.smartchart.graphics.stroke.Stroke;

public class ColumnSeries extends CartesianSeries
{

    protected int seriesIndex = 0;

    protected int seriesSize = 1;

    public final static String Type = "Column";

    protected float maxWidth;

    protected float barSpaceWidth = 10;

    public ColumnSeries(Context context, IStroke stroke)
    {
        super(context);
        if (stroke == null)
        {
            this.stroke = Stroke.YELLOW;
            return;
        }
        this.stroke = stroke;
    }

    public ColumnSeries(Context context)
    {
        super(context);
        this.stroke = Stroke.YELLOW;
    }

    public void onDrawHandler(Canvas canvas)
    {
        super.onDrawHandler(canvas);
        drawColumnBarHandler(canvas);
    }

    protected void drawColumnBarHandler(Canvas canvas)
    {
        AxisLabelSetting horAxisLabelSetting = categoryAxis.getAxisLabelSetting();
        List<AxisLabel> axisLables = horAxisLabelSetting.getLabels();
        int size = axisLables.size();
        float stopX, stopY;
        double position = categoryAxis.isBaseZero() ? axisLables.get(1).getPosition() : axisLables.get(0).getPosition();
        float perUnitWidth = (float) (position * areaRect.width());
        // float perColumnWidth = (perUnitWidth - 2 * barSpaceWidth) /
        // // seriesSize;
        float totalSpaceWidth = 2 * barSpaceWidth;
        float perColumnWidth = (perUnitWidth - totalSpaceWidth) / seriesSize;
        float left, top, right;
        for (int index = 0; index < size; index++)
        {
            stopX =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            stopY =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / verAxis.getMaxValue())));
            left = barSpaceWidth * (2 * index + 1) + (seriesIndex + seriesSize * index) * perColumnWidth + paddingLeft;// +
            right = left + perColumnWidth;
            top = stopY;
            canvas.drawRect(left, top, right, areaRect.bottom, paint);
            int rectLeft = (int) Math.abs((left + offsetX) * scale);
            int rectTop = (int) ((top + offsetY) * scale);
            int rectRight = (int) (rectLeft + perColumnWidth * scale);
            int rectBottom = (int) (int) ((areaRect.bottom + offsetY) * scale);
            addClickPoint(index, new Rect(rectLeft, rectTop, rectRight, rectBottom), stopX, stopY);
            Log.v("ColumnSeries", stopX + "stopX:" + right + "right:" + left + "lfet:" + top + "top:");
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

    // private void drawColumn()
    // {
    //
    // float perUnitWidth = (float) (axisLables.get(0).getPosition() *
    // areaRect.width());
    // // float perColumnWidth = (perUnitWidth - 2 * barSpaceWidth) /
    // // // seriesSize;
    //
    // float totalSpaceWidth = size * barSpaceWidth;
    // float perColumnWidth = (areaRect.width() - perUnitWidth -
    // totalSpaceWidth) / (size * seriesSize);
    // float left, top, bottom, right;
    // for (int index = 0; index < size; index++)
    // {
    // stopX =
    // Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() *
    // areaRect.width()))
    // + paddingLeft;
    // stopY =
    // (float) (areaRect.bottom -
    // (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
    // .height() / verAxis.getMaxValue())));
    // left = barSpaceWidth * (index + 1) + (seriesIndex + seriesSize * index) *
    // perColumnWidth + paddingLeft;// +
    // // (seriesSize
    // // *
    // // perColumnWidth)
    // // /
    // // 2;
    // right = left + perColumnWidth;
    // top = stopY;
    // canvas.drawRect(left, top, right, areaRect.bottom, paint);
    // Log.v("ColumnSeries", stopX + "stopX:" + right + "right:" + left +
    // "lfet:" + top + "top:");
    // }

    public void setSeriesIndex(int seriesIndex)
    {
        this.seriesIndex = seriesIndex;
    }

    public void setSeriesSize(int seriesSize)
    {
        this.seriesSize = seriesSize;
    }

}
