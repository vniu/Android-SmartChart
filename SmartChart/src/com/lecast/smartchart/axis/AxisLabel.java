package com.lecast.smartchart.axis;

/**
 * @author vincent
 * 
 */
public class AxisLabel
{

    private double position;

    private Object value;

    private String text;

    public AxisLabel(double position, Object value, String text)
    {
        this.position = position;
        this.value = value;
        this.text = text;
    }

    public double getPosition()
    {
        return position;
    }

    public Object getValue()
    {
        return value;
    }

    public String getText()
    {
        return text;
    }

    public void setPosition(double position)
    {
        this.position = position;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
