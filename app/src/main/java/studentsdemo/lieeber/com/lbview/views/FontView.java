package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lieeber on 2017/6/16.
 */

public class FontView extends View {
    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint mPaint;// 画笔
    private FontMetrics mFontMetrics;// 文本测量对象
    private Paint linePaint;

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        setLayerType(LAYER_TYPE_HARDWARE,null);
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);
        mPaint.setTypeface(Typeface.SERIF);
        mFontMetrics = mPaint.getFontMetrics();

        mPaint.setMaskFilter(new BlurMaskFilter(20, Blur.OUTER));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.RED);

        Log.d("Aige", "ascent：" + mFontMetrics.ascent);
        Log.d("Aige", "top：" + mFontMetrics.top);
        Log.d("Aige", "leading：" + mFontMetrics.leading);
        Log.d("Aige", "descent：" + mFontMetrics.descent);
        Log.d("Aige", "bottom：" + mFontMetrics.bottom);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(TEXT, canvas.getWidth()/2 -mPaint.measureText(TEXT)/2,  canvas.getHeight() / 2 - (mPaint.ascent() + mPaint.descent())/2, mPaint);
//        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, linePaint);
    }
}
