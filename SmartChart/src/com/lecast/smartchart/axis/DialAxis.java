package com.lecast.smartchart.axis;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;

import com.lecast.smartchart.utils.MathHelper;

public class DialAxis extends AxisBase implements IAxis
{

    private double minValue = 0;

    private double maxValue = 100;

    private float minAngle = 315; // 330 //405

    private float maxAngle = 45; // 30 //135

    protected List<AxisLabel> axisLabels = null;

    private double minorTicks = MathHelper.NULL_VALUE;

    private double majorTicks = MathHelper.NULL_VALUE;

    private float centerX;

    private float centerY;

    private float radius = 100;

    private Paint paint = new Paint();

    private RectF oval;

    private float[] warnRanges = new float[] { 30, 70 };

    private int interval = 10;

    private int multipleSpacer = 2;

    private int gradientColors[] = { 0xffffffff, 0xFFF4D27C };

    public DialAxis()
    {

    }

    public void setMinValue(float minValue)
    {
        this.minValue = minValue;
    }

    public int getInterval()
    {
        return interval;
    }

    public int getMultipleSpacer()
    {
        return multipleSpacer;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }

    public void setMultipleSpacer(int multipleSpacer)
    {
        this.multipleSpacer = multipleSpacer;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public void setOval(RectF oval)
    {
        this.oval = oval;
    }

    public void drawAxisHandler(Canvas canvas)
    {
        if (minValue == maxValue)
        {
            minValue = minValue * 0.5;
            maxValue = maxValue * 1.5;
        }
        if (minorTicks == MathHelper.NULL_VALUE)
        {
            minorTicks = (maxValue - minValue) / (interval * multipleSpacer);
        }
        if (majorTicks == MathHelper.NULL_VALUE)
        {
            majorTicks = (maxValue - minValue) / interval;
        }
        paint.setAntiAlias(true);
        float shortRadius = radius * .9f;
        float longRadius = radius * 1.05f;
        paint.setColor(0xffA2D87F);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawArc(oval, maxAngle, 360, true, paint);
        paint.setStyle(Style.FILL);
        Shader gradient =
                    new RadialGradient(centerX, centerY, shortRadius, gradientColors[0], gradientColors[1],
                                TileMode.MIRROR);
        paint.setShader(gradient);
        oval.left = oval.left + 6;
        oval.top = oval.top + 6;
        oval.right = oval.right - 6;
        oval.bottom = oval.bottom - 6;
        canvas.drawArc(oval, maxAngle, 360, true, paint);
        paint.setShader(null);
        paint.setColor(Color.RED);
        paint.setStyle(Style.FILL);
        paint.setStrokeWidth(2);
        drawTicks(canvas, longRadius, radius, minorTicks, false);
        drawTicks(canvas, longRadius, shortRadius, majorTicks, true);

    }

    public void setCenterHandler(float centerX, float centerY)
    {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    private double getAngleForValue(double value)
    {
        double angleDiff = maxAngle - minAngle;
        double diff = maxValue - minValue;
        return Math.toRadians(minAngle + (value - minValue) * angleDiff / diff);
    }

    private float getAngleForWarning(float value)
    {
        double angleDiff = maxAngle - minAngle;
        double diff = maxValue - minValue;
        return Float.valueOf(String.valueOf(value * angleDiff / diff));
    }

    public void setWarnRanges(float[] warnRanges)
    {
        this.warnRanges = warnRanges;
    }

    private void drawTicks(Canvas canvas, double longRadius, double shortRadius, double ticks, boolean labels)
    {
        for (double i = minValue; i <= maxValue; i += ticks)
        {
            double angle = getAngleForValue(i);
            double sinValue = Math.sin(angle);
            double cosValue = Math.cos(angle);
            int startX = Math.round(centerX + (float) (shortRadius * sinValue));
            int startY = Math.round(centerY + (float) (shortRadius * cosValue));
            int endX = Math.round(centerX + (float) (longRadius * sinValue));
            int endY = Math.round(centerY + (float) (longRadius * cosValue));
            canvas.drawLine(startX, startY, endX, endY, paint);
            if (labels)
            {
                paint.setTextAlign(Align.LEFT);
                if (startX <= endX)
                {
                    paint.setTextAlign(Align.RIGHT);
                }
                String text = i + "";
                if (Math.round(i) == (long) i)
                {
                    text = (long) i + "";
                }
                canvas.drawText(text, startX, startY, paint);
            }
        }
    }

    public double getMinValue()
    {
        return minValue;
    }

    public double getMaxValue()
    {
        return maxValue;
    }

    public float getMinAngle()
    {
        return minAngle;
    }

    public float getMaxAngle()
    {
        return maxAngle;
    }

    public void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }

    public void setMinAngle(float minAngle)
    {
        this.minAngle = minAngle;
    }

    public void setMaxAngle(float maxAngle)
    {
        this.maxAngle = maxAngle;
    }

}
