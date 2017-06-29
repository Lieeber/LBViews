package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/21.
 */

public class WaveView extends View {
    private Path mPath;// 路径对象
    private Paint mPaint;// 画笔对象

    private int vWidth, vHeight;// 控件宽高
    private float ctrX, ctrY;// 控制点的xy坐标
    private float waveY;// 整个Wave顶部两端点的Y坐标，该坐标与控制点的Y坐标增减幅一致

    private boolean isInc;// 判断控制点是该右移还是左移

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化画笔并设置参数
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFFA2D6AE);

        // 实例化路径对象
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取控件宽高
        vWidth = w;
        vHeight = h;

        // 计算控制点Y坐标
        waveY = 1 / 8F * vHeight;

        // 计算端点Y坐标
        ctrY = -1 / 16F * vHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
         * 设置Path起点
         * 注意我将Path的起点设置在了控件的外部看不到的区域
         * 如果我们将起点设置在控件左端x=0的位置会使得贝塞尔曲线变得生硬
         * 至于为什么刚才我已经说了
         * 所以我们稍微让起点往“外”走点
         */
        mPath.moveTo(-1 / 4F * vWidth, waveY);

        /*
         * 以二阶曲线的方式通过控制点连接位于控件右边的终点
         * 终点的位置也是在控件外部
         * 我们只需不断让ctrX的大小变化即可实现“浪”的效果
         */
        mPath.quadTo(ctrX, ctrY, vWidth + 1 / 4F * vWidth, waveY);

        // 围绕控件闭合曲线
        mPath.lineTo(vWidth + 1 / 4F * vWidth, vHeight);
        mPath.lineTo(-1 / 4F * vWidth, vHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

        /*
         * 当控制点的x坐标大于或等于终点x坐标时更改标识值
         */
        if (ctrX >= vWidth + 1 / 4F * vWidth) {
            isInc = false;
        }
        /*
         * 当控制点的x坐标小于或等于起点x坐标时更改标识值
         */
        else if (ctrX <= -1 / 4F * vWidth) {
            isInc = true;
        }

        // 根据标识值判断当前的控制点x坐标是该加还是减
        ctrX = isInc ? ctrX + 20 : ctrX - 20;

        /*
         * 让“水”不断减少
         */
        if (ctrY <= vHeight) {
            ctrY += 2;
            waveY += 2;
        }

        mPath.reset();

        // 重绘
        invalidate();
    }
}