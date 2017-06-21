package studentsdemo.lieeber.com.zhezhao;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lieeber on 2017/6/20.
 */
public class MultiCricleView extends View {
    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
            SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比
            LINE_LENGTH = 3F / 32F, // 线段长度占比
            CRICLE_LARGER_RADIU = 3F / 32F,// 大圆半径
            CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径
            ARC_RADIU = 1F / 8F,// 弧半径
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

    private Paint strokePaint, textPaint;// 描边画笔和文字画笔

    private int size;// 控件边长

    private float strokeWidth;// 描边宽度
    private float ccX, ccY;// 中心圆圆心坐标
    private float largeCricleRadiu, smallCricleRadiu;// 大圆半径和小圆半径
    private float lineLength;// 线段长度
    private float space;// 大圆小圆线段两端间隔
    private float textOffsetY;// 文本的Y轴偏移值

    public MultiCricleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化画笔
        initPaint(context);
    }

    /**
     * 初始化画笔
     *
     * @param context Fuck
     */
    private void initPaint(Context context) {
        /*
         * 初始化描边画笔
         */
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);

        /*
         * 初始化文字画笔
         */
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textOffsetY = (textPaint.descent() + textPaint.ascent()) / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 强制长宽一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取控件边长
        size = w;

        // 参数计算
        calculation();
    }

    /*
     * 参数计算
     */
    private void calculation() {
        // 计算描边宽度
        strokeWidth = STROKE_WIDTH * size;

        // 计算大圆半径
        largeCricleRadiu = size * CRICLE_LARGER_RADIU;

        // 计算小圆半径
        smallCricleRadiu = size * CRICLE_SMALL_RADIU;

        // 计算线段长度
        lineLength = size * LINE_LENGTH;

        // 计算大圆小圆线段两端间隔
        space = size * SPACE;

        // 计算中心圆圆心坐标
        ccX = size / 2;
        ccY = size / 2 + size * CRICLE_LARGER_RADIU;

        // 设置参数
        setPara();
    }

    /**
     * 设置参数
     */
    private void setPara() {
        // 设置描边宽度
        strokePaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制背景
        canvas.drawColor(0xFFF29B76);

        // 绘制中心圆
        canvas.drawCircle(ccX, ccY, largeCricleRadiu, strokePaint);
        canvas.drawText("AigeStudio", ccX, ccY - textOffsetY, textPaint);

        // 绘制左上方图形
        drawTopLeft(canvas);

        // 绘制右上方图形
        drawTopRight(canvas);

        // 绘制左下方图形
        drawBottomLeft(canvas);

        // 绘制下方图形
        drawBottom(canvas);

        // 绘制右下方图形
        drawBottomRight(canvas);
    }

    /**
     * 绘制左上方图形
     *
     * @param canvas
     */
    private void drawTopLeft(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-30);

        // 依次画：线-圈-线-圈
        canvas.drawLine(0, -largeCricleRadiu, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCricleRadiu, strokePaint);
        canvas.drawText("Apple", 0, -lineLength * 3 - textOffsetY, textPaint);

        canvas.drawLine(0, -largeCricleRadiu * 4, 0, -lineLength * 5, strokePaint);
        canvas.drawCircle(0, -lineLength * 6, largeCricleRadiu, strokePaint);
        canvas.drawText("Orange", 0, -lineLength * 6 - textOffsetY, textPaint);

        // 释放画布
        canvas.restore();
    }

    /**
     * 绘制右上方图形
     *
     * @param canvas
     */
    private void drawTopRight(Canvas canvas) {
        float cricleY = -lineLength * 3;

        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(30);

        // 依次画：线-圈
        canvas.drawLine(0, -largeCricleRadiu, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, cricleY, largeCricleRadiu, strokePaint);
        canvas.drawText("Tropical", 0, cricleY - textOffsetY, textPaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottomLeft(Canvas canvas) {
        float lineYS = -largeCricleRadiu - space, lineYE = -lineLength * 2 - space, cricleY = -lineLength * 2 - smallCricleRadiu - space * 2;

        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-100);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, lineYS, 0, lineYE, strokePaint);
        canvas.drawCircle(0, cricleY, smallCricleRadiu, strokePaint);
        canvas.drawText("Banana", 0, cricleY - textOffsetY, textPaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottom(Canvas canvas) {
        float lineYS = -largeCricleRadiu - space, lineYE = -lineLength * 2 - space, cricleY = -lineLength * 2 - smallCricleRadiu - space * 2;

        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(180);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, lineYS, 0, lineYE, strokePaint);
        canvas.drawCircle(0, cricleY, smallCricleRadiu, strokePaint);
        canvas.drawText("Cucumber", 0, cricleY - textOffsetY, textPaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottomRight(Canvas canvas) {
        float lineYS = -largeCricleRadiu - space, lineYE = -lineLength * 2 - space, cricleY = -lineLength * 2 - smallCricleRadiu - space * 2;

        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(100);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, lineYS, 0, lineYE, strokePaint);
        canvas.drawCircle(0, cricleY, smallCricleRadiu, strokePaint);
        canvas.drawText("Vibrators", 0, cricleY - textOffsetY, textPaint);

        // 释放画布
        canvas.restore();
    }
}