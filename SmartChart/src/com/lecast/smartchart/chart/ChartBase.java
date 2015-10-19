package com.lecast.smartchart.chart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.lecast.smartchart.axis.IAxis;
import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.common.PointMapping;
import com.lecast.smartchart.legend.DiamondLegend;
import com.lecast.smartchart.legend.Legend;
import com.lecast.smartchart.listener.IChartRepaintHandler;
import com.lecast.smartchart.series.ISeries;

/**
 * @author vincent
 * 
 */
public abstract class ChartBase
{

    protected IAxis horizonAxis;

    protected IAxis verticalAxis;

    protected List<ISeries> mSeries = new ArrayList<ISeries>();

    protected Object dataProvider;

    protected Context context;

    protected float scale = 1;

    protected int paddingLeft = 5;

    protected int paddingTop = 10;

    protected int paddingBottom = 10;

    protected int paddingRight = 5;

    protected int fontSize = 20;

    protected float offsetX = 0;

    protected float offsetY = 0;

    protected boolean dataChanged = false;

    protected Legend chartLegend;

    protected PointMapping pointMapping;

    protected boolean drawFinished = false;

    protected IChartRepaintHandler chartRepaintHandler;

    protected View chartView;

    protected boolean showLegend = false;

    public ChartBase(Context context)
    {
        this.context = context;
        chartLegend = new DiamondLegend(this);
        pointMapping = new PointMapping();

    }

    public void setChartLegend(Legend chartLegend)
    {
        this.chartLegend = chartLegend;
    }

    public void setChartView(View chartView)
    {
        this.chartView = chartView;
    }

    public View getChartView()
    {
        return chartView;
    }

    public void setPadding(int paddingLeft, int paddingTop, int paddingBottom, int paddingRight)
    {
        this.paddingLeft = paddingLeft;// paddingLeft < 60 ? 60 : paddingLeft;
        this.paddingBottom = paddingBottom;// paddingBottom < 50 ? 50 :
                                           // paddingBottom;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
    }

    public void setHorizonAxis(IAxis horizonAxis)
    {
        this.horizonAxis = horizonAxis;
    }

    public void setVerticalAxis(IAxis verticalAxis)
    {

        this.verticalAxis = verticalAxis;
    }

    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
    }

    public void run(Canvas canvas)
    {
        dataChanged = true;
        drawFinished = true;
        if (showLegend && chartLegend != null && dataProvider != null)
        {
            chartLegend.setFontSize(fontSize);
            chartLegend.setLegendPoint(paddingLeft, canvas.getHeight() - paddingBottom * 3 / 5);
            chartLegend.executeDrawHandler(canvas);
        }
    }

    public void setDataProvider(Object dataProvider)
    {
        if (dataProvider == null)
        {
            dataProvider = new JSONArray();
            return;
        }
        this.dataProvider = dataProvider;
        dataChanged = true;
        if (drawFinished && chartRepaintHandler != null)
        {
            chartRepaintHandler.repaintHandler();
        }
    }

    public void setSeriesList(List<ISeries> seriesList)
    {
        mSeries = seriesList;
    }

    public void addSeries(ISeries series)
    {
        if (mSeries.contains(series))
        {
            return;
        }
        mSeries.add(series);
    }

    public List<ISeries> getSeries()
    {
        return mSeries;
    }

    abstract protected void drawSeriesHandler(Canvas canvas);

    public LocalPoint getPointForScreenCoordinate(LocalPoint screenPoint)
    {
        return null;
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public float getOffsetX()
    {
        return offsetX;
    }

    public float getOffsetY()
    {
        return offsetY;
    }

    public void setOffsetX(float offsetX)
    {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY)
    {
        this.offsetY = offsetY;
    }

    public void setChartRepaintHandler(IChartRepaintHandler chartRepaintHandler)
    {
        this.chartRepaintHandler = chartRepaintHandler;
    }

    public void restore()
    {
        offsetX = 0;
        offsetY = 0;
        scale = 1;
    }

    public boolean isShowLegend()
    {
        return showLegend;
    }

    public void setShowLegend(boolean showLegend)
    {
        this.showLegend = showLegend;
    }

}
