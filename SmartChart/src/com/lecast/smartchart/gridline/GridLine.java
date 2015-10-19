package com.lecast.smartchart.gridline;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lecast.smartchart.graphics.stroke.IStroke;
import com.lecast.smartchart.graphics.stroke.Stroke;

/**
 * @author vincent
 * 
 */
public class GridLine
{

    private IStroke stroke;

    private Paint paint = new Paint();

    public void setStroke(IStroke stroke)
    {
        if (stroke == null)
            return;
        this.stroke = stroke;
    }

    public GridLine(IStroke stroke)
    {
        if (stroke == null)
        {
            this.stroke = Stroke.GRAYSOLID;
            return;
        }
        this.stroke = stroke;
    }

    public void drawYGridLine(Canvas canvas, float x, float y, RectF rect)
    {
        paint.setColor(stroke.getColor());
        paint.setStrokeWidth(stroke.getStrokeWidth());
        paint.setStrokeJoin(stroke.getJoin());
        paint.setStrokeCap(stroke.getCap());
        canvas.drawLine(x, y, rect.right, y, paint);
    }

    public void drawXGridLine(Canvas canvas, float x, float y, RectF rect)
    {
        paint.setColor(stroke.getColor());
        paint.setStrokeWidth(stroke.getStrokeWidth());
        paint.setStrokeJoin(stroke.getJoin());
        paint.setStrokeCap(stroke.getCap());
        canvas.drawLine(x, y, x, rect.top, paint);
    }
}
