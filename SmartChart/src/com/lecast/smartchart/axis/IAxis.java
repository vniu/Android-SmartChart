package com.lecast.smartchart.axis;

import com.lecast.smartchart.chart.CartesianChart;

/**
 * @author vincent
 * 
 */
public interface IAxis
{

    public void dataChanged();

    public void update();

    public String getTitle();

    public String getDisplayName();

    public void setDataProvider(Object dataProvider);

    public Object getDataProvider();

    public void setFontSize(int fontSize);

    public int getTextAngle();

    public AxisLabelSetting getAxisLabelSetting();

    public void accept(CartesianChart chart);
}
