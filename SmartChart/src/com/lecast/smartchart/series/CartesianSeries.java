package com.lecast.smartchart.series;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.lecast.smartchart.axis.AxisLabel;
import com.lecast.smartchart.axis.CategoryAxis;
import com.lecast.smartchart.axis.IAxis;
import com.lecast.smartchart.axis.LinearAxis;
import com.lecast.smartchart.common.LocalPoint;

public class CartesianSeries extends Series
{

    protected IAxis horizontalAxis;

    protected IAxis verticalAxis;

    protected int paddingLeft = 5;

    protected int paddingTop = 10;

    protected int paddingBottom = 10;

    protected int paddingRight = 5;

    protected RectF areaRect;

    protected Paint paint = new Paint();

    protected CategoryAxis categoryAxis = null;

    protected LinearAxis verAxis = null;

    public CartesianSeries(Context context)
    {
        super(context);
    }

    public void setHorizontalAxis(IAxis horizonAxis)
    {
        this.horizontalAxis = horizonAxis;
    }

    public void setVerticalAxis(IAxis verticalAxis)
    {
        this.verticalAxis = verticalAxis;
    }

    public void setPadding(int paddingLeft, int paddingTop, int paddingBottom, int paddingRight)
    {
        this.paddingLeft = paddingLeft;
        this.paddingBottom = paddingBottom;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
    }

    protected void convertAxisClass()
    {
        if (horizontalAxis instanceof LinearAxis)
        {
            verAxis = (LinearAxis) horizontalAxis;
        }
        else
        {
            categoryAxis = (CategoryAxis) horizontalAxis;

        }
        if (verticalAxis instanceof CategoryAxis)
        {
            categoryAxis = (CategoryAxis) verticalAxis;
        }
        else
        {
            verAxis = (LinearAxis) verticalAxis;
        }
    }

    public void onDrawHandler(Canvas canvas)
    {
        int width = getChart().getChartView().getWidth();// canvas.getWidth();
        int height = getChart().getChartView().getHeight();// canvas.getHeight();
        areaRect = new RectF(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        // Paint p = new Paint();
        // Shader mShader =
        // new LinearGradient(0, 0, 100, 100, new int[] { Color.RED,
        // Color.GREEN, Color.BLUE, Color.YELLOW,
        // Color.LTGRAY }, null, Shader.TileMode.REPEAT);
        //
        // Matrix localM = new Matrix();
        // mShader.setLocalMatrix(localM);
        // p.setShader(mShader);

        paint.setAntiAlias(true);
        paint.setColor(stroke.getColor());
        paint.setStrokeWidth(stroke.getStrokeWidth());
        paint.setStrokeCap(stroke.getCap());
        paint.setStrokeJoin(stroke.getJoin());
        convertAxisClass();
    }

    protected float[] getPoints(double maxValue, List<AxisLabel> axisLables)
    {
        float[] points = new float[dataSize * 2];
        for (int index = 0; index < dataSize; index++)
        {
            points[2 * index] =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            points[2 * index + 1] =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / maxValue)));
        }
        return points;
    }

    protected List<LocalPoint> getListPoints(double maxValue, List<AxisLabel> axisLables)
    {
        List<LocalPoint> points = new ArrayList<LocalPoint>();
        for (int index = 0; index < dataSize; index++)
        {
            float pointX =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            float pointY =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / maxValue)));
            LocalPoint point = new LocalPoint(pointX, pointY);
            points.add(point);
        }
        return points;
    }

    protected void addClickPoint(int index, float x, float y)
    {
        Object object = null;
        try
        {
            object = isJsonFormat ? jsonArraySources.get(index) : dataSources.get(index);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        LocalPoint touchPoint =
                    new LocalPoint(Math.abs((x + offsetX) * scale), Math.abs((y + offsetY) * scale), object);
        touchPoint.setRect(getClickableRect(touchPoint.getX(), touchPoint.getY()));
        Log.v("Click Point add", touchPoint.getX() + "+++++++++" + touchPoint.getY());
        Log.v("Click Point add", scale + "----scale----++" + offsetX + "---offsetX----++" + offsetY + "---offsetY-----");
        pointMapping.addPoint(touchPoint);
    }

    protected Rect getClickableRect(float x, float y)
    {
        float scaleFactor = 30;
        int left = (int) (x - scaleFactor);
        int top = (int) (y - scaleFactor);
        int right = (int) (x + scaleFactor);
        int bottom = (int) (y + scaleFactor);
        return new Rect(left, top, right, bottom);
    }
}
