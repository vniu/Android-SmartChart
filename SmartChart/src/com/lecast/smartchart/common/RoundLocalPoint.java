package com.lecast.smartchart.common;

/**
 * @author vincent
 * 
 */
public class RoundLocalPoint extends LocalPoint
{

    /**
     * 
     */
    private static final long serialVersionUID = 3744610869444287742L;

    public RoundLocalPoint(float angle, Object object)
    {
        this.value = object;
        this.angle = angle;
    }

    private float angle;

    public float getAngle()
    {
        return angle;
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }
}
