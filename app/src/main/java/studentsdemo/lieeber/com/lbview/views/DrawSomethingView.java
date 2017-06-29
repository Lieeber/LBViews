package studentsdemo.lieeber.com.lbview.views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import studentsdemo.lieeber.com.lbview.bean.PathBean;

import static android.content.ContentValues.TAG;

/**
 * Created by lieeber on 2017/6/28.
 * 自定义画板，可以更换画笔颜色，粗细，实现了前进，后退，保存到本地等功能。
 */
public class DrawSomethingView extends View {
    private final Paint linePaint;
    private final Paint paintPaint;
    private final Path fillPath;
    private final Paint eraserPaint;
    private float downX;
    private float downY;
    private Path linePath;

    private int resetCount = 0;


    private final int DRAW_LINE = 0;
    private final int DRAW_FILL = 1;
    private final int DRAW_PAINT = 2;
    private final int DRAW_ERASER = 3;

    private int drawStyle = 0;


    private int lineColor = Color.BLACK;
    private int paintColor = Color.BLUE;
    private int lineWidth = 20;
    private float paintWidthProgress = 50;
    private float lineWidthProgress = 50;
    private int paintWidth = 70;

    private float eraserWidthProgress = 50;
    private int eraserWidth = 100;


    public float getEraserWidthProgress() {
        return eraserWidthProgress;
    }

    public void setEraserWidthProgress(float eraserWidthProgress) {
        this.eraserWidthProgress = eraserWidthProgress;
    }

    public int getEraserWidth() {
        return eraserWidth;
    }

    public void setEraserWidth() {
        eraserPaint.setStrokeWidth(eraserWidth * eraserWidthProgress / 100);
    }

    public float getPaintWidthProgress() {
        return paintWidthProgress;
    }

    public void setPaintWidthProgress(float paintWidthProgress) {
        this.paintWidthProgress = paintWidthProgress;
    }

    public float getLineWidthProgress() {
        return lineWidthProgress;
    }

    public void setLineWidthProgress(float lineWidthProgress) {
        this.lineWidthProgress = lineWidthProgress;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        paintPaint.setColor(paintColor);
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth() {
        linePaint.setStrokeWidth(lineWidth * lineWidthProgress / 100);
    }

    public int getPaintWidth() {
        return paintWidth;
    }

    public void setPaintWidth() {
        paintPaint.setStrokeWidth(paintWidth * paintWidthProgress / 100);
    }

    private ArrayList<PathBean> pathlist = new ArrayList<>();
    private ArrayList<PathBean> deletePathList = new ArrayList<>();


    private final Canvas mCanvas;
    private Bitmap mBitmap;
    private int viewWidth;
    private int viewHeight;

    public DrawSomethingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Style.STROKE);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth * lineWidthProgress / 100);
        linePaint.setPathEffect(new CornerPathEffect(100f));
        linePaint.setStrokeCap(Cap.ROUND);
        linePaint.setStrokeJoin(Join.ROUND);


        paintPaint = new Paint();
        paintPaint.setStyle(Style.STROKE);
        paintPaint.setColor(paintColor);
        paintPaint.setStrokeWidth(paintWidth * paintWidthProgress / 100);
        paintPaint.setStrokeCap(Cap.ROUND);
        paintPaint.setPathEffect(new CornerPathEffect(100f));
        paintPaint.setStrokeJoin(Join.ROUND);


        eraserPaint = new Paint();
        eraserPaint.setStyle(Style.STROKE);
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        eraserPaint.setColor(Color.TRANSPARENT);
        eraserPaint.setStrokeWidth(eraserWidth * eraserWidthProgress / 100);
        eraserPaint.setStrokeCap(Cap.ROUND);
        eraserPaint.setPathEffect(new CornerPathEffect(100f));
        eraserPaint.setStrokeJoin(Join.ROUND);

        linePath = new Path();
        fillPath = new Path();


        mCanvas = new Canvas();


    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        blankBitmap();
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                linePath = new Path();
                linePath.moveTo(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                downY = event.getY();
                switch (drawStyle) {
                    case DRAW_LINE:
                        linePath.lineTo(downX, downY);
                        mCanvas.drawPath(linePath, linePaint);
                        break;
                    case DRAW_PAINT:
                        linePath.lineTo(downX, downY);
                        mCanvas.drawPath(linePath, paintPaint);
                        break;
                    case DRAW_ERASER:
                        linePath.lineTo(downX, downY);
                        mCanvas.drawPath(linePath, eraserPaint);
                        break;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                switch (drawStyle) {
                    case DRAW_LINE:
                        pathlist.add(new PathBean(linePath, linePaint, lineWidthProgress * lineWidth / 100, lineColor));
                        break;
                    case DRAW_PAINT:
                        pathlist.add(new PathBean(linePath, paintPaint, paintWidthProgress * paintWidth / 100, paintColor));
                        break;
                    case DRAW_ERASER:
                        pathlist.add(new PathBean(linePath, eraserPaint, eraserWidthProgress * eraserWidth / 100, Color.TRANSPARENT));
                        break;
                }
                deletePathList.clear();

                break;
        }

        return true;
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, new Matrix(), null);
    }

    private void blankBitmap() {
        mBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    public void clear() {
        pathlist.clear();
        deletePathList.clear();
        blankBitmap();
        invalidate();
    }


    public void reset() {
        blankBitmap();
        if (pathlist.size() > 0) {
            PathBean pathBean = pathlist.get(pathlist.size() - 1);
            pathlist.remove(pathBean);
            deletePathList.add(pathBean);
        }
        for (int i = 0; i < pathlist.size(); i++) {
            pathlist.get(i).paint.setStrokeWidth(pathlist.get(i).width);
            pathlist.get(i).paint.setColor(pathlist.get(i).color);
            mCanvas.drawPath(pathlist.get(i).path, pathlist.get(i).paint);
        }
        invalidate();
    }

    public void forward() {
        if (deletePathList.size() > 0) {
            PathBean pathBean = deletePathList.get(deletePathList.size() - 1);
            pathBean.paint.setStrokeWidth(pathBean.width);
            pathBean.paint.setColor(pathBean.color);
            mCanvas.drawPath(pathBean.path, pathBean.paint);
            deletePathList.remove(deletePathList.size() - 1);
            pathlist.add(pathBean);
        }
        invalidate();
    }

    public void setDrawLine() {
        drawStyle = DRAW_LINE;
    }


    public void setDrawPaint() {
        drawStyle = DRAW_PAINT;
    }

    public void setEraser() {
        drawStyle = DRAW_ERASER;
    }

    public int getlineColor() {
        return lineColor;

    }

    public void save() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            //拥有读写文件权限
            Log.i(TAG,"拥有读写文件权限");
            //获得系统当前时间，并以该时间作为文件名
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            String str = formatter.format(curDate) + "paint.png";
            File file = new File(dir + "/"+str);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBitmap.compress(CompressFormat.PNG, 100, fos);

//            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
//            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
//            getContext().sendBroadcast(intent);
            getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_LONG).show();
        }else{
            Log.i(TAG,"没有读写权限");

            Toast.makeText(getContext(), "没有读写权限", Toast.LENGTH_LONG).show();
            //没有读写权限
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        140);
            }else{
//                showPermissionDialog();
            }
        }
    }
}
