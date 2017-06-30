package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lieeber on 2017/6/29.
 * 自定义遥控器按钮，实现不规则区域的点击事件。
 */

public class RemoteControlMenu extends View {
    private final Region topRegion;
    private final Region rightRegion;
    private final Region bottomRegion;
    private final Region leftRegion;
    private final Region centerRegion;
    private int viewWidth;
    private int viewHeight;
    private final Path topPath;
    private final Path rightPath;
    private final Path bottomPath;
    private final Path leftPath;
    private final Path centerPath;
    private float bigRadius;
    private float smallRadius;
    private final Paint mPaint;


    private int clickState = 0;
    private final int CLICK_TOP = 1;
    private final int CLICK_RIGHT = 2;
    private final int CLICK_BOTTOM = 3;
    private final int CLICK_LEFT = 4;
    private final int CLICK_CENTER = 5;
    private int CLICK_NONE = 6;
    private final Matrix mMatrix;

    public RemoteControlMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        topPath = new Path();
        rightPath = new Path();
        bottomPath = new Path();
        leftPath = new Path();
        centerPath = new Path();


        topRegion = new Region();
        rightRegion = new Region();
        bottomRegion = new Region();
        leftRegion = new Region();
        centerRegion = new Region();


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFF4E5268);

        mMatrix = new Matrix();
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Region globalRegion = new Region(-w, -h, w, h);
        viewWidth = w;
        viewHeight = h;


        bigRadius = viewWidth / 2.0f - dip2px(50);
        smallRadius = bigRadius - dip2px(80);
        float smallArcRadius = bigRadius - dip2px(68);

        float bigCircleGap = 5;
        //通过大圆的角度，算弧长，小圆在等弧长的情况下转过的角度
        float huchang = (float) (2 * Math.PI * bigRadius * bigCircleGap / 360);
        //获取小圆弧转过的间隙角度
        float smallArcGap = (float) (huchang / 2 / Math.PI / smallArcRadius * 360);

        float bigSweepAngle = 90 - bigCircleGap;
        float smallSweepAngle = -90 + smallArcGap;

        RectF bigCircle = new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius);

        RectF smallCircle = new RectF(-smallArcRadius, -smallArcRadius, smallArcRadius, smallArcRadius);

        topPath.addArc(bigCircle, -135 + bigCircleGap / 2, bigSweepAngle);
        topPath.arcTo(smallCircle, -45 - smallArcGap / 2, smallSweepAngle);
        topPath.close();
        topRegion.setPath(topPath, globalRegion);


        rightPath.addArc(bigCircle, -45 + bigCircleGap / 2, bigSweepAngle);
        rightPath.arcTo(smallCircle, 45 - smallArcGap / 2, smallSweepAngle);
        rightPath.close();
        rightRegion.setPath(rightPath, globalRegion);


        bottomPath.addArc(bigCircle, 45 + bigCircleGap / 2, bigSweepAngle);
        bottomPath.arcTo(smallCircle, 135 - smallArcGap / 2, smallSweepAngle);
        bottomPath.close();
        bottomRegion.setPath(bottomPath, globalRegion);


        leftPath.addArc(bigCircle, 135 + bigCircleGap / 2, bigSweepAngle);
        leftPath.arcTo(smallCircle, 225 - smallArcGap / 2, smallSweepAngle);
        leftPath.close();
        leftRegion.setPath(leftPath, globalRegion);


        centerPath.addCircle(0, 0, smallRadius, Direction.CW);
        centerRegion.setPath(centerPath, globalRegion);

    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(viewWidth / 2, viewHeight / 2);

        Matrix matrix = canvas.getMatrix();
        if (mMatrix.isIdentity()) {
            matrix.invert(mMatrix);
        }
        canvas.drawPath(topPath, mPaint);
        canvas.drawPath(rightPath, mPaint);
        canvas.drawPath(bottomPath, mPaint);
        canvas.drawPath(leftPath, mPaint);
        canvas.drawPath(centerPath, mPaint);


        mPaint.setColor(0xffe39d84);
        switch (clickState) {
            case CLICK_CENTER:
                canvas.drawPath(centerPath, mPaint);
                break;
            case CLICK_TOP:
                canvas.drawPath(topPath, mPaint);
                break;
            case CLICK_RIGHT:
                canvas.drawPath(rightPath, mPaint);
                break;
            case CLICK_BOTTOM:
                canvas.drawPath(bottomPath, mPaint);
                break;
            case CLICK_LEFT:
                canvas.drawPath(leftPath, mPaint);
                break;
        }
        mPaint.setColor(0xFF4E5268);
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float[] points = {x, y};
                mMatrix.mapPoints(points);
                x = points[0];
                y = points[1];
                if (centerRegion.contains((int) x, (int) y)) {
                    clickState = CLICK_CENTER;
                } else if (topRegion.contains((int) x, (int) y)) {
                    clickState = CLICK_TOP;
                } else if (rightRegion.contains((int) x, (int) y)) {
                    clickState = CLICK_RIGHT;
                } else if (bottomRegion.contains((int) x, (int) y)) {
                    clickState = CLICK_BOTTOM;
                } else if (leftRegion.contains((int) x, (int) y)) {
                    clickState = CLICK_LEFT;
                } else {
                    clickState = CLICK_NONE;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                clickState = CLICK_NONE;
                invalidate();
                break;
        }
        return true;
    }

    public float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
}
