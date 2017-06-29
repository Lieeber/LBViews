package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lieeber on 2017/6/20.
 */

public class LBMultiCircleView extends View {
    private int size;
    private float cR;
    private float bcR;
    private Paint mPaint;
    private float ccY;
    private float ccX;
    private TextPaint textPaint;
    private float textOffset;
    private Paint arcPiant;

    public LBMultiCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0xffeeeeee);
        mPaint.setStrokeCap(Cap.ROUND);


        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Align.CENTER);
        textOffset = (textPaint.ascent() + textPaint.descent()) / 2;

        arcPiant = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        arcPiant.setStyle(Style.FILL);
        arcPiant.setColor(0x99F78060);


    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = w;

        cR = size * 1.0f / 11;
        bcR = cR * 1.3f;
        Log.e("circleRadius=======", cR + "");
        ccY = cR * 6.5f;
        ccX = size / 2.0f;

    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFF29B76);
        canvas.drawCircle(ccX, ccY, cR, mPaint);
        canvas.drawText("Lieeber", ccX, ccY - textOffset, textPaint);


        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(-30);
        canvas.drawLine(0, -cR, 0, -cR * 2, mPaint);
        canvas.drawCircle(0, -cR * 3, cR, mPaint);

        canvas.save();
        canvas.rotate(30, 0, -cR * 3);
        canvas.drawText("LiBing", 0, -cR * 3 - textOffset, textPaint);
        canvas.restore();

        canvas.translate(0, -cR * 3);
        canvas.drawLine(0, -cR, 0, -cR * 2, mPaint);
        canvas.drawCircle(0, -cR * 3, cR, mPaint);

        canvas.rotate(30, 0, -cR * 3);
        canvas.drawText("李兵", 0, -cR * 3 - textOffset, textPaint);

        canvas.restore();


        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(30);
        canvas.drawLine(0, -cR, 0, -cR * 2, mPaint);
        canvas.drawCircle(0, -cR * 3, cR, mPaint);


        canvas.rotate(-30, 0, -cR * 3);
        canvas.drawText("shijiao", 0, -cR * 3 - textOffset, textPaint);

        RectF rectF = new RectF(-bcR, -cR * 3 - bcR, bcR, -cR * 3 + bcR);
        canvas.drawArc(rectF, -150, 120, true, arcPiant);
        canvas.drawArc(rectF, -150, 120, false, mPaint);


            canvas.save();
            canvas.rotate(-60, 0, -cR * 3);
            canvas.drawText("LiBe", 0, -cR * 3 - bcR - textOffset - 40, textPaint);
            canvas.restore();

            canvas.save();
            canvas.rotate(-30, 0, -cR * 3);
            canvas.drawText("LiBe", 0, -cR * 3 - bcR - textOffset - 40, textPaint);
            canvas.restore();

            canvas.save();
            canvas.rotate(-0, 0, -cR * 3);
            canvas.drawText("LiBe", 0, -cR * 3 - bcR - textOffset - 40, textPaint);
            canvas.restore();


            canvas.save();
            canvas.rotate(30, 0, -cR * 3);
            canvas.drawText("LiBe", 0, -cR * 3 - bcR - textOffset - 40, textPaint);
            canvas.restore();


            canvas.save();
            canvas.rotate(60, 0, -cR * 3);
            canvas.drawText("LiBe", 0, -cR * 3 - bcR - textOffset - 40, textPaint);
            canvas.restore();


        canvas.restore();


        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(-20);
        canvas.drawLine(-cR, 0, -cR * 2, 0, mPaint);
        canvas.drawCircle(-cR * 3, 0, cR, mPaint);

        canvas.rotate(20, -cR * 3, 0);
        canvas.drawText("lishaowei", -cR * 3, 0 - textOffset, textPaint);

        canvas.restore();


        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.rotate(20);
        canvas.drawLine(cR, 0, cR * 2, 0, mPaint);
        canvas.drawCircle(cR * 3, 0, cR, mPaint);

        canvas.rotate(-20, cR * 3, 0);
        canvas.drawText("HeHe", cR * 3, 0 - textOffset, textPaint);

        canvas.restore();


        canvas.save();
        canvas.translate(ccX, ccY);
        canvas.drawLine(0, cR, 0, cR * 2, mPaint);
        canvas.drawCircle(0, cR * 3, cR, mPaint);
        canvas.drawText("HeiHei", 0, cR * 3 - textOffset, textPaint);
        canvas.restore();


    }
}
