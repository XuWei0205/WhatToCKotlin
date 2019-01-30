package hanyu.com.whattockotlin.widgets

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.SuperLog
import hanyu.com.whattockotlin.commons.dpToPx


/**
 * Created by HanYu on 2019/1/10.
 */
class RatingAnimationView : View {
    private var ratingListener: IRatingListener? = null
    private var margin = 0f
    private var startsSize = 0f
    private var likeBitmap: Bitmap? = null
    private var unlikeBitmap: Bitmap? = null
    private var bitmapPaint: Paint? = null
    private var startNum = 0//星级 1..5
    private var canSetup = true//是否可以通过点击设置值
    private var startScale = 1f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        margin = dpToPx(context, 10f).toFloat()
        startsSize = dpToPx(context, 50f).toFloat()
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        likeBitmap = setSize(startsSize, BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like))
        unlikeBitmap = setSize(startsSize, BitmapFactory.decodeResource(resources, R.mipmap.ic_message_unlike))

    }

    private fun setSize(size: Float, bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = size / width
        val scaleHeight = size / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = View.MeasureSpec.makeMeasureSpec((4 * margin + 5 * startsSize).toInt(), MeasureSpec.EXACTLY)
        val mHeight = View.MeasureSpec.makeMeasureSpec((2 * margin + startsSize).toInt(), MeasureSpec.EXACTLY)
        super.onMeasure(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawDefault(canvas)
    }

    private fun drawDefault(canvas: Canvas?) {
        for (i in 0..4) {
            val bitmap = if (startNum < i + 1) unlikeBitmap else likeBitmap
            canvas?.save()
            //canvas?.scale(startScale, startScale, (bitmap!!.width * i*10 / 2).toFloat(), (bitmap.height / 2).toFloat())
            canvas?.drawBitmap(bitmap, (startsSize + margin) * i, margin, bitmapPaint)
            canvas?.restore()
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                performClick()
            }
            MotionEvent.ACTION_DOWN -> {
                if (canSetup) {
                    startNum = range(event.x)
                    ratingListener?.onRating(startNum)
                    showAnimation()
                    //invalidate()
                }
            }

        }
        return super.onTouchEvent(event)
    }

    private fun showAnimation() {
        val startScaleAnimator = ObjectAnimator.ofFloat(this, "startScale", 1.2f, 0.8f, 1f)
        startScaleAnimator.duration = 10000
        startScaleAnimator.start()
    }



    fun setStartScale(startScale: Float) {
        this.startScale = startScale
        invalidate()
    }

    private fun range(x: Float): Int {
        return when (x) {
            in 0..(startsSize + margin * 0.5).toInt() -> 1
            in (startsSize + margin * 0.5).toInt()..(startsSize * 2 + margin * 1.5).toInt() -> {
                2
            }
            in (startsSize * 2 + margin * 1.5).toInt()..(startsSize * 3 + margin * 2.5).toInt() -> {
                3
            }
            in (startsSize * 3 + margin * 2.5).toInt()..(startsSize * 4 + margin * 3.5).toInt() -> {
                4
            }
            in (startsSize * 4 + margin * 3.5).toInt()..width -> {
                5
            }
            else -> {
                return 1
            }
        }

    }

    private fun setCanChangeble(canSetup: Boolean) {
        this.canSetup = canSetup

    }

    private fun setStartNum(startNum: Int) {
        this.startNum = startNum
        invalidate()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        likeBitmap?.recycle()
        unlikeBitmap?.recycle()
    }

    fun setRatingListener(ratingListener: IRatingListener) {
        this.ratingListener = ratingListener
    }

    fun setStartSize(startSize: Float) {
        this.startsSize = startsSize
    }

    fun setMarging(margin: Float) {
        this.margin = margin
    }

    interface IRatingListener {
        fun onRating(rating: Int)
    }

}