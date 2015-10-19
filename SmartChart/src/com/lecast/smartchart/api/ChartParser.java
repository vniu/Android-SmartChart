package com.lecast.smartchart.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ChartParser
{

    private String chartJson;

    public ChartParser(String chartJson)
    {
        this.chartJson = chartJson;
    }

    public ChartModel parserHandler()
    {
        ChartModel chartModel = null;
        if (chartJson == null)
            return chartModel;
        chartModel = new ChartModel();
        try
        {
            JSONObject chartObject = JSON.parseObject(chartJson);
            if (chartObject.containsKey("titleName"))
                chartModel.setTitleName(chartObject.getString("titleName"));
            chartModel.setName(chartObject.getString("chart"));
            if (chartObject.containsKey("fontSize"))
                chartModel.setFontSize(chartObject.getIntValue("fontSize"));
            if (chartObject.containsKey("padding"))
            {
                JSONObject paddingObject = chartObject.getJSONObject("padding");
                if (paddingObject.containsKey("paddingBottom"))
                    chartModel.setPaddingBottom(paddingObject.getIntValue("paddingBottom"));
                if (paddingObject.containsKey("paddingLeft"))
                    chartModel.setPaddingLeft(paddingObject.getIntValue("paddingLeft"));
                if (paddingObject.containsKey("paddingTop"))
                    chartModel.setPaddingTop(paddingObject.getIntValue("paddingTop"));
                if (paddingObject.containsKey("paddingRight"))
                    chartModel.setPaddingRight(paddingObject.getIntValue("paddingRight"));
            }

            if (chartObject.containsKey("series"))
            {
                JSONArray seriesJsons = chartObject.getJSONArray("series");
                int size = seriesJsons.size();
                for (int index = 0; index < size; index++)
                {
                    JSONObject seriesObject = seriesJsons.getJSONObject(index);
                    SeriesModel seriesModel = new SeriesModel();
                    if (seriesObject.containsKey("color"))
                        seriesModel.setColor(seriesObject.getString("color"));
                    if (seriesObject.containsKey("displayName"))
                        seriesModel.setDisplayName(seriesObject.getString("displayName"));
                    if (seriesObject.containsKey("valueField"))
                        seriesModel.setValueField(seriesObject.getString("valueField"));
                    if (seriesObject.containsKey("name"))
                        seriesModel.setName(seriesObject.getString("name"));
                    if (seriesObject.containsKey("strokeWidth"))
                        seriesModel.setStrokeWidth(seriesObject.getIntValue("strokeWidth"));
                    if (seriesObject.containsKey("labelField"))
                        seriesModel.setLabelField(seriesObject.getString("labelField"));
                    if (seriesObject.containsKey("suffixName"))
                        seriesModel.setSuffixName(seriesObject.getString("suffixName"));
                    chartModel.addSeriesModel(seriesModel);
                }
            }
            if (chartObject.containsKey("verticalAxis"))
            {
                AxisModel verticalAxis = new AxisModel();
                JSONObject verticalAxisObject = chartObject.getJSONObject("verticalAxis");
                verticalAxis.setName(verticalAxisObject.getString("name"));
                if (verticalAxisObject.containsKey("textAngle"))
                    verticalAxis.setTextAngle(verticalAxisObject.getIntValue("textAngle"));
                if (verticalAxisObject.containsKey("categoryField"))
                    verticalAxis.setCategoryField(verticalAxisObject.getString("categoryField"));
                if (verticalAxisObject.containsKey("displayName"))
                    verticalAxis.setDisplayName(verticalAxisObject.getString("displayName"));
                if (verticalAxisObject.containsKey("valueField"))
                    verticalAxis.setValueField(verticalAxisObject.getString("valueField"));
                if (verticalAxisObject.containsKey("interval"))
                    verticalAxis.setInterval(verticalAxisObject.getIntValue("interval"));

                chartModel.setVerticalAxisModel(verticalAxis);
            }
            if (chartObject.containsKey("horizonAxis"))
            {
                AxisModel horizonalAxis = new AxisModel();
                JSONObject horizonalObject = chartObject.getJSONObject("horizonAxis");
                if (horizonalObject.containsKey("name"))
                    horizonalAxis.setName(horizonalObject.getString("name"));
                if (horizonalObject.containsKey("textAngle"))
                    horizonalAxis.setTextAngle(horizonalObject.getIntValue("textAngle"));
                if (horizonalObject.containsKey("valueField"))
                    horizonalAxis.setValueField(horizonalObject.getString("valueField"));
                if (horizonalObject.containsKey("displayName"))
                    horizonalAxis.setDisplayName(horizonalObject.getString("displayName"));
                if (horizonalObject.containsKey("categoryField"))
                    horizonalAxis.setCategoryField(horizonalObject.getString("categoryField"));
                if (horizonalObject.containsKey("interval"))
                    horizonalAxis.setInterval(horizonalObject.getIntValue("interval"));
                chartModel.setHorizonalAxisModel(horizonalAxis);
            }
            if (chartObject.containsKey("dialAxis"))
            {
                AxisModel dialAxisModel = new AxisModel();
                JSONObject dialObject = chartObject.getJSONObject("dialAxis");
                if (dialObject.containsKey("name"))
                    dialAxisModel.setName(dialObject.getString("name"));
                if (dialObject.containsKey("maxValue"))
                    dialAxisModel.setMaxValue(dialObject.getFloatValue("maxValue"));
                if (dialObject.containsKey("minValue"))
                    dialAxisModel.setMinValue(dialObject.getFloatValue("minValue"));
                chartModel.setDialAxisModel(dialAxisModel);
            }
            if (chartObject.containsKey("chartLegend"))
            {
                chartModel.setChartLegendName(chartObject.getString("chartLegend"));
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return chartModel;
    }
}
