package studentsdemo.lieeber.com.lbview.views;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lieeber on 2017/6/26.
 */

public class SearchView extends View {
    private final Paint mPaint;
    private int viewWidth;
    private int viewHeight;
    private final Path searchPath;
    private final Path circlePath;
    private final PathMeasure pathMeasure;
    private float mAnimatorValue = 0;


    private final int SEARCH = 0;
    private final int ENDING = 1;

    private final int END = 2;


    private int state = 0;
    private int count = 0;
    private final ValueAnimator mStartingAnimator;

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(15);


        searchPath = new Path();
        circlePath = new Path();

        RectF circleRect = new RectF(-100, -100, 100, 100);
        circlePath.addArc(circleRect, 45, 359.9f);

        RectF searchRect = new RectF(-50, -50, 50, 50);
        searchPath.addArc(searchRect, 45, -359.9f);

        float[] pos = new float[2];
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(circlePath, false);
        pathMeasure.getPosTan(0, pos, null);
        searchPath.lineTo(pos[0], pos[1]);

        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(2000);
        mStartingAnimator.setRepeatCount(10000);
        mStartingAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mStartingAnimator.addListener(new AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {

            }

            @Override public void onAnimationEnd(Animator animation) {
                Log.e("================", "onAnimationEnd");

            }

            @Override public void onAnimationCancel(Animator animation) {

            }

            @Override public void onAnimationRepeat(Animator animation) {
                if (state == SEARCH) {
                    count++;
                    if (count > 1) {
                        state = ENDING;
                        return;
                    }
                }
                if (state == ENDING) {
                    state = END;
                }
            }
        });
        mStartingAnimator.start();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#0082D7"));
        canvas.translate(viewWidth / 2, viewHeight / 2);

//        canvas.drawPath(circlePath,mPaint);
//        canvas.drawPath(searchPath, mPaint);

        Path dst = new Path();
        switch (state) {
            case SEARCH:
                dst.reset();
                pathMeasure.setPath(circlePath, false);
                float stop = pathMeasure.getLength() * mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * pathMeasure.getLength() * 0.5));
                pathMeasure.getSegment(start, stop, dst, true);
                break;
            case ENDING:
                dst.reset();
                pathMeasure.setPath(searchPath, false);
                start = pathMeasure.getLength() - pathMeasure.getLength() * mAnimatorValue;
                pathMeasure.getSegment(start, pathMeasure.getLength(), dst, true);
                break;
            case END:
                dst.reset();
                pathMeasure.setPath(searchPath, false);
                pathMeasure.getSegment(0, pathMeasure.getLength(), dst, true);
                mStartingAnimator.cancel();
                break;
        }

        canvas.drawPath(dst, mPaint);
    }
}
