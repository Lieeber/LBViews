package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/21.
 */

public class LBWaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int viewWidth;
    private int viewHeight;


    private float drawY = 100;
    private float drawX = -60;

    private float quadX = 100;
    private float quadY = drawY - 300;

    private boolean quadRight = true;


    public LBWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        // 实例化画笔并设置参数
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFFA2D6AE);

        // 实例化路径对象
        mPath = new Path();
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(drawX, drawY);

        mPath.quadTo(quadX, quadY, viewWidth - drawX, drawY);

        mPath.lineTo(viewWidth, viewHeight);
        mPath.lineTo(0, viewHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

        mPath.reset();

        drawY += 1;
        quadY += 1;

        if (quadX >= viewWidth) {
            quadRight = false;
        }
        if (quadX <= 0) {
            quadRight = true;
        }
        if (quadRight) {
            quadX += 30;
        } else {
            quadX -= 30;
        }

        invalidate();

    }
}
