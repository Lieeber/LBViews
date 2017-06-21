package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lieeber on 2017/6/20.
 */

public class MyImageView extends AppCompatImageView {
    private static final int MODE_NONE = 0x00123;// 默认的触摸模式
    private static final int MODE_DRAG = 0x00321;// 拖拽模式
    private static final int MODE_ZOOM = 0x00132;// 缩放or旋转模式
    private final PointF mid;

    private int mode;// 当前的触摸模式
    private final Matrix mMatrix;
    private float downX;
    private float downY;
    private float originSpace;
    private float originRotate;

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cc);
        bitmap = Bitmap.createScaledBitmap(bitmap, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, true);
        setImageBitmap(bitmap);

        mid = new PointF();
        mMatrix = new Matrix();
//        mMatrix.postTranslate(100, 200);
        mMatrix.postScale(0.5f, 0.5f);
        setImageMatrix(mMatrix);

    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                mode = MODE_DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                originSpace = calSpacing(event);
                if (originSpace > 10F) {
                    calMidPoint(mid, event);
                    mode = MODE_ZOOM;
                }
                originRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_DRAG) {
                    float currentX = event.getX();
                    float currentY = event.getY();
                    float dx = currentX - downX;
                    float dy = currentY - downY;
                    mMatrix.postTranslate(dx, dy);
                    setImageMatrix(mMatrix);
                    downX = currentX;
                    downY = currentY;
                } else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {
                    float currentSpace = calSpacing(event);
                    if (currentSpace > 10F) {
                        float scale = currentSpace / originSpace;
                        mMatrix.postScale(scale, scale, mid.x, mid.y);
                        originSpace = currentSpace;
                    }
                    float currentRotate = calRotation(event);
                    float dRotate = currentRotate - originRotate;
                    mMatrix.postRotate(dRotate, mid.x, mid.y);
                    setImageMatrix(mMatrix);
                    originRotate = currentRotate;
                }
                break;
            case MotionEvent.ACTION_UP:
                Camera camera = new Camera();
                camera.getMatrix(mMatrix);
                break;

        }
        return true;
    }

    private void calMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));
        double radius = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radius);
    }
}
