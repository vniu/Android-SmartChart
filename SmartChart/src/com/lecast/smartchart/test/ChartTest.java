package com.lecast.smartchart.test;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.LinearAxis;
import com.lecast.smartchart.chart.ChartBase;
import com.lecast.smartchart.chart.CombinedChart;
import com.lecast.smartchart.chart.LineChart;
import com.lecast.smartchart.chart.PieChart;
import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.graphics.stroke.Stroke;
import com.lecast.smartchart.listener.IChartTouchListener;
import com.lecast.smartchart.series.ColumnSeries;
import com.lecast.smartchart.series.LineSeries;
import com.lecast.smartchart.series.PieSeries;
import com.lecast.smartchart.tools.ITouchHandler;
import com.lecast.smartchart.tools.MultiTouchController;
import com.lecast.smartchart.tools.PanListener;
import com.lecast.smartchart.tools.TipWindowFactory;
import com.lecast.smartchart.tools.TouchHandler;
import com.lecast.smartchart.tools.ZoomEvent;
import com.lecast.smartchart.tools.ZoomListener;

public class ChartTest extends View
{

    private Context context;

    private float scale = 1;

    private float dx = 0, dy = 0;

    private MultiTouchController<Object> multiTouchController;

    private JSONArray jsonArray;

    private ChartBase chartBase;

    private ITouchHandler touchHandler;

    private View document;

    private float oldX, oldY;

    private IChartTouchListener chartTouchListener;

    public ChartTest(final Context context)
    {
        super(context);
        this.context = context;
        initDataProvider();
        initPieChart(jsonArray);
        // createChart();
        // multiTouchController = new MultiTouchController<Object>(this);
        touchHandler = new TouchHandler(this, chartBase);
        document = this;
        touchHandler.addPanListener(new PanListener()
        {

            public void panApplied()
            {
                invalidate();
            }
        });
        touchHandler.addZoomListener(new ZoomListener()
        {

            public void zoomReset()
            {

            }

            public void zoomApplied(ZoomEvent e)
            {
                scale = chartBase.getScale();
                invalidate();

            }
        });

        this.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                LocalPoint localPoint = getCurrentSeriesAndPoint();
                if (localPoint != null && localPoint.getValue() != null)
                {
                    TipWindowFactory.createWithImageToast(context, localPoint.getValue().toString(), 0).show();
                    Log.v("Click sucess", "click suces");
                    // chartTouchListener.execute(localPoint.getValue());
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
        try
        {
            jsonArray = new JSONArray(jsonArrBuffer.toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void createChart()
    {
        chartBase = new CombinedChart(context);
        chartBase.setPadding(60, 80, 130, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setTextAngle(45);
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("你好");
        linearAxis.setInterval(2);
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
        LineSeries ltseries = new LineSeries(context, Stroke.GREEN);
        ltseries.setValueField("tname");
        // chartBase.addSeries(series);
        chartBase.addSeries(tseries);
        chartBase.addSeries(wseries);
        chartBase.addSeries(ltseries);
        // chartBase.addSeries(lseries);
        chartBase.setDataProvider(jsonArray);
    }

    public void onDraw(Canvas canvas)
    {
        canvas.scale(scale, scale);
        if (chartBase.getOffsetX() != 0 || chartBase.getOffsetY() != 0)
            canvas.translate(chartBase.getOffsetX(), chartBase.getOffsetY());
        chartBase.run(canvas);
    }

    public void initPieChart(JSONArray jsonArray)
    {
        chartBase = new PieChart(context);
        chartBase.setDataProvider(jsonArray);
        PieSeries pieSeries = new PieSeries(context);
        pieSeries.setSuffixName("月");
        pieSeries.setLabelField("categoryName");
        pieSeries.setValueField("name");
        chartBase.addSeries(pieSeries);

    }

    public void initLineChart(JSONArray jsonArray, Canvas canvas)
    {
        LineChart chartBase = new LineChart(context);
        chartBase.setPadding(50, 100, 100, 20);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSuffixName("月");
        categoryAxis.setCategoryField("categoryName");
        LinearAxis linearAxis = new LinearAxis();
        linearAxis.setDisplayName("你好");
        linearAxis.setInterval(10);
        chartBase.setVerticalAxis(linearAxis);
        chartBase.setHorizonAxis(categoryAxis);
        LineSeries series = new LineSeries(context, Stroke.BLUE);
        series.setValueField("name");
        // LineSeries wseries = new LineSeries(context, Stroke.YELLOW);
        // wseries.setValueField("sname");
        LineSeries tseries = new LineSeries(context, Stroke.GREEN);
        tseries.setValueField("tname");
        chartBase.addSeries(series);
        chartBase.addSeries(tseries);
        // chartBase.addSeries(wseries);
        chartBase.setDataProvider(jsonArray);
        chartBase.run(canvas);
    }

    public void restore()
    {
        scale = 1;
        chartBase.restore();
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public void setChartTouchListener(IChartTouchListener chartTouchListener)
    {
        this.chartTouchListener = chartTouchListener;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            oldX = event.getX();
            oldY = event.getY();
            TipWindowFactory.createWithImageToast(context, oldX + "-oldX:" + oldY + "-oldY:", 0).show();
        }
        if (touchHandler.handleTouch(event))
        {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public LocalPoint getCurrentSeriesAndPoint()
    {
        Log.v("Scale=====", scale + "");
        return chartBase.getPointForScreenCoordinate(new LocalPoint(oldX, oldY));
    }

}
