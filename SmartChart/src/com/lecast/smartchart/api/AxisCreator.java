package com.lecast.smartchart.api;

import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.DialAxis;
import com.lecast.smartchart.axis.IAxis;
import com.lecast.smartchart.axis.LinearAxis;

public class AxisCreator
{

    public static IAxis getAxis(AxisModel model)
    {

        if (model.getName().equals("CategoryAxis"))
        {
            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.setCategoryField(model.getCategoryField());
            categoryAxis.setDisplayName(model.getDisplayName());
            categoryAxis.setTextAngle(model.getTextAngle());
            categoryAxis.setSuffixName(model.getSuffixName());
            return categoryAxis;
        }
        if (model.getName().equals("LinearAxis"))
        {
            LinearAxis linearAxis = new LinearAxis();
            linearAxis.setDisplayName(model.getDisplayName());
            linearAxis.setTextAngle(model.getTextAngle());
            linearAxis.setSuffixName(model.getSuffixName());
            linearAxis.setInterval(model.getInterval());
            return linearAxis;
        }
        if (model.getName().equals("DialAxis"))
        {
            DialAxis dialAxis = new DialAxis();
            dialAxis.setMaxValue(model.getMaxValue());
            dialAxis.setMinValue(model.getMinValue());
            return dialAxis;
        }
        return null;
    }
}
