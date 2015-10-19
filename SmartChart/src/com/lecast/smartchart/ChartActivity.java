package com.lecast.smartchart;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.ChartView;
import com.lecast.smartchart.chart.DialChart;

public class ChartActivity extends Activity
{

    private Button zoomOutBtn;

    private Button zoomInBtn;

    private float scale = 1;

    private ChartView chartView;

    private JSONArray jsonArray;

    private float dialValue = 0;

    private Boolean fisrtCircle = false;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zoomOutBtn = (Button) findViewById(R.id.zoomOutBtn);
        zoomInBtn = (Button) findViewById(R.id.zoomInBtn);
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        Intent intent = getIntent();
        String chartType = intent.getStringExtra("chartType");
        initDataProvider();
        final ChartBase chart = ChartFactory.getChart(chartType, this);
        if (!(chart instanceof DialChart))
            chart.setDataProvider(jsonArray);
        else
            chart.setDataProvider(10.0f);
        chartView = new ChartView(this, chart);
        content.addView(chartView);
        zoomOutBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View paramView)
            {
                scale += 0.2;
                chart.setScale(scale);
                chartView.invalidate();
            }
        });
        zoomInBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View paramView)
            {
                scale -= 0.2;
                chart.setScale(scale);
                chartView.invalidate();
            }
        });

        Button addBtn = (Button) findViewById(R.id.addBtn);
        Button refreshBtn = (Button) findViewById(R.id.refreshBtn);

        refreshBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                chartView.restore();
                chartView.setVisibility(View.INVISIBLE);
                chartView.invalidate();
                Animation animation = new TranslateAnimation(-480, 0, chartView.getPaddingLeft(), 0);
                animation.setDuration(500);
                chartView.startAnimation(animation);
                chartView.setVisibility(View.VISIBLE);
            }
        });

        Button returnBtn = (Button) findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                if (chart instanceof DialChart)
                {

                    dialValue = fisrtCircle ? dialValue - 10 : dialValue + 10;
                    if (dialValue == 100)
                    {
                        fisrtCircle = true;
                    }
                    if (dialValue == 0)
                    {
                        fisrtCircle = false;
                    }
                    DialChart dialChart = (DialChart) chart;
                    dialChart.setDialValue(dialValue);
                }
                else
                {
                    if (!fisrtCircle)
                    {
                        initDataProvider2();
                        fisrtCircle = true;
                    }
                    else
                    {
                        initDataProvider();
                        fisrtCircle = false;
                    }
                    chart.setDataProvider(jsonArray);
                }
            }
        });

    }

    private void initDataProvider()
    {
        StringBuffer jsonArrBuffer = new StringBuffer();
        jsonArrBuffer.append("[");
        for (int index = 0; index < 8; index++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", Math.sqrt(index) + 1);
            map.put("tname", Math.sqrt(index) * 5 + 1);
            map.put("sname", Math.sqrt(index) * 10 + 1);
            map.put("categoryName", index + 1);
            JSONObject json = new JSONObject(map);
            jsonArrBuffer.append(json.toString());
            jsonArrBuffer.append(",");
        }
        jsonArrBuffer.deleteCharAt(jsonArrBuffer.length() - 1);
        jsonArrBuffer.append("]");
        Log.v("DataProvider", jsonArrBuffer.toString());
        try
        {
            jsonArray = JSON.parseArray(jsonArrBuffer.toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void initDataProvider2()
    {
        StringBuffer jsonArrBuffer = new StringBuffer();
        jsonArrBuffer.append("[");
        for (int index = 0; index < 8; index++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", index + 1);
            map.put("tname", index * 2 + 1);
            map.put("sname", index * 3 + 1);
            map.put("categoryName", index + 1);
            JSONObject json = new JSONObject(map);
            jsonArrBuffer.append(json.toString());
            jsonArrBuffer.append(",");
        }
        jsonArrBuffer.deleteCharAt(jsonArrBuffer.length() - 1);
        jsonArrBuffer.append("]");
        Log.v("DataProvider", jsonArrBuffer.toString());
        try
        {
            jsonArray = JSON.parseArray(jsonArrBuffer.toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
