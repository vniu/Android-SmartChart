package com.lecast.smartchart;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lecast.smartchart.test.ChartTest;

public class MainActivity extends Activity
{

    private Button zoomOutBtn;

    private Button zoomInBtn;

    private float scale = 1;

    private ListView listView;

    private ChartListAdapter chartListAdapter;

    private List<ChartVO> chartVOs;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_list_view);
        listView = (ListView) findViewById(R.id.chartListView);
        chartListAdapter = new ChartListAdapter(this);
        createChartVO();
    }

    private void createChartVO()
    {
        String[] chartNames = { "线图", "多线图", "柱图", "多柱混合图", "线柱混合图", "水平柱状图", "水平多柱状图", "区域图", "饼图", "仪表盘" };
        String[] chartTypes =
                    { "LineChart", "MutilLineChart", "ColumnChart", "MutilColumnChart", "LineAndColumnChart",
                                "BarChart", "MutiBarChart", "AreaChart", "PieChart", "DialChart" };
        chartVOs = new ArrayList<ChartVO>();
        for (int index = 0; index < chartNames.length; index++)
        {
            ChartVO chartVO = new ChartVO();
            chartVO.setChartName(chartNames[index]);
            chartVO.setChartType(chartTypes[index]);
            chartVOs.add(chartVO);
        }
        chartListAdapter.updateDataProvider(chartVOs);
        listView.setAdapter(chartListAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
