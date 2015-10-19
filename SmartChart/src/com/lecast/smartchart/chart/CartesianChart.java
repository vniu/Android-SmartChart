package com.lecast.smartchart.chart;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.lecast.smartchart.axis.AxisLabel;
import com.lecast.smartchart.axis.AxisLabelSetting;
import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.LinearAxis;
import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.graphics.stroke.AxisStroke;
import com.lecast.smartchart.graphics.stroke.Stroke;
import com.lecast.smartchart.gridline.GridLine;
import com.lecast.smartchart.series.CartesianSeries;
import com.lecast.smartchart.series.ISeries;
import com.lecast.smartchart.series.LineSeries;
import com.lecast.smartchart.utils.DrawUtils;

/**
 * @author vincent
 * 
 */
public class CartesianChart extends ChartBase
{

    protected boolean showYGridLine = true;

    protected boolean showXGridLine = true;

    protected GridLine grideLine;

    protected AxisStroke axisStroke;

    protected String direction = "vertical";// horizonal

    public static final String HORIZONTAL = "horizontal";

    public static final String V = "vertical";

    public CartesianChart(Context context)
    {
        super(context);
        horizonAxis = new CategoryAxis();
        verticalAxis = new LinearAxis();
        grideLine = new GridLine(Stroke.GRAYSOLID);
        axisStroke = AxisStroke.DEFAULT;
    }

    public void setGrideLine(GridLine grideLine)
    {
        this.grideLine = grideLine;
    }

    protected void drawAxis(Canvas canvas)
    {

        int width = chartView.getWidth();// canvas.getWidth();
        int height = chartView.getHeight();// canvas.getHeight();
        RectF areaRect = new RectF(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        drawXAxis(canvas, paint, areaRect);
        drawYAxis(canvas, paint, areaRect);
    }

    protected void drawXAxis(Canvas canvas, Paint paint, RectF areaRect)
    {
        paint.setColor(axisStroke.getColor());
        paint.setStrokeWidth(axisStroke.getHorAxisStrokeWidth());
        canvas.drawLine(areaRect.left, areaRect.bottom, areaRect.right, areaRect.bottom, paint);
        paint.setStrokeWidth(axisStroke.getStrokeWidth());
        drawXAxisLabel(canvas, paint, areaRect);
    }

    protected void drawXAxisLabel(Canvas canvas, Paint paint, RectF areaRect)
    {
        AxisLabelSetting horAxisLabelSetting = horizonAxis.getAxisLabelSetting();
        List<AxisLabel> axisLables = horAxisLabelSetting.getLabels();
        for (AxisLabel label : axisLables)
        {
            float xLabel = Float.valueOf(String.valueOf(label.getPosition() * areaRect.width())) + paddingLeft;
            float lastXLabel = xLabel - DrawUtils.getTextRect(label.getText(), fontSize).width() / 2;
            paint.setColor(axisStroke.getTextColor());
            DrawUtils.drawText(canvas, label.getText(), lastXLabel, areaRect.bottom + fontSize * 4 / 3, paint,
                        horizonAxis.getTextAngle());
            paint.setColor(axisStroke.getColor());
            if (showXGridLine)
                grideLine.drawXGridLine(canvas, xLabel, areaRect.bottom, areaRect);
            canvas.drawLine(xLabel, areaRect.bottom, xLabel, areaRect.bottom + fontSize / 3, paint);
        }
        paint.setColor(axisStroke.getTextColor());
        if (horizonAxis.getDisplayName() != null)
        {
            Rect fontRect = DrawUtils.getTextRect(horizonAxis.getDisplayName(), fontSize);
            DrawUtils.drawText(canvas, horizonAxis.getDisplayName(), paddingLeft + areaRect.width() / 2,
                        areaRect.bottom + 2 * fontRect.height() + 10, paint, 0);
        }
    }

    protected void drawYAxis(Canvas canvas, Paint paint, RectF areaRect)
    {
        paint.setColor(axisStroke.getColor());
        paint.setStrokeWidth(axisStroke.getVerAxisStrokeWidth());
        canvas.drawLine(areaRect.left, areaRect.bottom, areaRect.left, areaRect.top, paint);
        paint.setStrokeWidth(axisStroke.getStrokeWidth());
        drawYAxisLabel(canvas, paint, areaRect);
    }

    protected void drawYAxisLabel(Canvas canvas, Paint paint, RectF areaRect)
    {
        List<AxisLabel> verLables = verticalAxis.getAxisLabelSetting().getLabels();
        Rect fontRect = null;
        for (AxisLabel label : verLables)
        {
            float yLabel = areaRect.bottom - Float.valueOf(String.valueOf(label.getPosition() * areaRect.height()));
            fontRect = DrawUtils.getTextRect(label.getText(), fontSize);
            float lastYLabel = yLabel + fontRect.height() / 2;
            paint.setColor(axisStroke.getColor());
            canvas.drawLine(paddingLeft - 5, yLabel, paddingLeft, yLabel, paint);
            paint.setColor(axisStroke.getTextColor());
            if (showYGridLine && label.getPosition() != 0)
                grideLine.drawYGridLine(canvas, paddingLeft, yLabel, areaRect);
            DrawUtils.drawText(canvas, label.getText(), paddingLeft - fontSize * 4 / 3 - 5, lastYLabel, paint,
                        verticalAxis.getTextAngle());
        }
        if (verticalAxis.getDisplayName() != null && fontRect != null)
            DrawUtils.drawText(canvas, verticalAxis.getDisplayName(), paddingLeft - fontSize * 4 / 3 - fontRect.width()
                        / 2, paddingTop + areaRect.height() / 2 - fontRect.height(), paint, 90);
    }

    public void run(Canvas canvas)
    {
        super.run(canvas);
        if (dataProvider == null)
        {
            Log.v("Chart DataProvider---", "DataProvider is null");
            return;
        }
        pointMapping.clear();
        setAxisValueFields();
        horizonAxis.accept(this);
        verticalAxis.accept(this);
        horizonAxis.setDataProvider(dataProvider);
        verticalAxis.setDataProvider(dataProvider);
        drawAxis(canvas);
        drawSeriesHandler(canvas);
    }

    private void setAxisValueFields()
    {
        if (verticalAxis instanceof LinearAxis)
        {
            LinearAxis linearAxis = (LinearAxis) verticalAxis;
            linearAxis.setValueFields(getValueFields());
        }
        else if (horizonAxis instanceof LinearAxis)
        {
            LinearAxis linearAxis = (LinearAxis) horizonAxis;
            linearAxis.setValueFields(getValueFields());
        }
    }

    public LocalPoint getPointForScreenCoordinate(LocalPoint screenPoint)
    {
        return pointMapping.getClickPoint(screenPoint);
    }

    protected void drawSeriesHandler(Canvas canvas)
    {
        for (ISeries series : mSeries)
        {
            LineSeries lineSeries = (LineSeries) series;
            setSeriesProperty(lineSeries);
            series.onDrawHandler(canvas);
        }
    }

    protected void setSeriesProperty(CartesianSeries series)
    {
        series.setOffset(offsetX, offsetY);
        series.setScale(scale);
        series.setDataProvider(dataProvider);
        series.setFontSize(fontSize);
        series.setPiontMapping(pointMapping);
        series.setHorizontalAxis(horizonAxis);
        series.setVerticalAxis(verticalAxis);
        series.setPadding(paddingLeft, paddingTop, paddingBottom, paddingRight);
    }

    private String[] getValueFields()
    {
        int offset = 0;
        int size = mSeries.size();
        String[] valueFileds = new String[size];
        for (ISeries serie : mSeries)
        {
            valueFileds[offset] = serie.getValueField();
            if (offset != size - 1)
                offset++;
        }
        return valueFileds;
    }

    public void updateCategoryAxis(CategoryAxis categoryAxis)
    {

    }

    public void updateLinearAxis(LinearAxis linearAxis)
    {

    }

}
