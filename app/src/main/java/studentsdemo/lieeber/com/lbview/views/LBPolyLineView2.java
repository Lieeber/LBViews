package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lieeber on 5017/6/23.
 */

public class LBPolyLineView2 extends View {

    private final Paint linePaint;
    private final Paint circlePaint;
    private int viewSize;
    private float itemSize;
    private final Path linePath;
    private final Path circlePath;
    private PointF onePoint;
    private PointF twoPoint;
    private PointF threePoint;
    private PointF fourPoint;

    private int touchposition = 0;

    public LBPolyLineView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(6f);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setColor(Color.YELLOW);
        circlePaint.setStyle(Style.FILL);
        linePath = new Path();
        circlePath = new Path();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewSize = w;
        itemSize = viewSize / 5.0f;

        onePoint = new PointF(itemSize * 1f, itemSize * 1f);
        twoPoint = new PointF(itemSize * 2f, itemSize * 2f);
        threePoint = new PointF(itemSize * 3f, itemSize * 3f);
        fourPoint = new PointF(itemSize * 4f, itemSize * 4f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            float touchX = event.getX();
            float touchY = event.getY();
            if (onePoint.x + 50 > touchX && onePoint.x - 50< touchX &&onePoint.y + 50 > (viewSize - touchY) && onePoint.y - 50 < (viewSize - touchY)) {
                touchposition = 1;
            }else  if (twoPoint.x + 50 > touchX && twoPoint.x - 50< touchX &&twoPoint.y + 50 > (viewSize - touchY) && twoPoint.y - 50 < (viewSize - touchY)) {
                touchposition = 2;
            }else  if (threePoint.x + 50 > touchX && threePoint.x - 50< touchX &&threePoint.y + 50 > (viewSize - touchY) && threePoint.y - 50 < (viewSize - touchY)) {
                touchposition = 3;
            } else if (fourPoint.x + 50 > touchX && fourPoint.x - 50 < touchX && fourPoint.y + 50 > (viewSize - touchY) && fourPoint.y - 50 < (viewSize - touchY)) {
                touchposition = 4;
            } else {
                touchposition = 0;
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            switch (touchposition) {
                case 1:
                    onePoint.y = viewSize - event.getY();
                    break;
                case 2:
                    twoPoint.y = viewSize - event.getY();
                    break;
                case 3:
                    threePoint.y = viewSize - event.getY();
                    break;
                case 4:
                    fourPoint.y = viewSize - event.getY();
                    break;
            }
        }

        invalidate();
        return true;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0f, viewSize);
        canvas.scale(1F, -1F);
        canvas.drawARGB(255, 155, 77, 0);



        linePath.moveTo(onePoint.x, onePoint.y);
        linePath.lineTo(twoPoint.x, twoPoint.y);
        linePath.lineTo(threePoint.x, threePoint.y);
        linePath.lineTo(fourPoint.x, fourPoint.y);
        linePaint.setPathEffect(new CornerPathEffect(50f));

        circlePath.addCircle(onePoint.x, onePoint.y, 50, Direction.CCW);
        circlePath.addCircle(twoPoint.x, twoPoint.y, 50, Direction.CCW);
        circlePath.addCircle(threePoint.x, threePoint.y, 50, Direction.CCW);
        circlePath.addCircle(fourPoint.x, fourPoint.y, 50, Direction.CCW);

        canvas.drawPath(linePath, linePaint);
        canvas.drawPath(circlePath, circlePaint);
        linePath.reset();
        circlePath.reset();
    }
}
