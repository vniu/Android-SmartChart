package com.lecast.smartchart;

import android.content.Context;

import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.DialAxis;
import com.lecast.smartchart.axis.LinearAxis;
import com.lecast.smartchart.chart.AreaChart;
import com.lecast.smartchart.chart.BarChart;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.CombinedChart;
import com.lecast.smartchart.chart.DialChart;
import com.lecast.smartchart.chart.LineChart;
import com.lecast.smartchart.chart.PieChart;
import com.lecast.smartchart.graphics.stroke.Stroke;
import com.lecast.smartchart.legend.CircleLegend;
import com.lecast.smartchart.legend.SquareLegend;
import com.lecast.smartchart.legend.TriangleLegend;
import com.lecast.smartchart.series.AreaSeries;
import com.lecast.smartchart.series.BarSeries;
import com.lecast.smartchart.series.ColumnSeries;
import com.lecast.smartchart.series.DialSeries;
import com.lecast.smartchart.series.LineSeries;
import com.lecast.smartchart.series.PieSeries;

public class ChartFactory
{

    public static ChartBase getChart(String chartType, Context context)
    {
        if (chartType.equals("LineChart"))
        {
            return getLineChart(context);
        }
        if (chartType.equals("MutilLineChart"))
        {
            return getMutilLineChart(context);
        }
        if (chartType.equals("LineAndColumnChart"))
        {
            return getLineAndColumnChart(context);
        }
        if (chartType.equals("ColumnChart"))
        {
            return getColumnChart(context);
        }
        if (chartType.equals("MutilColumnChart"))
        {
            return getMutilColumnChart(context);
        }
        if (chartType.equals("BarChart"))
        {
            return getBarChart(context);
        }
        if (chartType.equals("MutiBarChart"))
        {
            return getMutiBarChart(context);
        }
        if (chartType.equals("AreaChart"))
        {
            return getAreaChart(context);
        }
        if (chartType.equals("PieChart"))
        {
            return getPieChart(context);
        }
        if (chartType.equals("DialChart"))
        {
            return getDialChart(context);
        }
        return null;
    }

    private static ChartBase getLineChart(Context context)
    {
        LineChart lineChart = new LineChart(context);
        lineChart.setChartLegend(new SquareLegend(lineChart));
        lineChart.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        lineChart.setVerticalAxis(linearAxis);
        lineChart.setHorizonAxis(categoryAxis);
        // LineSeries series = new LineSeries(context, Stroke.BLUE);
        // series.setValueField("name");
        LineSeries tseries = new LineSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        tseries.setDisplayName("平均销售量");
        lineChart.addSeries(tseries);
        return lineChart;
    }

    private static ChartBase getMutilLineChart(Context context)
    {
        LineChart lineChart = new LineChart(context);
        lineChart.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(10);
        lineChart.setVerticalAxis(linearAxis);
        lineChart.setHorizonAxis(categoryAxis);
        LineSeries series = new LineSeries(context, Stroke.BLUE);
        series.setValueField("name");
        LineSeries wseries = new LineSeries(context, Stroke.YELLOW);
        wseries.setValueField("sname");
        LineSeries tseries = new LineSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        series.setDisplayName("销售量");
        tseries.setDisplayName("平均销售量");
        wseries.setDisplayName("价格");
        lineChart.addSeries(series);
        lineChart.addSeries(wseries);
        lineChart.addSeries(tseries);
        return lineChart;
    }

    private static ChartBase getLineAndColumnChart(Context context)
    {
        CombinedChart chartBase = new CombinedChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        chartBase.setVerticalAxis(linearAxis);
        chartBase.setHorizonAxis(categoryAxis);
        ColumnSeries series = new ColumnSeries(context, Stroke.BLUE);
        series.setValueField("name");

        ColumnSeries wseries = new ColumnSeries(context, Stroke.YELLOW);
        wseries.setValueField("sname");

        ColumnSeries tseries = new ColumnSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        // chartBase.addSeries(series);
        // chartBase.addSeries(tseries);
        LineSeries lseries = new LineSeries(context, Stroke.BLUE);
        lseries.setValueField("name");
        // LineSeries wseries = new LineSeries(context, Stroke.YELLOW);
        // wseries.setValueField("sname");
        LineSeries ltseries = new LineSeries(context, Stroke.BLUE);
        ltseries.setValueField("tname");
        ltseries.setDisplayName("销售量");
        tseries.setDisplayName("平均销售量");
        wseries.setDisplayName("价格");
        // chartBase.addSeries(series);
        chartBase.addSeries(tseries);
        chartBase.addSeries(wseries);
        chartBase.addSeries(ltseries);
        return chartBase;
        // chartBase.addSeries(lseries);
    }

    private static ChartBase getColumnChart(Context context)
    {
        CombinedChart chartBase = new CombinedChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        chartBase.setVerticalAxis(linearAxis);
        chartBase.setHorizonAxis(categoryAxis);
        ColumnSeries tseries = new ColumnSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        tseries.setDisplayName("平均销售量");
        chartBase.addSeries(tseries);
        return chartBase;
    }

    private static ChartBase getBarChart(Context context)
    {
        BarChart chartBase = new BarChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        chartBase.setChartLegend(new TriangleLegend(chartBase));
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        chartBase.setVerticalAxis(categoryAxis);
        chartBase.setHorizonAxis(linearAxis);
        BarSeries tseries = new BarSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        BarSeries series = new BarSeries(context, Stroke.YELLOW);
        series.setValueField("name");
        series.setDisplayName("销售量");
        tseries.setDisplayName("平均销售量");
        chartBase.addSeries(tseries);
        return chartBase;
    }

    private static ChartBase getMutiBarChart(Context context)
    {
        BarChart chartBase = new BarChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        chartBase.setChartLegend(new TriangleLegend(chartBase));
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        chartBase.setVerticalAxis(categoryAxis);
        chartBase.setHorizonAxis(linearAxis);
        BarSeries tseries = new BarSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        BarSeries series = new BarSeries(context, Stroke.YELLOW);
        series.setValueField("name");
        series.setDisplayName("销售量");
        tseries.setDisplayName("平均销售量");
        chartBase.addSeries(tseries);
        chartBase.addSeries(series);
        return chartBase;
    }

    private static ChartBase getMutilColumnChart(Context context)
    {
        CombinedChart chartBase = new CombinedChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(5);
        chartBase.setVerticalAxis(linearAxis);
        chartBase.setHorizonAxis(categoryAxis);
        ColumnSeries series = new ColumnSeries(context, Stroke.BLUE);
        series.setValueField("name");
        ColumnSeries wseries = new ColumnSeries(context, Stroke.YELLOW);
        wseries.setValueField("sname");
        ColumnSeries tseries = new ColumnSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        series.setDisplayName("销售量");
        tseries.setDisplayName("平均销售量");
        wseries.setDisplayName("价格");
        chartBase.addSeries(tseries);
        chartBase.addSeries(wseries);
        return chartBase;
    }

    private static ChartBase getAreaChart(Context context)
    {
        AreaChart areaChart = new AreaChart(context);
        areaChart.setPadding(50, 50, 180, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setDisplayName("月份");
        categoryAxis.setSuffixName("月");
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("价格");
        linearAxis.setInterval(1);
        areaChart.setVerticalAxis(linearAxis);
        areaChart.setHorizonAxis(categoryAxis);
        // LineSeries series = new LineSeries(context, Stroke.BLUE);
        // series.setValueField("name");
        AreaSeries tseries = new AreaSeries(context, Stroke.GREEN);
        tseries.setValueField("name");
        AreaSeries sseries = new AreaSeries(context, Stroke.YELLOW);
        sseries.setValueField("sname");
        tseries.setDisplayName("平均销售量");
        sseries.setDisplayName("销售量");
        areaChart.addSeries(sseries);
        areaChart.addSeries(tseries);
        return areaChart;
    }

    private static ChartBase getPieChart(Context context)
    {
        PieChart chartBase = new PieChart(context);
        chartBase.setPadding(50, 50, 180, 20);
        chartBase.setChartLegend(new CircleLegend(chartBase));
        PieSeries pieSeries = new PieSeries(context);
        pieSeries.setSuffixName("月");
        pieSeries.setLabelField("categoryName");
        pieSeries.setValueField("name");
        chartBase.addSeries(pieSeries);
        return chartBase;
    }

    private static ChartBase getDialChart(Context context)
    {
        DialAxis dialAxis = new DialAxis();
        dialAxis.setMaxValue(100);
        dialAxis.setMinValue(0);
        DialChart chartBase = new DialChart(context, dialAxis);
        DialSeries dialSeries = new DialSeries(context);
        dialSeries.setDisplayName("销售金额");
        dialSeries.setSuffixName("万元");
        chartBase.addSeries(dialSeries);
        chartBase.setPadding(50, 50, 180, 20);
        return chartBase;

    }

}
