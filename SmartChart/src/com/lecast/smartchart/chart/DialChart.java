package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.lecast.smartchart.axis.DialAxis;
import com.lecast.smartchart.series.DialSeries;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class DialChart extends RoundChart
{

    private float dialValue = 0;

    private DialAxis dialAxis;

    public DialChart(Context context, DialAxis dialAxis)
    {
        super(context);
        if (dialAxis == null)
        {
            throw new RuntimeException("DialAxis should be not null value!");
        }
        this.dialAxis = dialAxis;
    }

    public DialChart(Context context)
    {
        super(context);

    }

    public void run(Canvas canvas)
    {
        drawFinished = true;
        if (dialValue == 0)
            return;
        commitedCenterPoint(canvas);
        setAxisPropertyHandler();
        dialAxis.drawAxisHandler(canvas);
        drawSeriesHandler(canvas);

    }

    private void setAxisPropertyHandler()
    {
        dialAxis.setCenterHandler(centerX, centerY);
        dialAxis.setRadius(radius);
        float lastRadius = radius * 1.1f;
        RectF oval = new RectF(centerX - lastRadius, centerY - lastRadius, centerX + lastRadius, centerY + lastRadius);
        dialAxis.setOval(oval);
    }

    public void setDataProvider(Object dataProvider)
    {
        this.dialValue = (Float) dataProvider;
        if (drawFinished && chartRepaintHandler != null)
        {
            chartRepaintHandler.repaintHandler();
        }
    }

    protected void drawSeriesHandler(Canvas canvas)
    {
        for (ISeries series : mSeries)
        {
            DialSeries dialSeries = (DialSeries) series;
            dialSeries.setMinValue(dialAxis.getMinValue());
            dialSeries.setMaxValue(dialAxis.getMaxValue());
            dialSeries.setMaxAngle(dialAxis.getMaxAngle());
            dialSeries.setMinAngle(dialAxis.getMinAngle());
            dialSeries.setCurrtValue(dialValue);
            setSeriesProperty(dialSeries);
            dialSeries.onDrawHandler(canvas);
        }
    }

    public float getDialValue()
    {
        return dialValue;
    }

    public void setDialValue(float dialValue)
    {
        this.dialValue = dialValue;
        if (drawFinished)
        {
            if (mSeries.size() == 0)
                return;
            DialSeries dialSeries = (DialSeries) mSeries.get(0);
            dialSeries.dataChange();
        }
    }

    public void setDialAxis(DialAxis dialAxis)
    {
        this.dialAxis = dialAxis;
    }
}
