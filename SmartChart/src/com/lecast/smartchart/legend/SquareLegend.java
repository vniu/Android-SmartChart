package com.lecast.smartchart.legend;

import android.graphics.Canvas;

import com.lecast.smartchart.chart.ChartBase;

/**
 * @author vincent
 * 
 */
public class SquareLegend extends Legend
{

    public SquareLegend(ChartBase chart)
    {
        super(chart);
    }

    protected void drawLegendHandler(Canvas canvas, String name)
    {
        paint.setAntiAlias(true);
        drawSquare(canvas);
        super.drawLegendHandler(canvas, name);
    }

    private void drawSquare(Canvas canvas)
    {
        canvas.drawRect(x - size, y - size, x + size, y + size, paint);
    }
}
