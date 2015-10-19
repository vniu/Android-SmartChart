package com.lecast.smartchart.series;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;

import com.alibaba.fastjson.JSONObject;
import com.lecast.smartchart.common.LabelPoint;
import com.lecast.smartchart.utils.DrawUtils;
import com.lecast.smartchart.utils.ReflectUtils;

/**
 * @author vincent
 * 
 */
public class PieSeries extends RoundSeries
{

    private String[] categoryNames;

    private boolean showLabel = true;

    private Paint paint = new Paint();

    public PieSeries(Context context)
    {
        super(context);
    }

    /**
     * ִ�л���ͼ����
     * 
     */
    public void onDrawHandler(Canvas canvas)
    {
        super.onDrawHandler(canvas);
        paint.setAntiAlias(true);
        float lastCenterX = centerX;
        float lastCenterY = centerY;
        float lastRadius = radius;
        setCenterHandler(Math.abs((centerX + offsetX) * scale), Math.abs((centerY + offsetY) * scale), radius * scale);// lastCenterX,
        RectF oval =
                    new RectF(lastCenterX - lastRadius, lastCenterY - lastRadius, lastCenterX + lastRadius, lastCenterY
                                + lastRadius);
        float totalValue = 0;
        for (int index = 0; index < dataSize; index++)
        {
            totalValue += Float.valueOf(String.valueOf(propertyValues[index]));
        }
        float currentAngle = 0;
        List<RectF> prevLabelsBounds = new ArrayList<RectF>();
        float shortRadius = lastRadius * 0.9f;
        float longRadius = lastRadius * 1.1f;
        paint.setShadowLayer(5f, 5.0f, 5.0f, Color.BLACK);
        canvas.drawArc(oval, 0, 360, true, paint);
        paint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        categoryNames = new String[dataSize];
        for (int index = 0; index < dataSize; index++)
        {
            int color = colorsCache[index];
            LinearGradient mShader =
                        new LinearGradient(lastCenterX + radius, lastCenterY + radius, lastCenterX, lastCenterY,
                                    new int[] { DrawUtils.adjustBrightness2(color, 10),
                                                DrawUtils.adjustBrightness2(color, 60) }, null, Shader.TileMode.REPEAT);
            Matrix localM = new Matrix();
            mShader.setLocalMatrix(localM);
            paint.setShader(mShader);
            paint.setColor(color);
            paint.setStyle(Style.FILL);
            float value = Float.valueOf(String.valueOf(propertyValues[index]));
            double precentValue = value * 100 / totalValue;
            float angle = (float) (value / totalValue * 360);
            addClickPoint(index, angle);
            canvas.drawArc(oval, currentAngle, angle, true, paint);
            paint.setShader(null);
            String categoryName = getCategoryName(index);
            categoryNames[index] = categoryName;
            if (showLabel)
            {
                drawLabel(canvas, categoryName, prevLabelsBounds, (int) lastCenterX, (int) lastCenterY, shortRadius,
                            longRadius, currentAngle, angle, 30, 30, 0xff000000, paint, true, false);
                drawLabel(canvas, formaterValue(precentValue), prevLabelsBounds, (int) lastCenterX, (int) lastCenterY,
                            shortRadius / 2, longRadius / 2, currentAngle, angle, 30, 30, 0xff000000, paint, false);
            }
            currentAngle += angle;
        }
        prevLabelsBounds.clear();
    }

    protected void drawLabel(Canvas canvas, String labelText, List<RectF> prevLabelsBounds, int centerX, int centerY,
                float shortRadius, float longRadius, float currentAngle, float angle, int left, int right, int color,
                Paint paint, boolean line, boolean showMoreLabel)
    {

        LabelPoint labelPoint =
                    drawLabelHandler(canvas, labelText, prevLabelsBounds, centerX, centerY, shortRadius, longRadius,
                                currentAngle, angle, left, right, color, paint, line);
        String[] texts = labelText.split("/");
        for (int index = 0; index < texts.length; index++)
        {
            canvas.drawText(texts[index], labelPoint.xLabel, labelPoint.yLabel, paint);
            FontMetrics fm = paint.getFontMetrics();
            labelPoint.yLabel = labelPoint.yLabel + Math.round(fm.descent - fm.top) + 2;
        }
        if (line)
        {
            prevLabelsBounds.add(new RectF(labelPoint.xLabel, labelPoint.yLabel, labelPoint.xLabel
                        + labelPoint.widthLabel, labelPoint.yLabel + labelPoint.size));
        }
    }

    /**
     * ��ȡ����ֵ
     * 
     */
    private String getCategoryName(int index)
    {
        suffixName = suffixName == null ? "" : suffixName;
        try
        {
            if (isJsonFormat)
            {
                JSONObject jsonObject = jsonArraySources.getJSONObject(index);
                return jsonObject.getString(labelField) + suffixName;
            }

            Object object = dataSources.get(index);
            return (String) ReflectUtils.getProperty(object, labelField) + suffixName;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String formaterValue(double value)
    {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(value) + "%";
    }

    protected void drawLabel(Canvas canvas, String labelText, List<RectF> prevLabelsBounds, int centerX, int centerY,
                float shortRadius, float longRadius, float currentAngle, float angle, int left, int right, int color,
                Paint paint, boolean line)
    {
        LabelPoint labelPoint =
                    drawLabelHandler(canvas, labelText, prevLabelsBounds, centerX, centerY, shortRadius, longRadius,
                                currentAngle, angle, left, right, color, paint, line);
        canvas.drawText(labelText, labelPoint.xLabel, labelPoint.yLabel, paint);
        if (line)
        {
            prevLabelsBounds.add(new RectF(labelPoint.xLabel, labelPoint.yLabel, labelPoint.xLabel
                        + labelPoint.widthLabel, labelPoint.yLabel + labelPoint.size));
        }
    }

    protected LabelPoint drawLabelHandler(Canvas canvas, String labelText, List<RectF> prevLabelsBounds, int centerX,
                int centerY, float shortRadius, float longRadius, float currentAngle, float angle, int left, int right,
                int color, Paint paint, boolean line)
    {

        paint.setColor(color);
        double rAngle = Math.toRadians(90 - (currentAngle + angle / 2));
        double sinValue = Math.sin(rAngle);
        double cosValue = Math.cos(rAngle);
        int x1 = Math.round(centerX + (float) (shortRadius * sinValue));
        int y1 = Math.round(centerY + (float) (shortRadius * cosValue));
        int x2 = Math.round(centerX + (float) (longRadius * sinValue));
        int y2 = Math.round(centerY + (float) (longRadius * cosValue));

        float extra = Math.max(fontSize / 2, 10);
        paint.setTextAlign(Align.LEFT);
        if (x1 > x2)
        {
            extra = -extra;
            paint.setTextAlign(Align.RIGHT);
        }
        float xLabel = x2 + extra;
        float yLabel = y2;
        float width = right - xLabel;
        if (x1 > x2)
        {
            width = xLabel - left;
        }
        labelText = getFitText(labelText, width, paint);
        float widthLabel = paint.measureText(labelText);
        boolean okBounds = false;
        while (!okBounds && line)
        {
            boolean intersects = false;
            int length = prevLabelsBounds.size();
            for (int j = 0; j < length && !intersects; j++)
            {
                RectF prevLabelBounds = prevLabelsBounds.get(j);
                if (prevLabelBounds.intersects(xLabel, yLabel, xLabel + widthLabel, yLabel + fontSize))
                {
                    intersects = true;
                    yLabel = Math.max(yLabel, prevLabelBounds.bottom);
                }
            }
            okBounds = !intersects;
        }

        if (line)
        {
            y2 = (int) (yLabel - fontSize / 2);
            canvas.drawLine(x1, y1, x2, y2, paint);
            canvas.drawLine(x2, y2, x2 + extra, y2, paint);
        }
        else
        {
            paint.setTextAlign(Align.CENTER);
        }
        return new LabelPoint(xLabel, yLabel, widthLabel, fontSize);
    }

    private String getFitText(String text, float width, Paint paint)
    {
        String newText = text;
        int length = text.length();
        int diff = 0;
        while (paint.measureText(newText) > width && diff < length)
        {
            diff++;
            newText = text.substring(0, length - diff) + "...";
        }
        if (diff == length)
        {
            newText = "...";
        }
        return newText;
    }

    public String[] getCategoryNames()
    {
        return categoryNames;
    }

    public boolean isShowLabel()
    {
        return showLabel;
    }

    public void setShowLabel(boolean showLabel)
    {
        this.showLabel = showLabel;
    }

}
