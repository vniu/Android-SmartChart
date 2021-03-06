package com.lecast.smartchart.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lecast.smartchart.common.LocalPoint;
import com.lecast.smartchart.listener.IChartTouchListener;
import com.lecast.smartchart.tools.ITouchHandler;
import com.lecast.smartchart.tools.PanListener;
import com.lecast.smartchart.tools.TipWindowFactory;
import com.lecast.smartchart.tools.TouchHandler;
import com.lecast.smartchart.tools.ZoomEvent;
import com.lecast.smartchart.tools.ZoomListener;

/**
 * @author vincent
 * 
 */
public class ChartView extends View
{

    private ChartBase chart;

    private ITouchHandler touchHandler;

    private float oldX, oldY;

    private Context context;

    private Handler mHandler;

    private IChartTouchListener chartTouchListener;

    private Bitmap zoomInImage;

    private Bitmap zoomOutImage;

    private Bitmap fitZoomImage;

    private RectF mZoomR = new RectF();

    private Rect mRect = new Rect();

    private int zoomSize = 50;

    private static final int ZOOM_BUTTONS_COLOR = Color.argb(175, 150, 150, 150);

    public ChartView(Context context, ChartBase chart)
    {
        super(context);
        if (chart == null)
            throw new RuntimeException("Chart is null");
        this.chart = chart;
        this.context = context;
        touchHandler = new TouchHandler(this, chart);
        resigterListener();
        resigterTouchHandler();
        mHandler = new Handler();
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

    private LocalPoint getCurrentSeriesAndPoint()
    {
        return chart.getPointForScreenCoordinate(new LocalPoint(oldX, oldY));
    }

    private void resigterListener()
    {
        this.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                LocalPoint localPoint = getCurrentSeriesAndPoint();
                if (localPoint != null && localPoint.getValue() != null)
                {
                    TipWindowFactory.createWithImageToast(context, localPoint.getValue().toString(), 0).show();
                    Log.v("Click sucess", "click suces");
                    if (chartTouchListener != null)
                        chartTouchListener.execute(localPoint.getValue());
                }
            }
        });
    }

    private void resigterTouchHandler()
    {
        touchHandler.addPanListener(new PanListener()
        {

            public void panApplied()
            {
                repaintHandelr();
            }
        });
        touchHandler.addZoomListener(new ZoomListener()
        {

            public void zoomReset()
            {

            }

            public void zoomApplied(ZoomEvent e)
            {
                repaintHandelr();

            }
        });
    }

    public void onDraw(Canvas canvas)
    {
        canvas.scale(chart.getScale(), chart.getScale());
        if (chart.getOffsetX() != 0 || chart.getOffsetY() != 0)
            canvas.translate(chart.getOffsetX(), chart.getOffsetY());
        chart.run(canvas);
    }

    private void drawZoomTool(Canvas canvas)
    {
        canvas.getClipBounds(mRect);
        int top = mRect.top;
        int left = mRect.left;
        int width = mRect.width();
        int height = mRect.height();
        Paint mPaint = new Paint();
        mPaint.setColor(ZOOM_BUTTONS_COLOR);
        zoomSize = Math.max(zoomSize, Math.min(width, height) / 7);
        mZoomR.set(left + width - zoomSize * 3, top + height - zoomSize * 0.775f, left + width, top + height);
        canvas.drawRoundRect(mZoomR, zoomSize / 3, zoomSize / 3, mPaint);
        float buttonY = top + height - zoomSize * 0.625f;
        canvas.drawBitmap(zoomInImage, left + width - zoomSize * 2.75f, buttonY, null);
        canvas.drawBitmap(zoomOutImage, left + width - zoomSize * 1.75f, buttonY, null);
        canvas.drawBitmap(fitZoomImage, left + width - zoomSize * 0.75f, buttonY, null);
    }

    public void setChartTouchListener(IChartTouchListener chartTouchListener)
    {
        this.chartTouchListener = chartTouchListener;
    }

    private void repaintHandelr()
    {
        mHandler.post(new Runnable()
        {

            public void run()
            {
                invalidate();
            }
        });
    }

    public void restore()
    {
        chart.setScale(1);
        chart.restore();
    }
}
