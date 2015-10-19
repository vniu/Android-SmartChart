package com.lecast.smartchart.api;

import android.content.Context;

import com.lecast.smartchart.axis.DialAxis;
import com.lecast.smartchart.chart.AreaChart;
import com.lecast.smartchart.chart.BarChart;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.ColumnChart;
import com.lecast.smartchart.chart.CombinedChart;
import com.lecast.smartchart.chart.DialChart;
import com.lecast.smartchart.chart.LineChart;
import com.lecast.smartchart.chart.PieChart;
import com.lecast.smartchart.utils.Constants;

public class ChartCreator
{

    public static ChartBase getChart(ChartModel chartModel, Context context)
    {
        ChartBase chart = createChart(chartModel, context);
        if (chart == null)
            return chart;
        composeChartHandler(chart, chartModel, context);
        return chart;
    }

    private static void composeChartHandler(ChartBase chart, ChartModel chartModel, Context context)
    {
        for (SeriesModel seriesModel : chartModel.getSeriesModels())
        {
            chart.addSeries(seriesModel.getSeries(context));
        }
        chart.setFontSize(chartModel.getFontSize());
        chart.setPadding(chartModel.getPaddingLeft(), chartModel.getPaddingTop(), chartModel.getPaddingBottom(),
                    chartModel.getPaddingRight());
        if (chartModel.getHorizonalAxisModel() != null)
            chart.setHorizonAxis(AxisCreator.getAxis(chartModel.getHorizonalAxisModel()));
        if (chartModel.getVerticalAxisModel() != null)
            chart.setVerticalAxis(AxisCreator.getAxis(chartModel.getVerticalAxisModel()));
        if (chartModel.getChartLegendName() != null)
            chart.setChartLegend(ChartLegendCreator.getChartLegend(chart, chartModel.getChartLegendName()));
        if (chartModel.getDialAxisModel() != null)
        {
            DialChart dialChart = (DialChart) chart;
            dialChart.setDialAxis((DialAxis) AxisCreator.getAxis(chartModel.getDialAxisModel()));
        }
    }

    private static ChartBase createChart(ChartModel chartModel, Context context)
    {
        ChartBase chart = null;
        if (chartModel.getName() == null)
            return chart;
        if (chartModel.getName().equals(Constants.LineChart))
        {
            chart = new LineChart(context);
        }
        else if (chartModel.getName().equals(Constants.ColumnChart))
        {
            chart = new ColumnChart(context);
        }
        else if (chartModel.getName().equals(Constants.CombineChart))
        {
            chart = new CombinedChart(context);
        }
        else if (chartModel.getName().equals(Constants.BarChart))
        {
            chart = new BarChart(context);
        }
        else if (chartModel.getName().equals(Constants.PieChart))
        {
            chart = new PieChart(context);
        }
        else if (chartModel.getName().equals(Constants.AreaChart))
        {
            chart = new AreaChart(context);
        }
        else if (chartModel.getName().equals(Constants.DialChart))
        {
            chart = new DialChart(context);
        }
        return chart;

    }

}
