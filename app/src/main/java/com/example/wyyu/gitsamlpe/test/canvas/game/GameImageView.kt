package com.example.wyyu.gitsamlpe.test.canvas.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.wyyu.gitsamlpe.R

class GameImageView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {
        private const val RES_ID = R.mipmap.image_test_1
        private const val WIDTH_DIVIDE = 12
        private const val LIST = 4
        private const val ROW = 4
    }

    private var bitmap: Bitmap? = null
    private var paint: Paint? = null

    private var imageSize: Int = 0

    init {
        bitmap = BitmapFactory.decodeResource(resources, RES_ID)
        paint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)

        val viewWidth = measuredWidth
        imageSize = (viewWidth - WIDTH_DIVIDE * (LIST - 1)) / LIST
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (bitmap == null) {
            return
        }
        for (index in 0 until ROW) {
            for (position in 0 until LIST) {
                val left = (imageSize + WIDTH_DIVIDE) * position * 1.0f
                val top = (imageSize + WIDTH_DIVIDE) * index * 1.0f
                canvas?.drawBitmap(bitmap!!, left, top, paint)
            }
        }
    }
}