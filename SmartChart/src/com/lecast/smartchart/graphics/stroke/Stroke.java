package com.lecast.smartchart.graphics.stroke;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;

/**
 * @author vincent
 * 
 */
public class Stroke implements IStroke
{

    public static final Stroke GRAYSOLID = new Stroke(Cap.BUTT, Join.MITER, 4, null, 0, 0xFFE0E0E0, 1);

    public static final Stroke SOLID = new Stroke(Cap.BUTT, Join.MITER, 4, null, 0, 0xFFBACEDC, 1);

    public static final Stroke DASHED = new Stroke(Cap.ROUND, Join.BEVEL, 10, new float[] { 10, 10 }, 1, 0xFFBACEDC, 1);

    public static final Stroke DOTTED = new Stroke(Cap.ROUND, Join.BEVEL, 5, new float[] { 2, 10 }, 1, 0xFFBACEDC, 1);

    public static final Stroke YELLOW = new Stroke(Cap.BUTT, Join.MITER, 4, null, 0, 0xFFFE902A, 3);

    public static final Stroke BLUE = new Stroke(Cap.BUTT, Join.MITER, 4, null, 0, 0xFF49AEBE, 3);

    public static final Stroke GREEN = new Stroke(Cap.BUTT, Join.MITER, 4, null, 0, 0xFF78CD46, 3);

    private Cap mCap = Cap.BUTT;

    private Join mJoin = Join.MITER;

    private float mMiter = 4;

    private float[] mIntervals;

    private float mPhase;

    private int color = 0xFFBACEDC;

    private int strokeWidth = 1;

    public Stroke(Cap cap, Join join, float miter, float[] intervals, float phase, int color, int strokeWidth)
    {
        mCap = cap;
        mJoin = join;
        mMiter = miter;
        mIntervals = intervals;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Stroke(int color)
    {
        mCap = Cap.BUTT;
        mJoin = Join.MITER;
        mMiter = 4;
        mIntervals = null;
        this.color = color == 0 ? 0xFF49AEBE : color;
    }

    public Stroke(int color, int strokeWidth)
    {
        mCap = Cap.BUTT;
        mJoin = Join.MITER;
        mMiter = 4;
        mIntervals = null;
        this.color = color == 0 ? 0xFF49AEBE : color;
        this.strokeWidth = strokeWidth;
    }

    public Cap getCap()
    {
        return mCap;
    }

    public Join getJoin()
    {
        return mJoin;
    }

    public float getmMiter()
    {
        return mMiter;
    }

    public float[] getmIntervals()
    {
        return mIntervals;
    }

    public float getmPhase()
    {
        return mPhase;
    }

    public void setmCap(Cap mCap)
    {
        this.mCap = mCap;
    }

    public void setmJoin(Join mJoin)
    {
        this.mJoin = mJoin;
    }

    public void setmMiter(float mMiter)
    {
        this.mMiter = mMiter;
    }

    public void setmIntervals(float[] mIntervals)
    {
        this.mIntervals = mIntervals;
    }

    public void setmPhase(float mPhase)
    {
        this.mPhase = mPhase;
    }

    public int getColor()
    {
        return color;
    }

    public int getStrokeWidth()
    {
        return strokeWidth;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setStrokeWidth(int strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }

}
