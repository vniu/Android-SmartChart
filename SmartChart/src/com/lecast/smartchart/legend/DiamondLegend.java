package com.lecast.smartchart.legend;

import java.util.List;

import android.graphics.Canvas;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public class DiamondLegend extends Legend
{

    public DiamondLegend(ChartBase chart)
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
        drawDiamond(canvas, new float[8]);
        super.drawLegendHandler(canvas, name);
    }

    private void drawDiamond(Canvas canvas, float[] path)
    {
        path[0] = x;
        path[1] = y - size;
        path[2] = x - size;
        path[3] = y;
        path[4] = x;
        path[5] = y + size;
        path[6] = x + size;
        path[7] = y;
        drawPath(canvas, path, paint, true);
    }
}
