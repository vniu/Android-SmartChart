package com.lecast.smartchart.tools;

import java.util.ArrayList;
import java.util.List;

import com.lecast.smartchart.chart.ChartBase;

public class Pan
{

    private ChartBase chart;

    private List<PanListener> mPanListeners = new ArrayList<PanListener>();

    private boolean limitsReachedX = false;

    private boolean limitsReachedY = false;

    public Pan(ChartBase chart)
    {
        this.chart = chart;
    }

    public void apply(float oldX, float oldY, float newX, float newY)
    {

        float offsetX = Math.abs(newX - oldX);
        float offsetY = Math.abs(newY - oldY);
        if (offsetX > 5 || offsetY > 5)
        {
            chart.setOffsetX(chart.getOffsetX() + (int) (newX - oldX));
            chart.setOffsetY(chart.getOffsetY() + (int) (newY - oldY));
            notifyPanListeners();
        }
    }

    private double getAxisRatio(double[] range)
    {
        return Math.abs(range[1] - range[0]) / Math.abs(range[3] - range[2]);
    }

    private synchronized void notifyPanListeners()
    {
        for (PanListener listener : mPanListeners)
        {
            listener.panApplied();
        }
    }

    public synchronized void addPanListener(PanListener listener)
    {
        mPanListeners.add(listener);
    }

    public synchronized void removePanListener(PanListener listener)
    {
        mPanListeners.remove(listener);
    }

}
