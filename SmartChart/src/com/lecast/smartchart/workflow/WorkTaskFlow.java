package com.lecast.smartchart.workflow;

import android.content.Context;

import com.lecast.smartchart.ChartFactory;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.ChartView;
import com.lecast.smartchart.listener.IChartTouchListener;

public class WorkTaskFlow implements IWorkTaskFlow
{

    private String chartType;

    private String requestMethod;

    private String requestURL;

    private String[] requestParams;

    private String categoryField;

    private String[] valueFields;

    private WorkTaskFlow nextTaskFlow;

    private WorkTaskFlow preTaskFlow;

    private Object dataProvider;

    private ChartBase chart;

    private ChartView chartView;

    private Context context;

    private int taskIndex;

    public WorkTaskFlow(Context context, int taskIndex)
    {
        this.context = context;
        this.taskIndex = taskIndex;
    }

    public WorkTaskFlow getNextTaskFlow()
    {
        return nextTaskFlow;
    }

    public WorkTaskFlow getPreTaskFlow()
    {
        return preTaskFlow;
    }

    public void setNextTaskFlow(WorkTaskFlow nextTaskFlow)
    {
        this.nextTaskFlow = nextTaskFlow;
    }

    public void setPreTaskFlow(WorkTaskFlow preTaskFlow)
    {
        this.preTaskFlow = preTaskFlow;
    }

    public String getChartType()
    {
        return chartType;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public String getRequestURL()
    {
        return requestURL;
    }

    public String[] getRequestParams()
    {
        return requestParams;
    }

    public String getCategoryField()
    {
        return categoryField;
    }

    public void setChartType(String chartType)
    {
        this.chartType = chartType;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public void setRequestURL(String requestURL)
    {
        this.requestURL = requestURL;
    }

    public void setRequestParams(String[] requestParams)
    {
        this.requestParams = requestParams;
    }

    public void setCategoryField(String categoryField)
    {
        this.categoryField = categoryField;
    }

    public String[] getValueFields()
    {
        return valueFields;
    }

    public Object getDataProvider()
    {
        return dataProvider;
    }

    public void setValueFields(String[] valueFields)
    {
        this.valueFields = valueFields;
    }

    public void notifyChanged(Object dataProvider)
    {
        this.dataProvider = dataProvider;
        if (dataProvider == null)
            return;
        if (chart == null)
            return;
        chart.setDataProvider(dataProvider);
    }

    public void repaint()
    {
        chartView.invalidate();
    }

    public ChartView createChartHandler(IChartTouchListener chartTouchListener)
    {
        if (chartView != null)
            return chartView;
        chart = ChartFactory.getChart(chartType, context);
        this.chartView = new ChartView(context, chart);
        chartView.setChartTouchListener(chartTouchListener);
        return chartView;
    }

    public int getTaskIndex()
    {
        return taskIndex;
    }

}
