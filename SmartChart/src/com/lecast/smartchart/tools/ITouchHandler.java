package com.lecast.smartchart.tools;

import android.view.MotionEvent;

public interface ITouchHandler
{

    boolean handleTouch(MotionEvent event);

    void addZoomListener(ZoomListener listener);

    void removeZoomListener(ZoomListener listener);

    void addPanListener(PanListener listener);

    void removePanListener(PanListener listener);

}