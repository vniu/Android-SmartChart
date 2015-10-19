package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.series.ColumnSeries;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class ColumnChart extends CartesianChart
{

    public ColumnChart(Context context)
    {
        super(context);
    }

    public void drawSeriesHandler(Canvas canvas)
    {
        int count = 0;
        int seriesSize = mSeries.size();
        for (ISeries series : mSeries)
        {
            ColumnSeries columnSeries = (ColumnSeries) series;
            columnSeries.setSeriesIndex(count);
            columnSeries.setSeriesSize(seriesSize);
            setSeriesProperty(columnSeries);
            count++;
            series.onDrawHandler(canvas);
        }
    }
}
