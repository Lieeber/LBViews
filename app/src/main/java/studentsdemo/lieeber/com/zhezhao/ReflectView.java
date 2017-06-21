package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/16.
 */

public class ReflectView extends View {
    private Bitmap mSrcBitmap, mRefBitmap;// 位图
    private Paint mPaint;// 画笔
    private PorterDuffXfermode mXfermode;// 混合模式

    private int x, y;// 位图起点坐标

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化资源
        initRes(context);
    }

    /*
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cc);
        mSrcBitmap = Bitmap.createScaledBitmap(mSrcBitmap, getResources().getDisplayMetrics().widthPixels, (int)((mSrcBitmap.getHeight() *1.0/ mSrcBitmap.getWidth()) * getResources().getDisplayMetrics().widthPixels), true);

        // 实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);
        // 生成倒影图
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        int screenW = getResources().getDisplayMetrics().widthPixels;
        int screenH = getResources().getDisplayMetrics().heightPixels;

        x = screenW / 2 - mSrcBitmap.getWidth() / 2;
        y = screenH / 2 - mSrcBitmap.getHeight() / 2;

        // ………………………………
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(0, mSrcBitmap.getHeight(), 0, mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        // ………………………………
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, 0, 0, null);

        int sc = canvas.saveLayer(0, mSrcBitmap.getHeight(),  mRefBitmap.getWidth(), mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mRefBitmap, 0,  mSrcBitmap.getHeight(), null);

        mPaint.setXfermode(mXfermode);

        canvas.drawRect(0, mSrcBitmap.getHeight(), mRefBitmap.getWidth(), mSrcBitmap.getHeight() * 2, mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}
