package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.series.CartesianSeries;
import com.lecast.smartchart.series.ColumnSeries;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class CombinedChart extends CartesianChart
{

    public CombinedChart(Context context)
    {
        super(context);
    }

    public void drawSeriesHandler(Canvas canvas)
    {
        int columnSeriesSize = getColumnSeriesSize();
        int columnSeriesIndex = 0;
        for (ISeries series : mSeries)
        {
            if (series instanceof ColumnSeries)
            {
                ((ColumnSeries) series).setSeriesSize(columnSeriesSize);
                ((ColumnSeries) series).setSeriesIndex(columnSeriesIndex);
                columnSeriesIndex++;
            }
            setSeriesProperty((CartesianSeries) series);
            series.onDrawHandler(canvas);
        }
    }

    private int getColumnSeriesSize()
    {
        int count = 0;
        for (ISeries series : mSeries)
        {
            if (series instanceof ColumnSeries)
            {
                count++;
            }
        }
        return count;
    }

}
