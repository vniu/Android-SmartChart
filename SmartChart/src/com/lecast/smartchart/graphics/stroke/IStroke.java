package com.lecast.smartchart.graphics.stroke;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;

/**
 * @author vincent
 * 
 */
public interface IStroke
{

    public int getColor();

    public int getStrokeWidth();

    public Cap getCap();

    public Join getJoin();

}
