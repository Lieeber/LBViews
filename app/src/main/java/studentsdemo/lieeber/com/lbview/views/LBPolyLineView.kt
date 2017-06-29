package studentsdemo.lieeber.com.lbview.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by lieeber on 2017/6/21.
 */

class LBPolyLineView(context: android.content.Context, attrs: android.util.AttributeSet?) : android.view.View(context, attrs) {
    private var viewSize: Float = 0f
    var linePaint: android.graphics.Paint
    var itemWith: Float = 0f
    var mPath: android.graphics.Path
    var meshPaint: android.graphics.Paint

    private var xyPaint: android.graphics.Paint

    init {
        linePaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        linePaint.style = android.graphics.Paint.Style.STROKE
        linePaint.strokeWidth = 6f
        linePaint.color = android.graphics.Color.WHITE
        linePaint.strokeCap = android.graphics.Paint.Cap.ROUND
        linePaint.strokeJoin = android.graphics.Paint.Join.ROUND


        meshPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        meshPaint.style = android.graphics.Paint.Style.STROKE
        meshPaint.strokeWidth = 3f
        meshPaint.color = 0xffeeeee
        meshPaint.strokeCap = android.graphics.Paint.Cap.ROUND
        meshPaint.strokeJoin = android.graphics.Paint.Join.ROUND


        xyPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        xyPaint.style = android.graphics.Paint.Style.STROKE
        xyPaint.strokeWidth = 8f
        xyPaint.color = android.graphics.Color.WHITE


        mPath = android.graphics.Path()

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


    override fun onDraw(canvas: android.graphics.Canvas) {
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

        val onePoint = android.graphics.PointF(itemWith * 1f, itemWith * 0.5f)
        val twoPoint = android.graphics.PointF(itemWith * 1.5f, itemWith * 3f)
        val threePoint = android.graphics.PointF(itemWith * 3.5f, itemWith * 4f)
        val fourPoint = android.graphics.PointF(itemWith * 5f, itemWith * 3f)
        val fivePoint = android.graphics.PointF(itemWith * 7f, itemWith * 4f)
        val sixPoint = android.graphics.PointF(itemWith * 7.5f, itemWith * 8f)
        val sevenPoint = android.graphics.PointF(itemWith * 10f, itemWith * 5f)






        android.graphics.PointF()
        mPath.lineTo(onePoint.x,onePoint.y)
        mPath.lineTo(twoPoint.x,twoPoint.y)
        mPath.lineTo(threePoint.x,threePoint.y)
        mPath.lineTo(fourPoint.x,fourPoint.y)
        mPath.lineTo(fivePoint.x,fivePoint.y)
        mPath.lineTo(sixPoint.x,sixPoint.y)
        mPath.lineTo(sevenPoint.x,sevenPoint.y)
        linePaint.pathEffect = android.graphics.CornerPathEffect(200f)


        canvas.drawPath(mPath, linePaint)
        mPath.reset()

    }
}
