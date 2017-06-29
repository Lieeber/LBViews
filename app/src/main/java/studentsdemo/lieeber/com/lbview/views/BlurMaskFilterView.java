package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import studentsdemo.lieeber.com.lbview.R;

/**
 * Created by lieeber on 2017/6/16.
 */

public class BlurMaskFilterView extends View {
    private Paint shadowPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap srcBitmap, shadowBitmap;// 位图和阴影位图

    private int x, y;// 位图绘制时左上角的起点坐标

    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 记得设置模式为SOFTWARE
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(Color.DKGRAY);
        shadowPaint.setMaskFilter(new BlurMaskFilter(50, Blur.NORMAL));


    }

    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取位图
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.timg);
        srcBitmap = Bitmap.createScaledBitmap(srcBitmap, getResources().getDisplayMetrics().widthPixels-100, getResources().getDisplayMetrics().heightPixels-200,true);

        // 获取位图的Alpha通道图
        shadowBitmap = srcBitmap.extractAlpha();
        shadowBitmap = Bitmap.createScaledBitmap(shadowBitmap, getResources().getDisplayMetrics().widthPixels-100, getResources().getDisplayMetrics().heightPixels-200, true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(shadowBitmap, 50, 100, shadowPaint);
        canvas.drawBitmap(srcBitmap, 50, 100, shadowPaint);
    }
}