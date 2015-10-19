package com.lecast.smartchart.series;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.lecast.smartchart.axis.AxisLabel;
import com.lecast.smartchart.axis.AxisLabelSetting;
import com.lecast.smartchart.graphics.stroke.IStroke;
import com.lecast.smartchart.graphics.stroke.Stroke;

public class AreaSeries extends CartesianSeries
{

    private Paint paintBackground = new Paint();

    private boolean drawBackground = true;

    public AreaSeries(Context context, IStroke stroke)
    {
        super(context);
        if (stroke == null)
        {
            this.stroke = Stroke.YELLOW;
            return;
        }
        this.stroke = stroke;
    }

    public AreaSeries(Context context)
    {
        super(context);
        this.stroke = Stroke.YELLOW;
    }

    public void onDrawHandler(Canvas canvas)
    {
        super.onDrawHandler(canvas);
        AxisLabelSetting horAxisLabelSetting = categoryAxis.getAxisLabelSetting();
        List<AxisLabel> axisLables = horAxisLabelSetting.getLabels();
        Paint paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        Path backgroundPath = new Path();
        int size = axisLables.size();
        float startX = 0;
        float startY = 0;
        for (int index = 0; index < size; index++)
        {
            float stopX =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            float stopY =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / verAxis.getMaxValue())));
            Log.v("Draw Point", stopY + "---");
            if (drawBackground)
            {
                if (index == 0)
                {
                    backgroundPath.moveTo(stopX, areaRect.bottom);
                }
                backgroundPath.lineTo(stopX, stopY);
                if (index == (dataSize - 1))
                {
                    backgroundPath.lineTo(stopX, areaRect.bottom);
                }
            }
            addClickPoint(index, stopX, stopY);
            if (index > 0)
                canvas.drawLine(startX, startY, stopX, stopY, paint);
            // canvas.drawCircle(stopX, stopY, 6, paint);
            startX = stopX;
            startY = stopY;
        }

        if (drawBackground)
        {
            // paintBackground.setARGB(100, 20, 40, 60);
            paintBackground.setAlpha(100);
            paintBackground.setColor(stroke.getColor());
            canvas.drawPath(backgroundPath, paintBackground);
        }
    }

    protected void drawBackGroundHandler(Canvas canvas, List<AxisLabel> axisLables)
    {
        double lastEndY = 0;
        double lastEndX = 0;
        float startY = areaRect.bottom;// graphheight + border;
        float horizontalStart = 10;
        paintBackground.setStrokeWidth(4);
        paintBackground.setColor(0xffcccccc);
        for (int index = 0; index < dataSize; index++)
        {
            float endX =
                        Float.parseFloat(String.valueOf(axisLables.get(index).getPosition() * areaRect.width()))
                                    + paddingLeft;
            float endY =
                        (float) (areaRect.bottom - (Float.parseFloat(String.valueOf(propertyValues[index])) * (areaRect
                                    .height() / verAxis.getMaxValue())));
            if (index > 0)
            {
                double numSpace = ((endX - lastEndX) / 2f) + 1;
                for (int xi = 0; xi < numSpace; xi++)
                {
                    float spaceX = (float) (lastEndX + ((endX - lastEndX) * xi / (numSpace - 1)));
                    float spaceY = (float) (lastEndY + ((endY - lastEndY) * xi / (numSpace - 1)));
                    float startX = spaceX;
                    if (startX - horizontalStart > 1)
                    {
                        canvas.drawLine(startX, startY, spaceX, spaceY + 1, paintBackground);
                    }
                }
            }
            lastEndY = endY;
            lastEndX = endX;
        }
    }

}
