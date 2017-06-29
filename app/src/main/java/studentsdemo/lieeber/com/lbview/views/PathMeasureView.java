package studentsdemo.lieeber.com.lbview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lieeber on 2017/6/23.
 */

public class PathMeasureView extends View {
    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(5);
        Path path = new Path();
        path.addRect(-100, -100, 100, 100, Direction.CCW);
        path.addRect(-200, -200, 200, 200, Direction.CW);
        canvas.drawPath(path, paint);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length1 = pathMeasure.getLength();
        pathMeasure.nextContour();
        float length2 = pathMeasure.getLength();
        Log.e("========", length1 + "");
        Log.e("========", length2 + "");

    }
}
