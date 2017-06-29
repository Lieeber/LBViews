package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import studentsdemo.lieeber.com.lbview.R;

/**
 * Created by lieeber on 2017/6/26.
 */

public class JiantouView extends View {

    private final Paint mPaint;
    private Bitmap mBitmap;
    private final Path mPath;

    private float currentValue = 0;
    private final float[] pos;
    private final float[] tan;
    private final Matrix mMatrix;

    public JiantouView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(8);


        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jiantou);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, 50, 50, true);

        mPath = new Path();

        pos = new float[2];
        tan = new float[2];

        mMatrix = new Matrix();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
//        canvas.drawCircle(0, 0, 200, mPaint);
        mPath.addCircle(0, 0, 200, Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(mPath, false);

        currentValue += 0.01;

        if (currentValue >= 1) {
            currentValue = 0;
        }

        pathMeasure.getPosTan(currentValue * pathMeasure.getLength(), pos, tan);
        mMatrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0f / Math.PI);
        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);

        canvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);

        invalidate();
    }
}
