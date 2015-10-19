package com.lecast.smartchart.legend;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.series.ISeries;
import com.lecast.smartchart.series.PieSeries;
import com.lecast.smartchart.utils.DrawUtils;

/**
 * @author vincent
 * 
 */
public class Legend
{

    protected ChartBase chart;

    protected static final float SIZE = 8;

    protected float size = SIZE;

    protected Paint paint = new Paint();

    protected float x = 10, y = 10;

    protected int fontSize;

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public ChartBase getChart()
    {
        return chart;
    }

    public void setChart(ChartBase chart)
    {
        this.chart = chart;
    }

    public Legend(ChartBase chart)
    {
        this.chart = chart;
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

    public void setLegendPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    protected boolean drawPieLegend(Canvas canvas, ISeries series)
    {
        if (series instanceof PieSeries)
        {
            PieSeries pieSeries = (PieSeries) series;
            int[] colors = pieSeries.getColors();
            int size = colors.length;
            for (int index = 0; index < size; index++)
            {
                paint.setColor(colors[index]);
                drawLegendHandler(canvas, pieSeries.getCategoryNames()[index]);
            }
            return true;
        }
        return false;

    }

    protected void drawLegendHandler(Canvas canvas, String name)
    {
        Rect rect = null;
        if (name != null)
        {
            rect = DrawUtils.getTextRect(name, fontSize);
            paint.setTextSize(fontSize);
            canvas.drawText(name, x + 2 * SIZE, y + (rect.height() - 2 * SIZE) / 2, paint);
        }
        x = x + size + (rect == null ? 10 : rect.width() + 10 + SIZE);
        if (x > chart.getChartView().getWidth())// canvas.getWidth())
            y += 20;
    }

    protected void drawPath(Canvas canvas, float[] points, Paint paint, boolean circular)
    {
        paint.setAntiAlias(true);
        Path path = new Path();
        int height = chart.getChartView().getHeight();// canvas.getHeight();
        int width = chart.getChartView().getWidth();// canvas.getWidth();

        float[] tempDrawPoints;
        if (points.length < 4)
        {
            return;
        }
        tempDrawPoints = calculateDrawPoints(points[0], points[1], points[2], points[3], height, width);
        path.moveTo(tempDrawPoints[0], tempDrawPoints[1]);
        path.lineTo(tempDrawPoints[2], tempDrawPoints[3]);
        for (int i = 4; i < points.length; i += 2)
        {
            if ((points[i - 1] < 0 && points[i + 1] < 0) || (points[i - 1] > height && points[i + 1] > height))
            {
                continue;
            }
            tempDrawPoints = calculateDrawPoints(points[i - 2], points[i - 1], points[i], points[i + 1], height, width);
            if (!circular)
            {
                path.moveTo(tempDrawPoints[0], tempDrawPoints[1]);
            }
            path.lineTo(tempDrawPoints[2], tempDrawPoints[3]);
        }
        if (circular)
        {
            path.lineTo(points[0], points[1]);
        }
        canvas.drawPath(path, paint);
    }

    private static float[] calculateDrawPoints(float p1x, float p1y, float p2x, float p2y, int screenHeight,
                int screenWidth)
    {
        float drawP1x;
        float drawP1y;
        float drawP2x;
        float drawP2y;
        if (p1y > screenHeight)
        {
            float m = (p2y - p1y) / (p2x - p1x);
            drawP1x = (screenHeight - p1y + m * p1x) / m;
            drawP1y = screenHeight;
            if (drawP1x < 0)
            {
                drawP1x = 0;
                drawP1y = p1y - m * p1x;
            }
            else if (drawP1x > screenWidth)
            {
                drawP1x = screenWidth;
                drawP1y = m * screenWidth + p1y - m * p1x;
            }
        }
        else if (p1y < 0)
        {
            float m = (p2y - p1y) / (p2x - p1x);
            drawP1x = (-p1y + m * p1x) / m;
            drawP1y = 0;
            if (drawP1x < 0)
            {
                drawP1x = 0;
                drawP1y = p1y - m * p1x;
            }
            else if (drawP1x > screenWidth)
            {
                drawP1x = screenWidth;
                drawP1y = m * screenWidth + p1y - m * p1x;
            }
        }
        else
        {
            drawP1x = p1x;
            drawP1y = p1y;
        }

        if (p2y > screenHeight)
        {
            float m = (p2y - p1y) / (p2x - p1x);
            drawP2x = (screenHeight - p1y + m * p1x) / m;
            drawP2y = screenHeight;
            if (drawP2x < 0)
            {
                drawP2x = 0;
                drawP2y = p1y - m * p1x;
            }
            else if (drawP2x > screenWidth)
            {
                drawP2x = screenWidth;
                drawP2y = m * screenWidth + p1y - m * p1x;
            }
        }
        else if (p2y < 0)
        {
            float m = (p2y - p1y) / (p2x - p1x);
            drawP2x = (-p1y + m * p1x) / m;
            drawP2y = 0;
            if (drawP2x < 0)
            {
                drawP2x = 0;
                drawP2y = p1y - m * p1x;
            }
            else if (drawP2x > screenWidth)
            {
                drawP2x = screenWidth;
                drawP2y = m * screenWidth + p1y - m * p1x;
            }
        }
        else
        {
            drawP2x = p2x;
            drawP2y = p2y;
        }

        return new float[] { drawP1x, drawP1y, drawP2x, drawP2y };
    }
}
