package com.lecast.smartchart.legend;

import java.util.List;

import android.graphics.Canvas;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class CircleLegend extends Legend
{

    public CircleLegend(ChartBase chart)
    {
        super(chart);
    }

    public void executeDrawHandler(Canvas canvas)
    {
        List<ISeries> seriess = chart.getSeries();
        if (seriess == null)
            return;
        for (ISeries series : seriess)
        {
            if (drawPieLegend(canvas, series))
            {
                return;
            }
            paint.setColor(series.getStroke().getColor());
            paint.setAntiAlias(true);
            drawLegendHandler(canvas, series.getDisplayName());
        }
    }

    protected void drawLegendHandler(Canvas canvas, String name)
    {
        drawCircle(canvas);
        super.drawLegendHandler(canvas, name);
    }

    private void drawCircle(Canvas canvas)
    {
        canvas.drawCircle(x, y, size, paint);
    }
}
