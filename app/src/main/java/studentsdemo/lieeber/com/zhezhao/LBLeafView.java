package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/22.
 */

public class LBLeafView extends View {

    private TextPaint textPaint;
    private Bitmap leafKuangBitmap;
    private int viewSize;
    private Bitmap fenShanBitmap;

    private int rotateDegree = 0;
    private Handler mHandler;
    private Paint kuangBgPaint;
    private Paint progressPaint;

    private int progress = 0;
    private Paint blackPaint;

    private int sodu = 10;


    public LBLeafView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(0xffeea830);
        textPaint.setTextSize(70);
        textPaint.setTextAlign(Align.CENTER);


        kuangBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        kuangBgPaint.setColor(0xffffe19c);

        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Style.STROKE);
        blackPaint.setStrokeWidth(5);




        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setColor(0xffffa830);

        leafKuangBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_kuang);
        fenShanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fengshan);


        mHandler = new Handler() {
            @Override public void handleMessage(Message msg) {
                super.handleMessage(msg);
                sodu = (int) (Math.random() * 20 + 5);
                invalidate();
            }
        };
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewSize = w;
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffffc654);
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawText("loading...", 0, -200, textPaint);

        canvas.drawRoundRect(new RectF(-leafKuangBitmap.getWidth() / 2, -leafKuangBitmap.getHeight() / 2, leafKuangBitmap.getWidth() / 2, leafKuangBitmap.getHeight() / 2), leafKuangBitmap.getHeight() / 2, leafKuangBitmap.getHeight() / 2, kuangBgPaint);



        canvas.save();
        canvas.translate(-leafKuangBitmap.getWidth() / 2, 0);
        Path path = new Path();
        path.addArc(new RectF(0, -leafKuangBitmap.getHeight() / 2, leafKuangBitmap.getHeight(), leafKuangBitmap.getHeight() / 2), 90f, 180f);
        path.addRect(new RectF(leafKuangBitmap.getHeight() / 2,-leafKuangBitmap.getHeight() / 2,leafKuangBitmap.getWidth()-leafKuangBitmap.getHeight() / 2,leafKuangBitmap.getHeight()/2), Direction.CCW);
//        canvas.drawPath(path, blackPaint);
        canvas.clipPath(path);
        canvas.drawRect(new RectF(0, -leafKuangBitmap.getHeight() / 2, progress, leafKuangBitmap.getHeight() / 2), progressPaint);
        canvas.restore();



        canvas.drawBitmap(leafKuangBitmap, -leafKuangBitmap.getWidth() / 2, -leafKuangBitmap.getHeight() / 2, new Paint());


        canvas.save();
        int fanshanX = leafKuangBitmap.getWidth() / 2 - fenShanBitmap.getWidth() - (leafKuangBitmap.getHeight() - fenShanBitmap.getWidth()) / 2;
        int fanshanY = -fenShanBitmap.getHeight() / 2;
        canvas.rotate(-rotateDegree, fanshanX + fenShanBitmap.getWidth() / 2, 0);
        rotateDegree += 8;
        progress += 2;
        if (rotateDegree == 360) {
            rotateDegree = 0;
        }

        if (progress >= leafKuangBitmap.getWidth()) {
            canvas.drawBitmap(fenShanBitmap, fanshanX, fanshanY, new Paint());
            return;
        } else {
            canvas.drawBitmap(fenShanBitmap, fanshanX, fanshanY, new Paint());
            mHandler.sendEmptyMessageDelayed(0, 10);
        }
        canvas.restore();

    }
}
