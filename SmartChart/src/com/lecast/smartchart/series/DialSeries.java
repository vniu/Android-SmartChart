package com.lecast.smartchart.series;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import com.lecast.smartchart.chart.ChartView;
import com.lecast.smartchart.utils.DrawUtils;

/**
 * @author vincent
 * 
 */
public class DialSeries extends RoundSeries
{

    private float currtValue = 0;

    private double minValue = 0;

    private double maxValue = 100;

    private Canvas canvas;

    private Paint painter = new Paint();

    private boolean isRotate = true;

    private float rotateValue = 30;

    private float endAngle = 0;

    private Drawable drawable;

    private float minAngle = 0;

    private float maxAngle = 0;

    private float angleSpacer = 0;

    public DialSeries(Context context)
    {
        super(context);
    }

    /**
     * It's will execute draw handler and show dial shape by data.
     * 
     */
    public void onDrawHandler(Canvas canvas)
    {
        this.canvas = canvas;
        Bitmap pointer = BitmapFactory.decodeStream(ChartView.class.getResourceAsStream("image/pointer.png"));
        drawable = new BitmapDrawable(pointer);
        int w = drawable.getIntrinsicWidth() - 30;
        int h = drawable.getIntrinsicHeight();
        float lastCenterX = centerX - h / 2;
        float lastCenterY = centerY - h / 2;
        drawable.setBounds((int) centerX, (int) lastCenterY, (int) (lastCenterX + w), (int) (lastCenterY + h));
        drawPointerHandler();
        painter.setAntiAlias(true);
        painter.setShadowLayer(5, 3, 3, 0xFFc2c2c2);
        canvas.drawCircle(centerX, centerY, 15, painter);
        painter.setColor(0xff000000);
        painter.setTextSize(fontSize);
        suffixName = suffixName == null ? "" : suffixName;
        String lastValue = currtValue + suffixName;
        Rect valueRect = DrawUtils.getTextRect(lastValue + "", fontSize);
        float lastX = centerX - valueRect.width() / 2;
        float lastY = centerY + valueRect.height() + radius / 2;
        DrawUtils.drawText(canvas, lastValue, lastX, lastY, painter, 0);
        if (displayName != null)
        {
            Rect textRect = DrawUtils.getTextRect(displayName, fontSize);
            lastX = centerX - textRect.width() / 2;
            lastY = centerY + textRect.height() + radius / 2 + valueRect.height() + 10;
            DrawUtils.drawText(canvas, displayName, lastX, lastY, painter, 0);
        }
    }

    /**
     * For data update and draw pointer direction.
     * 
     */
    private void drawPointerHandler()
    {
        canvas.save();
        canvas.rotate(endAngle, centerX, centerY);
        drawable.draw(canvas);
        canvas.restore();
    }

    public void setCurrtValue(float currtValue)
    {
        this.currtValue = currtValue;
        getAngleForValue(currtValue);
    }

    private double getAngleForValue(double value)
    {
        double angleDiff = maxAngle - minAngle;
        double diff = maxValue - minValue;
        rotateValue = (float) Math.abs(((value - minValue) * angleDiff / diff));
        angleSpacer = (float) rotateValue / 10;
        endAngle = minAngle + rotateValue;
        return minAngle + rotateValue;
    }

    public void dataChange()
    {
        rotateHandler.sendEmptyMessage(0);
    }

    private Handler rotateHandler = new Handler()
    {

        public void handleMessage(Message msg)
        {
            endAngle = endAngle + angleSpacer;
            if (endAngle >= minAngle + rotateValue)
            {
                endAngle = minAngle + rotateValue;
                isRotate = false;
            }
            if (repaintHandler != null)
                repaintHandler.repaintHandler();
            if (isRotate)
            {
                rotateHandler.sendEmptyMessageDelayed(0, 200);
            }
        }

    };

    public void setMinAngle(float minAngle)
    {
        this.minAngle = (float) (minAngle - 180);
        endAngle = this.minAngle;
    }

    public void setMinValue(double minValue)
    {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue)
    {
        this.maxValue = maxValue;
    }

    public float getMinAngle()
    {
        return minAngle;
    }

    public float getMaxAngle()
    {
        return maxAngle;
    }

    public void setMaxAngle(float maxAngle)
    {
        this.maxAngle = maxAngle - 180;
    }
}
