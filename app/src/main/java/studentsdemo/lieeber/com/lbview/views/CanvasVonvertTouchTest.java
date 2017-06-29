package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;

import studentsdemo.lieeber.com.lbview.util.CanvasAidUtils;

/**
 * Created by lieeber on 2017/6/28.
 *
 * 我们在画布上正常的绘制，需要将画布坐标系转换为全局坐标系后才能真正的绘制内容。所以我们反着来，将获得到的全局坐标系坐标使用当前画布的逆矩阵转化一下，
 * 就转化为当前画布的坐标系坐标了，如果对 Matrix原理 和 Matrix详解 理解了，即便我不说你们也肯定会想到这个方案的。
 */
public class CanvasVonvertTouchTest extends CustomView{
    float down_x = -1;
    float down_y = -1;

    public CanvasVonvertTouchTest(Context context) {
        this(context, null);
    }

    public CanvasVonvertTouchTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // ▼ 注意此处使用 getRawX，而不是 getX
                down_x = event.getRawX();
                down_y = event.getRawY();
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                down_x = down_y = -1;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        float drawX = down_x - mViewWidth / 2;
//        float drawY = down_y - mViewHeight / 2;
//        float[] pts = {drawX, drawY};
        float[] pts = {down_x, down_y};


        drawTouchCoordinateSpace(canvas);            // 绘制触摸坐标系，灰色
        // ▼注意画布平移
        canvas.translate(mViewWidth/2, mViewHeight/2);

        drawTranslateCoordinateSpace(canvas);        // 绘制平移后的坐标系，红色

        if (pts[0] == -1 && pts[1] == -1) return;    // 如果没有就返回

//         ▼ 获得当前矩阵的逆矩阵
        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);

//         ▼ 使用 mapPoints 将触摸位置转换为画布坐标
        invertMatrix.mapPoints(pts);

        // 在触摸位置绘制一个小圆
        canvas.drawCircle(pts[0],pts[1],20,mDeafultPaint);
    }

    /**
     *  绘制触摸坐标系，颜色为灰色，为了能够显示出坐标系，将坐标系位置稍微偏移了一点
     */
    private void drawTouchCoordinateSpace(Canvas canvas) {
        canvas.save();
        canvas.translate(10,10);
        CanvasAidUtils.set2DAxisLength(1000, 0, 1400, 0);
        CanvasAidUtils.setLineColor(Color.GRAY);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        canvas.restore();
    }

    /**
     * 绘制平移后的坐标系，颜色为红色
     */
    private void drawTranslateCoordinateSpace(Canvas canvas) {
        CanvasAidUtils.set2DAxisLength(500, 500, 700, 700);
        CanvasAidUtils.setLineColor(Color.RED);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);
    }
}