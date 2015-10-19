package com.lecast.smartchart.series;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.Log;

import com.lecast.smartchart.axis.AxisLabel;
import com.lecast.smartchart.axis.AxisLabelSetting;
import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.graphics.stroke.IStroke;
import com.lecast.smartchart.graphics.stroke.Stroke;

public class LineSeries extends CartesianSeries
{

    public static String CUBIC = "curve";

    public static String SEGMENT = "segment";

    private String form = "segment";

    public LineSeries(Context context, IStroke stroke)
    {
        super(context);
        if (stroke == null)
        {
            this.stroke = Stroke.YELLOW;
            return;
        }
        this.stroke = stroke;
    }

    public LineSeries(Context context)
    {
        super(context);
        this.stroke = Stroke.YELLOW;
    }

    public void setForm(String form)
    {
        this.form = form;
    }

    public void onDrawHandler(Canvas canvas)
    {
        super.onDrawHandler(canvas);
        AxisLabelSetting horAxisLabelSetting = categoryAxis.getAxisLabelSetting();
        List<AxisLabel> axisLables = horAxisLabelSetting.getLabels();
        int size = axisLables.size();
        float startX = 0;
        float startY = 0;
        float stopX = 0;
        float stopY = 0;
        float[] points = null;
        paint.setStrokeWidth(stroke.getStrokeWidth());
        if (form.equals(CUBIC))
        {
            points = getPoints(verAxis.getMaxValue(), axisLables);
            drawPath(canvas, points, paint, false);
            return;
        }
        // startX =
        // Float.parseFloat(String.valueOf(axisLables.get(0).getPosition() *
        // areaRect.width())) + paddingLeft;
        // startY =
        // (float) (areaRect.bottom -
        // (Float.parseFloat(String.valueOf(propertyValues[0])) * (areaRect
        // .height() / verAxis.getMaxValue())));
        // canvas.drawCircle(startX, startY, 6, paint);
        // addClickPoint(0, startX, startY);
        for (int index = 0; index < size; index++)
        {
            stopX =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            stopY =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / verAxis.getMaxValue())));
            Log.v("Draw Point", stopY + "---");
            addClickPoint(index, stopX, stopY);
            if (index > 0)
                canvas.drawLine(startX, startY, stopX, stopY, paint);
            canvas.drawCircle(stopX, stopY, 6, paint);
            startX = stopX;
            startY = stopY;
        }
    }

    private float firstMultiplier = 0.2f;

    private float secondMultiplier = (1 - firstMultiplier);

    private LocalPoint p1 = new LocalPoint();

    private LocalPoint p2 = new LocalPoint();

    private LocalPoint p3 = new LocalPoint();

    protected void drawPath(Canvas canvas, float[] points, Paint paint, boolean circular)
    {
        Path p = new Path();
        float x = points[0];
        float y = points[1];
        p.moveTo(x, y);
        addClickPoint(0, x, y);
        int length = points.length;
        if (circular)
        {
            length -= 4;
        }
        paint.setStyle(Style.FILL);
        canvas.drawCircle(x, y, 4, paint);
        int count = 1;
        for (int i = 0; i < length; i += 2)
        {
            int nextIndex = i + 2 < length ? i + 2 : i;
            int nextNextIndex = i + 4 < length ? i + 4 : nextIndex;
            calc(points, p1, i, nextIndex, secondMultiplier);
            p2.setX(points[nextIndex]);
            p2.setY(points[nextIndex + 1]);
            calc(points, p3, nextIndex, nextNextIndex, firstMultiplier);
            canvas.drawCircle(p3.getX() - 6, p3.getY() + 3, 8, paint);
            addClickPoint(count, p3.getX(), p3.getY());
            count++;
            p.cubicTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        }
        if (circular)
        {
            for (int i = length; i < length + 4; i += 2)
            {
                p.lineTo(points[i], points[i + 1]);
            }
            p.lineTo(points[0], points[1]);
        }
        paint.setStyle(Style.STROKE);
        canvas.drawPath(p, paint);
    }

    private void calc(float[] points, LocalPoint result, int index1, int index2, final float multiplier)
    {
        float p1x = points[index1];
        float p1y = points[index1 + 1];
        float p2x = points[index2];
        float p2y = points[index2 + 1];
        float diffX = p2x - p1x; // p2.x - p1.x;
        float diffY = p2y - p1y; // p2.y - p1.y;
        result.setX(p1x + (diffX * multiplier));
        result.setY(p1y + (diffY * multiplier));
    }

}
