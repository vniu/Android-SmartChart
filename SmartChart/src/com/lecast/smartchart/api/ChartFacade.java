package com.lecast.smartchart.api;

import android.content.Context;

import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.ChartView;

public class ChartFacade
{

    private Context context;

    private ChartBase chart;

    private ChartView chartView;

    public ChartFacade(Context contecxt)
    {
        this.context = contecxt;
    }

    public ChartView createChart(String chartJson)
    {
        ChartParser parser = new ChartParser(chartJson);
        ChartModel chartModel = parser.parserHandler();
        chart = ChartCreator.getChart(chartModel, context);
        chartView = new ChartView(context, chart);
        chartView.setTitleName(chartModel.getTitleName());
        return chartView;
    }

    public void refreshDataProvider(Object dataProvider)
    {
        if (dataProvider == null || chart == null)
            return;
        chart.setDataProvider(dataProvider);
    }

}
