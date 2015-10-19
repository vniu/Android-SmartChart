package com.lecast.smartchart.tools;

/**
 * A zoom listener.
 */
public interface ZoomListener
{

    /**
     * Called when a zoom change is triggered.
     * 
     * @param e
     *            the zoom event
     */
    void zoomApplied(ZoomEvent e);

    /**
     * Called when a zoom reset is done.
     */
    void zoomReset();
}
