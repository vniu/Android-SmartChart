package com.lecast.smartchart.graphics.stroke;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;

/**
 * @author vincent
 * 
 */
public class AxisStroke extends Stroke
{

    public static final AxisStroke DEFAULT = new AxisStroke(Cap.BUTT, Join.MITER, 4, 3, 3, 0xFFBACEDC, 0xFF3B4648);

    private int textColor = 0xFF3B4648;

    private int verAxisStrokeWidth = 2;

    private int horAxisStrokeWidth = 2;

    public AxisStroke(Cap cap, Join join, float miter, float[] intervals, float phase, int color, int strokeWidth)
    {
        super(cap, join, miter, intervals, phase, color, strokeWidth);
    }

    public AxisStroke(Cap cap, Join join, float miter, int verAxisStrokeWidth, int horAxisStrokeWidth, int color,
                      int textColor)
    {
        super(cap, join, miter, null, 0, color, 2);
        this.textColor = textColor;
        this.horAxisStrokeWidth = horAxisStrokeWidth;
        this.verAxisStrokeWidth = verAxisStrokeWidth;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public int getVerAxisStrokeWidth()
    {
        return verAxisStrokeWidth;
    }

    public void setVerAxisStrokeWidth(int verAxisStrokeWidth)
    {
        this.verAxisStrokeWidth = verAxisStrokeWidth;
    }

    public int getHorAxisStrokeWidth()
    {
        return horAxisStrokeWidth;
    }

    public void setHorAxisStrokeWidth(int horAxisStrokeWidth)
    {
        this.horAxisStrokeWidth = horAxisStrokeWidth;
    }

}
