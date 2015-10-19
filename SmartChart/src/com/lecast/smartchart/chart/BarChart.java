package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.LinearAxis;
import com.lecast.smartchart.series.BarSeries;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class BarChart extends CartesianChart
{

    public BarChart(Context context)
    {
        super(context);
    }

    public void drawSeriesHandler(Canvas canvas)
    {
        int count = 0;
        int seriesSize = mSeries.size();
        for (ISeries series : mSeries)
        {
            BarSeries barSeries = (BarSeries) series;
            barSeries.setSeriesIndex(count);
            barSeries.setSeriesSize(seriesSize);
            setSeriesProperty(barSeries);
            count++;
            series.onDrawHandler(canvas);
        }
    }

    public void updateCategoryAxis(CategoryAxis categoryAxis)
    {
        categoryAxis.setBaseZero(false);
    }

    public void updateLinearAxis(LinearAxis linearAxis)
    {

    }
}
