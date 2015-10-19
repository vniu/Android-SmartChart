package com.lecast.smartchart.series;

import android.graphics.Canvas;

import com.lecast.smartchart.graphics.stroke.IStroke;
import com.lecast.smartchart.listener.IChartRepaintHandler;

public interface ISeries
{

    public String getValueField();

    public String getDisplayName();

    public IStroke getStroke();

    public void onDrawHandler(Canvas canvas);

    public void setPadding(int paddingLeft, int paddingTop, int paddingBottom, int paddingRight);

    public void setFontSize(int fontSize);

    public void setScale(float scale);

    public float getScale();

    public void setOffset(float offsetX, float offsetY);

    public float getOffsetX();

    public float getOffsetY();

    public void setChartRepaintHandler(IChartRepaintHandler repaintHandler);
}
