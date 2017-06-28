package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class MatrixSetRectToRectTest extends View {

    private Bitmap bitmap;
    private final Matrix mMatrix;
    private final Paint paint;

    public MatrixSetRectToRectTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        bitmap = Bitmap.createScaledBitmap(bitmap, getResources().getDisplayMetrics().widthPixels, (int) (getResources().getDisplayMetrics().widthPixels * 0.7f), true);

        mMatrix = new Matrix();

        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setColor(Color.RED);

    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dst = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        mMatrix.setRectToRect(src, dst, ScaleToFit.CENTER);

        canvas.drawBitmap(bitmap, mMatrix, null);
//        canvas.setMatrix(mMatrix);
//        canvas.drawRect(src,paint);

    }
}
