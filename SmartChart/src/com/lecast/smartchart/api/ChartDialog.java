package com.lecast.smartchart.api;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class ChartDialog extends PluginDialog
{

    public ChartDialog(Context context)
    {
        super(context);
    }

    public void setChartView(View view)
    {
        view.setBackgroundColor(Color.WHITE);
        setContentView(view);
    }

}
