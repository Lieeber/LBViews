package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/21.
 */

public class    BitmapMeshView extends View {
    private static final int WIDTH = 19;// 横向分割成的网格数量
    private static final int HEIGHT = 19;// 纵向分割成的网格数量
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);// 横纵向网格交织产生的点数量

    private Bitmap mBitmap;// 位图资源

    private float[] verts;// 交点的坐标数组

    public BitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取位图资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, true);

        // 实例化数组
        verts = new float[COUNT * 2];

        /*
         * 生成各个交点坐标
         */
        int index = 0;
        float multiple = mBitmap.getWidth();
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = mBitmap.getHeight() * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = mBitmap.getWidth() * x / WIDTH + ((HEIGHT - y) * 1.0F / HEIGHT * multiple);
                setXY(fx, fy, index);
                index += 1;
            }
        }
    }

    /**
     * 将计算后的交点坐标存入数组
     *
     * @param fx
     *            x坐标
     * @param fy
     *            y坐标
     * @param index
     *            标识值
     */
    private void setXY(float fx, float fy, int index) {
        verts[index * 2 + 0] = fx;
        verts[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制网格位图
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
    }
}
