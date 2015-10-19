package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.common.RoundPointMapping;
import com.lecast.smartchart.series.RoundSeries;

/**
 * @author vincent
 * 
 */
public class RoundChart extends ChartBase
{

    protected float centerX;

    protected float centerY;

    protected float radius = 150;

    protected RectF areaRect;

    public RoundChart(Context context)
    {
        super(context);
        pointMapping = new RoundPointMapping();
    }

    public void run(Canvas canvas)
    {
        if (dataProvider != null)
        {
            commitedCenterPoint(canvas);
            drawSeriesHandler(canvas);
        }
        super.run(canvas);
    }

    protected void commitedCenterPoint(Canvas canvas)
    {
        int width = chartView.getWidth();// canvas.getWidth();
        int height = chartView.getHeight(); // canvas.getHeight();
        centerX = (width - paddingLeft - paddingRight) / 2 + paddingLeft;
        centerY = (height - paddingTop - paddingBottom) / 2 + paddingTop;
        areaRect = new RectF(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
    }

    protected void drawSeriesHandler(Canvas canvas)
    {

    }

    public LocalPoint getPointForScreenCoordinate(LocalPoint screenPoint)
    {
        RoundPointMapping roundPointMapping = (RoundPointMapping) pointMapping;
        return roundPointMapping.getClickPoint(screenPoint);
    }

    protected void setSeriesProperty(RoundSeries series)
    {
        series.setPadding(paddingLeft, paddingTop, paddingBottom, paddingRight);
        series.setChartRepaintHandler(chartRepaintHandler);
        series.setOffset(offsetX, offsetY);
        series.setCenter(centerX, centerY);
        series.setScale(scale);
        series.setPiontMapping(pointMapping);
        series.setRadius(radius);
        series.setDataProvider(dataProvider);
        series.setFontSize(fontSize);
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

}
