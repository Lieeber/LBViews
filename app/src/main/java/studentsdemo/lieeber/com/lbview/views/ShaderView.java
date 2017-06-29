package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import studentsdemo.lieeber.com.lbview.R;

/**
 * Created by lieeber on 2017/6/16.
 */

public class ShaderView extends View{

    private static final int RECT_SIZE = 400;// 矩形尺寸的一半

    private Paint mPaint;// 画笔

    private int left, top, right, bottom;// 矩形坐上右下坐标

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取屏幕中点坐标
        int screenX = getResources().getDisplayMetrics().widthPixels / 2;
        int screenY = getResources().getDisplayMetrics().heightPixels / 2;
        // 计算矩形左上右下坐标值
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;

        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        bitmap = Bitmap.createScaledBitmap(bitmap, getResources().getDisplayMetrics().widthPixels/4, getResources().getDisplayMetrics().heightPixels/4, true);

        // 设置着色器
        mPaint.setShader(new BitmapShader(bitmap, TileMode.REPEAT, TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制矩形
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
