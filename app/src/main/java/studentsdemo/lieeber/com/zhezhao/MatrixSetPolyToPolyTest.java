package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/27.
 */

public class MatrixSetPolyToPolyTest extends View {

    private  Matrix mPolyMatrix;
    private Bitmap bitmap;

    public MatrixSetPolyToPolyTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        bitmap = Bitmap.createScaledBitmap(bitmap, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().widthPixels, true);


        mPolyMatrix = new Matrix();

        float[] src = {0, 0, bitmap.getWidth(), 0, bitmap.getWidth(), bitmap.getHeight(), 0, bitmap.getHeight()};

        float[] dst = {0, 0, bitmap.getWidth(), 400, bitmap.getWidth(), bitmap.getHeight() - 200, 0, bitmap.getHeight()};
        mPolyMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//        mPolyMatrix.postScale(0.26f, 0.26f);
//        mPolyMatrix.postTranslate(0,200);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, mPolyMatrix, null);
    }
}
