package com.lecast.smartchart.common;

import java.io.Serializable;

import android.graphics.Rect;

/**
 * @author vincent
 * 
 */
public class LocalPoint implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 2871102839599909324L;

    private float mX;

    private float mY;

    private Rect rect;

    protected Object value;

    public LocalPoint()
    {
    }

    public LocalPoint(float x, float y)
    {
        mX = x;
        mY = y;
    }

    public LocalPoint(float x, float y, Object value)
    {
        mX = x;
        mY = y;
        this.value = value;
    }

    public float getX()
    {
        return mX;
    }

    public float getY()
    {
        return mY;
    }

    public void setX(float x)
    {
        mX = x;
    }

    public void setY(float y)
    {
        mY = y;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Rect getRect()
    {
        return rect;
    }

    public void setRect(Rect rect)
    {
        this.rect = rect;
    }
}
