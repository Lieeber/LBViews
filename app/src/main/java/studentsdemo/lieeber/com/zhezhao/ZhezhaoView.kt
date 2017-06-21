package studentsdemo.lieeber.com.zhezhao

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Created by lieeber on 2017/6/14.
 */

class ZhezhaoView : View {

    //    private var canvasBitmap: Bitmap? = null
//    private var mCanvas: Canvas? = null
    private var mPaint: Paint? = null

    var bitmap: Bitmap? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
//        val size = Point()
//        size.x = resources.displayMetrics.widthPixels
//        size.y = resources.displayMetrics.heightPixels
//        canvasBitmap = Bitmap.createBitmap(size.x, size.y, Config.ARGB_8888)
//        mCanvas = Canvas(canvasBitmap!!)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        mPaint!!.color = Color.WHITE
//        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)


        bitmap = BitmapFactory.decodeResource(resources, R.drawable.timg)
//        val colorMatrix = ColorMatrix(floatArrayOf(
//              0f,  0f,  1f, 0f, 0f,
//              1f,  0f,  0f, 0f, 0f,
//              0f,  1f,  0f, 0f, 0f,
//              0f,  0f,  0f, 1f, 0f))
//        mPaint?.colorFilter = ColorMatrixColorFilter(colorMatrix)
//        mPaint?.colorFilter = LightingColorFilter(0xFFFF00FF.toInt(), 0x00000000)
//        mPaint?.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.OVERLAY)
    }


    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        val rectF = RectF(10f, 10f, 100f, 100f)
//        mCanvas!!.drawColor(Color.BLACK)
//        mCanvas!!.drawRoundRect(rectF, 10f, 10f, mPaint!!)
//
        // 先绘制一层带透明度的颜色
        canvas.drawColor(Color.parseColor("#123456"))

        // 设置混合模式
        mPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)

//        val src = Rect(0, 0, bitmap!!.width, bitmap!!.height)
//// 指定图片在屏幕上显示的区域
//        val dst = Rect(0, 0, resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
//        canvas.drawBitmap(bitmap, src, dst, mPaint)

        // 缩放背景底图Bitmap至屏幕大小
        bitmap = Bitmap.createScaledBitmap(bitmap, resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels, true)
        canvas.drawBitmap(bitmap, Matrix(), mPaint)
    }
}
