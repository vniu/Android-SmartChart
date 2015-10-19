package com.lecast.smartchart.tools;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.lecast.smartchart.chart.ChartBase;

/**
 * @author vincent The zoom tool.
 */
public class Zoom
{

    private ChartBase chart;

    /** A flag to be used to know if this is a zoom in or out. */
    private boolean mZoomIn;

    /** The zoom rate. */
    private float mZoomRate;

    /** The zoom listeners. */
    private List<ZoomListener> mZoomListeners = new ArrayList<ZoomListener>();

    /** Zoom limits reached on the X axis. */
    private boolean limitsReachedX = false;

    /** Zoom limits reached on the Y axis. */
    private boolean limitsReachedY = false;

    /** Zoom on X axis and Y axis */
    public static final int ZOOM_AXIS_XY = 0;

    /** Zoom on X axis independently */
    public static final int ZOOM_AXIS_X = 1;

    /** Zoom on Y axis independently */
    public static final int ZOOM_AXIS_Y = 2;

    public static final float MIN_SCALE = 0.70f;

    public static final float MAX_SCALE = 1.3f;

    public static final float NORMAL_SCALE = 1.0f;

    /**
     * Builds the zoom tool.
     * 
     * @param chart
     *            the chart
     * @param in
     *            zoom in or out
     * @param rate
     *            the zoom rate
     */
    public Zoom(ChartBase chart, boolean in, float rate)
    {
        this.chart = chart;
        mZoomIn = in;
        setZoomRate(rate);
    }

    /**
     * Sets the zoom rate.
     * 
     * @param rate
     */
    public void setZoomRate(float rate)
    {
        mZoomRate = rate;
    }

    /**
     * Apply the zoom.
     */
    public void apply(int zoom_axis)
    {
        if (mZoomIn)
        {
            Log.v("mZoomRate result", mZoomRate + "");
            // if (chart.getScale() > MAX_SCALE)
            // chart.setScale(MAX_SCALE);
            float result = chart.getScale() * mZoomRate;
//            if (result < 0.7)
//                result = 0.6f;
            chart.setScale(result);
        }
        else
        {
            if (chart.getScale() < MIN_SCALE)
                chart.setScale(MIN_SCALE);

            chart.setScale(chart.getScale() / mZoomRate);
        }
        notifyZoomListeners(new ZoomEvent(mZoomIn, mZoomRate));
    }

    /**
     * Notify the zoom listeners about a zoom change.
     * 
     * @param e
     *            the zoom event
     */
    private synchronized void notifyZoomListeners(ZoomEvent e)
    {
        for (ZoomListener listener : mZoomListeners)
        {
            listener.zoomApplied(e);
        }
    }

    /**
     * Notify the zoom listeners about a zoom reset.
     */
    public synchronized void notifyZoomResetListeners()
    {
        for (ZoomListener listener : mZoomListeners)
        {
            listener.zoomReset();
        }
    }

    /**
     * Adds a new zoom listener.
     * 
     * @param listener
     *            zoom listener
     */
    public synchronized void addZoomListener(ZoomListener listener)
    {
        mZoomListeners.add(listener);
    }

    /**
     * Removes a zoom listener.
     * 
     * @param listener
     *            zoom listener
     */
    public synchronized void removeZoomListener(ZoomListener listener)
    {
        mZoomListeners.remove(listener);
    }

}
