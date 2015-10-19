package com.lecast.smartchart.api;

import android.content.Context;

import com.lecast.smartchart.graphics.stroke.Stroke;
import com.lecast.smartchart.series.AreaSeries;
import com.lecast.smartchart.series.BarSeries;
import com.lecast.smartchart.series.ColumnSeries;
import com.lecast.smartchart.series.DialSeries;
import com.lecast.smartchart.series.LineSeries;
import com.lecast.smartchart.series.PieSeries;
import com.lecast.smartchart.series.Series;
import com.lecast.smartchart.utils.Constants;

public class SeriesCreator
{

    public static Series getSeries(SeriesModel model, Context context)
    {
        if (model == null || model.getName() == null)
            return null;
        if (model.getName().equals(Constants.LineSeries))
        {
            return new LineSeries(context, new Stroke(model.getColor(), model.getStrokeWidth()));
        }
        if (model.getName().equals(Constants.ColumnSeries))
        {
            return new ColumnSeries(context, new Stroke(model.getColor(), model.getStrokeWidth()));
        }
        if (model.getName().equals(Constants.BarSeries))
        {
            return new BarSeries(context, new Stroke(model.getColor(), model.getStrokeWidth()));
        }
        if (model.getName().equals(Constants.PieSeries))
        {
            return new PieSeries(context);
        }
        if (model.getName().equals(Constants.AreaSeries))
        {
            return new AreaSeries(context, new Stroke(model.getColor()));
        }
        if (model.getName().equals(Constants.DialSeries))
        {
            return new DialSeries(context);
        }
        return null;
    }
}
