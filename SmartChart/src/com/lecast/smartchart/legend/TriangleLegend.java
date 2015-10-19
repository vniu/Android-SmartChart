package com.lecast.smartchart.legend;

import java.util.List;

import android.graphics.Canvas;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class TriangleLegend extends Legend
{

    public TriangleLegend(ChartBase chart)
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
            drawLegendHandler(canvas, series.getDisplayName());
        }
    }

    protected void drawLegendHandler(Canvas canvas, String name)
    {
        drawTriangle(canvas, new float[6]);
        super.drawLegendHandler(canvas, name);
    }

    private void drawTriangle(Canvas canvas, float[] path)
    {
        path[0] = x;
        path[1] = y - size - size / 2;
        path[2] = x - size;
        path[3] = y + size;
        path[4] = x + size;
        path[5] = path[3];
        drawPath(canvas, path, paint, true);
    }
}
