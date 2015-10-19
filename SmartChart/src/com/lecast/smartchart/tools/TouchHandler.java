package com.lecast.smartchart.tools;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lecast.smartchart.chart.ChartBase;

public class TouchHandler implements ITouchHandler
{

    private float oldX;

    private float oldY;

    private float oldX2;

    private float oldY2;

    private RectF zoomR = new RectF();

    private Pan mPan;

    private Zoom mPinchZoom;

    /** The chart view. */
    private View chartView;

    private long startTime;

    public TouchHandler(View view, ChartBase chart)
    {
        chartView = view;
        mPan = new Pan(chart);
        mPinchZoom = new Zoom(chart, true, 1);
    }

    public boolean handleTouch(MotionEvent event)
    {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_MOVE)
        {
            if (oldX >= 0 || oldY >= 0)
            {
                float newX = event.getX(0);
                float newY = event.getY(0);
                if (event.getPointerCount() > 1 && (oldX2 >= 0 || oldY2 >= 0))// &&
                                                                              // mRenderer.isZoomEnabled())
                {
                    float newX2 = event.getX(1);
                    float newY2 = event.getY(1);
                    float newDeltaX = Math.abs(newX - newX2);
                    float newDeltaY = Math.abs(newY - newY2);
                    float oldDeltaX = Math.abs(oldX - oldX2);
                    float oldDeltaY = Math.abs(oldY - oldY2);
                    float zoomRate = 1;

                    float tan1 = Math.abs(newY - oldY) / Math.abs(newX - oldX);
                    float tan2 = Math.abs(newY2 - oldY2) / Math.abs(newX2 - oldX2);
                    if (tan1 <= 0.25 && tan2 <= 0.25)
                    {
                        // horizontal pinch zoom, |deltaY| / |deltaX| is [0 ~
                        // 0.25], 0.25 is
                        // the approximate value of tan(PI / 12)
                        zoomRate = newDeltaX / oldDeltaX;
                        applyZoom(zoomRate, Zoom.ZOOM_AXIS_X);
                    }
                    else if (tan1 >= 3.73 && tan2 >= 3.73)
                    {
                        // pinch zoom vertically, |deltaY| / |deltaX| is [3.73 ~
                        // infinity],
                        // 3.732 is the approximate value of tan(PI / 2 - PI /
                        // 12)
                        zoomRate = newDeltaY / oldDeltaY;
                        applyZoom(zoomRate, Zoom.ZOOM_AXIS_Y);
                    }
                    else
                    {
                        // pinch zoom diagonally
                        if (Math.abs(newX - oldX) >= Math.abs(newY - oldY))
                        {
                            zoomRate = newDeltaX / oldDeltaX;
                        }
                        else
                        {
                            zoomRate = newDeltaY / oldDeltaY;
                        }
                        applyZoom(zoomRate, Zoom.ZOOM_AXIS_XY);
                    }
                    oldX2 = newX2;
                    oldY2 = newY2;
                }
                else if (true)// mRenderer.isPanEnabled())
                {
                    mPan.apply(oldX, oldY, newX, newY);
                    oldX2 = 0;
                    oldY2 = 0;
                }
                oldX = newX;
                oldY = newY;
                // chartView.invalidate();
                // graphicalView.repaint();
                return true;
            }
        }
        else if (action == MotionEvent.ACTION_DOWN)
        {
            startTime = System.currentTimeMillis();
            oldX = event.getX(0);
            oldY = event.getY(0);
            if (zoomR.contains(oldX, oldY))
            {
                if (oldX < zoomR.left + zoomR.width() / 3)
                {
                    // chartView.zoomIn();
                }
                else if (oldX < zoomR.left + zoomR.width() * 2 / 3)
                {
                    // chartView.zoomOut();
                }
                else
                {
                    // chartView.zoomReset();
                }
                return true;
            }
        }
        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)
        {
            long endTime = System.currentTimeMillis();

            oldX = 0;
            oldY = 0;
            oldX2 = 0;
            oldY2 = 0;
            if (action == MotionEvent.ACTION_POINTER_UP)
            {
                oldX = -1;
                oldY = -1;
            }
            if (endTime - startTime < 300)
                return false;
            return true;
        }
        return false;// ¿ÉÒÔµ¥»÷ !mRenderer.isClickEnabled();
    }

    private void applyZoom(float zoomRate, int axis)
    {
        if (zoomRate > 0.9 && zoomRate < 1.1)
        {
            Log.v("ZOOM RATE", zoomRate + "");
            mPinchZoom.setZoomRate(zoomRate);
            mPinchZoom.apply(axis);
        }
    }

    /**
     * Adds a new zoom listener.
     * 
     * @param listener
     *            zoom listener
     */
    public void addZoomListener(ZoomListener listener)
    {
        if (mPinchZoom != null)
        {
            mPinchZoom.addZoomListener(listener);
        }
    }

    /**
     * Removes a zoom listener.
     * 
     * @param listener
     *            zoom listener
     */
    public void removeZoomListener(ZoomListener listener)
    {
        if (mPinchZoom != null)
        {
            mPinchZoom.removeZoomListener(listener);
        }
    }

    /**
     * Adds a new pan listener.
     * 
     * @param listener
     *            pan listener
     */
    public void addPanListener(PanListener listener)
    {
        if (mPan != null)
        {
            mPan.addPanListener(listener);
        }
    }

    /**
     * Removes a pan listener.
     * 
     * @param listener
     *            pan listener
     */
    public void removePanListener(PanListener listener)
    {
        if (mPan != null)
        {
            mPan.removePanListener(listener);
        }
    }
}