package com.lecast.smartchart.graphics.stroke;

import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;

import com.lecast.smartchart.graphics.GradientBase;

/**
 * @author vincent
 * 
 */
public class GradientStroke extends GradientBase implements IStroke
{

    private Color[] colors;

    private int[] ratios;

    private int[] alphas;

    private double angle;

    public void setAngle(double value)
    {
        angle = value / 180 * Math.PI;
    }

    public int getColor()
    {
        return 0;
    }

    public int getStrokeWidth()
    {
        return 0;
    }

    @Override
    public Cap getCap()
    {
        return null;
    }

    @Override
    public Join getJoin()
    {
        return null;
    }

}
