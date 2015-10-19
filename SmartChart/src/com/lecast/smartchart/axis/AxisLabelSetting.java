package com.lecast.smartchart.axis;

import java.util.List;

/**
 * @author vincent
 * 
 */
public class AxisLabelSetting
{

    private List<AxisLabel> labels;

    private List ticks;

    public List<AxisLabel> getLabels()
    {
        return labels;
    }

    public List getTicks()
    {
        return ticks;
    }

    public void setLabels(List<AxisLabel> labels)
    {
        this.labels = labels;
    }

    public void setTicks(List ticks)
    {
        this.ticks = ticks;
    }

}
