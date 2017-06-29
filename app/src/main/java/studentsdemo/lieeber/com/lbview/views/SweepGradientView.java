package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/18.
 */


public class SweepGradientView extends View {

    int length = 500;
    private final int screenW;
    private final int screenH;
    private final Paint mPaint;

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        mPaint.setShader(new SweepGradient(screenW/2,screenH/2, new int[]{Color.RED, Color.GREEN},null));
        mPaint.setShader(new RadialGradient(screenW/2, screenH/2,30, Color.RED, Color.GREEN, TileMode.REPEAT));

    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(screenW / 2 - length / 2, screenH / 2 - length / 2, screenW / 2 + length / 2, screenH / 2 + length / 2,mPaint);
    }
}
