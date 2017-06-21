package studentsdemo.lieeber.com.zhezhao

import android.content.Context
import android.graphics.*
import android.graphics.PorterDuff.Mode
import android.util.AttributeSet
import android.view.View

/**
 * Created by lieeber on 2017/6/14.
 */

class PorterDuffXfermodeView : View {
    private var mPaint: Paint? = null
    private var dstBmp: Bitmap? = null
    private var srcBmp: Bitmap? = null
    private var dstRect: RectF? = null
    private var srcRect: RectF? = null

    private var mXfermode: Xfermode? = null
    private val mPorterDuffMode = Mode.XOR


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        dstBmp = BitmapFactory.decodeResource(resources, R.drawable.aa)
        srcBmp = BitmapFactory.decodeResource(resources, R.drawable.bb)
        mXfermode = PorterDuffXfermode(mPorterDuffMode)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //背景色设为白色，方便比较效果
        canvas.drawColor(Color.WHITE)
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG)
        //绘制目标图
        canvas.drawBitmap(dstBmp!!, null, dstRect!!, mPaint)
        //设置混合模式
        mPaint!!.xfermode = mXfermode
        //绘制源图
        canvas.drawBitmap(srcBmp!!, null, srcRect!!, mPaint)
        //清除混合模式
        mPaint!!.xfermode = null
        //还原画布
        canvas.restoreToCount(saveCount)

        val colorMatrix = ColorMatrix(floatArrayOf(0.5f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 0f, 0.5f, 0f, 0f, 0f, 0f, 0f, 1f, 0f))
        mPaint!!.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val width = if (w <= h) w else h
        val centerX = w / 2
        val centerY = h / 2
        val quarterWidth = width / 4
        srcRect = RectF((centerX - quarterWidth).toFloat(), (centerY - quarterWidth).toFloat(), (centerX + quarterWidth).toFloat(), (centerY + quarterWidth).toFloat())
        dstRect = RectF((centerX - quarterWidth).toFloat(), (centerY - quarterWidth).toFloat(), (centerX + quarterWidth).toFloat(), (centerY + quarterWidth).toFloat())
    }
}