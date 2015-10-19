package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.series.AreaSeries;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class AreaChart extends CartesianChart
{

    public AreaChart(Context context)
    {
        super(context);
    }

    protected void drawSeriesHandler(Canvas canvas)
    {
        for (ISeries series : mSeries)
        {
            AreaSeries lineSeries = (AreaSeries) series;
            setSeriesProperty(lineSeries);
            series.onDrawHandler(canvas);
        }
    }
}
