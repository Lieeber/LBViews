package studentsdemo.lieeber.com.zhezhao

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by lieeber on 2017/6/21.
 */

class LBPolyLineView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var viewSize: Float = 0f
    var linePaint: Paint
    var itemWith: Float = 0f
    var mPath: Path
    var meshPaint: Paint

    private var xyPaint: Paint

    init {
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 6f
        linePaint.color = Color.WHITE
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.strokeJoin = Paint.Join.ROUND


        meshPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        meshPaint.style = Paint.Style.STROKE
        meshPaint.strokeWidth = 3f
        meshPaint.color = 0xffeeeee
        meshPaint.strokeCap = Paint.Cap.ROUND
        meshPaint.strokeJoin = Paint.Join.ROUND


        xyPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        xyPaint.style = Paint.Style.STROKE
        xyPaint.strokeWidth = 8f
        xyPaint.color = Color.WHITE


        mPath = Path()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec))

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewSize = w.toFloat()
        itemWith = viewSize / 10.0f

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(0f, viewSize)
        canvas.scale(1F, -1F)
        canvas.drawARGB(255, 155, 77, 0)

        for (item in 1..10) {
            canvas.drawLine(0f, itemWith * item, viewSize, itemWith * item, meshPaint)
            canvas.drawLine(itemWith * item, 0f, itemWith * item, viewSize, meshPaint)
        }

        canvas.drawLine(0f, 0f, viewSize, 0f, xyPaint)
        canvas.drawLine(0f, 0f, 0f, viewSize, xyPaint)

        val onePoint = PointF(itemWith * 1f, itemWith * 0.5f)
        val twoPoint = PointF(itemWith * 1.5f, itemWith * 3f)
        val threePoint = PointF(itemWith * 3.5f, itemWith * 4f)
        val fourPoint = PointF(itemWith * 5f, itemWith * 3f)
        val fivePoint = PointF(itemWith * 7f, itemWith * 4f)
        val sixPoint = PointF(itemWith * 7.5f, itemWith * 8f)
        val sevenPoint = PointF(itemWith * 10f, itemWith * 5f)






        PointF()
        mPath.lineTo(onePoint.x,onePoint.y)
        mPath.lineTo(twoPoint.x,twoPoint.y)
        mPath.lineTo(threePoint.x,threePoint.y)
        mPath.lineTo(fourPoint.x,fourPoint.y)
        mPath.lineTo(fivePoint.x,fivePoint.y)
        mPath.lineTo(sixPoint.x,sixPoint.y)
        mPath.lineTo(sevenPoint.x,sevenPoint.y)
        linePaint.pathEffect = CornerPathEffect(200f)


        canvas.drawPath(mPath, linePaint)
        mPath.reset()

    }
}
