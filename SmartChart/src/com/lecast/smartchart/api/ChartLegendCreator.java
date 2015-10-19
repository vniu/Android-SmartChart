package com.lecast.smartchart.api;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.legend.CircleLegend;
import com.lecast.smartchart.legend.DiamondLegend;
import com.lecast.smartchart.legend.Legend;
import com.lecast.smartchart.legend.SquareLegend;
import com.lecast.smartchart.legend.TriangleLegend;

public class ChartLegendCreator
{

    public static Legend getChartLegend(ChartBase chart, String chartName)
    {
        if (chartName.equals("DiamondLegend¡¯"))
        {
            return new DiamondLegend(chart);
        }
        else if (chartName.equals("SquareLegend"))
        {
            return new SquareLegend(chart);
        }
        else if (chartName.equals("TriangleLegend"))
        {
            return new TriangleLegend(chart);
        }
        else if (chartName.equals("CircleLegend"))
        {
            return new CircleLegend(chart);
        }
        return null;
    }

}
