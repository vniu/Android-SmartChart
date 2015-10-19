package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;

import com.lecast.smartchart.series.ISeries;
import com.lecast.smartchart.series.PieSeries;

/**
 * @author vincent
 * 
 */
public class PieChart extends RoundChart
{

    public PieChart(Context context)
    {
        super(context);
    }

    protected void drawSeriesHandler(Canvas canvas)
    {
        for (ISeries series : mSeries)
        {
            PieSeries pieSeries = (PieSeries) series;
            setSeriesProperty(pieSeries);
            pieSeries.setDataProvider(dataProvider);
            pieSeries.onDrawHandler(canvas);
        }
    }

    public void addSeries(ISeries series)
    {
        if (mSeries.size() > 0)
        {
            return;
        }
        super.addSeries(series);
    }
}
