package hanyu.com.whattockotlin.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.dpToPx
import java.util.*


/**
 * Created by HanYu on 2019/1/10.
 */
class RatingAnimationView2 : LinearLayout {
    private var ratingListener: IRatingListener? = null
    private var margin = 0f
    private var startsSize = 0f
    private var bitmapPaint: Paint? = null
    private var startNum = 0//星级 1..5
    private var canSetup = true//是否可以通过点击设置值
    private var starts: ArrayList<ImageView> = arrayListOf()
    private var showAnimation: Boolean = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    companion object {
        private const val LIKE_DRAWABLE_RESOURCE = R.mipmap.ic_message_like
        private const val UNLIKE_DRAWABLE_RESOUCE = R.mipmap.ic_message_unlike
    }

    init {
        margin = dpToPx(context, 10f).toFloat()
        startsSize = dpToPx(context, 50f).toFloat()
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val params: LinearLayout.LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val intMargins = margin.toInt()
        params.setMargins(intMargins / 2, intMargins, intMargins / 2, intMargins)
        params.weight = 1f
        for (i in 0..4) {
            val tmpImageView = ImageView(context)
            tmpImageView.layoutParams = params
            tmpImageView.setBackgroundResource(UNLIKE_DRAWABLE_RESOUCE)
            this.addView(tmpImageView)
            starts.add(tmpImageView)
        }
    }


    private fun setStarts() {

    }


    private fun drawStarts() {
        val animatorSet = if (showAnimation) AnimatorSet() else null
        for (i in 0..4) {
            val mResource: Int = if (startNum < i + 1) {
                UNLIKE_DRAWABLE_RESOUCE
            } else {
                if (showAnimation) {
                    val duration = ((5 - i) * 100).toLong()
                    val targetImageView = starts[i]
                    val scaleXAnimator = ObjectAnimator.ofFloat(targetImageView, "ScaleX", 1.2f, 0.8f, 1f).setDuration(duration)
                    val scaleYAnimator = ObjectAnimator.ofFloat(targetImageView, "ScaleY", 1.2f, 0.8f, 1f).setDuration(duration)
                    val alphaAnimator = ObjectAnimator.ofFloat(targetImageView, "alpha", 0.5f, 1f).setDuration(duration)
                    animatorSet?.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator)
                }
                LIKE_DRAWABLE_RESOURCE
            }
            starts[i].setImageResource(mResource)
        }
        animatorSet?.start()

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
                    val selected = range(event.x)
                    if (startNum != selected) {
                        startNum = range(event.x)
                        drawStarts()
                    }
                    ratingListener?.onRating(startNum)
                }
            }

        }
        return super.onTouchEvent(event)
    }


    private fun range(x: Float): Int {
        return (5 * x / width).toInt() + 1
    }

    private fun setCanChangeble(canSetup: Boolean) {
        this.canSetup = canSetup

    }

    private fun setStartNum(startNum: Int) {
        this.startNum = when {
            startNum < 0 -> 0
            startNum > 5 -> 5
            else -> startNum
        }


        invalidate()
    }

    fun setRatingListener(ratingListener: IRatingListener) {
        this.ratingListener = ratingListener
    }

    fun setStartSize(startSize: Float) {
        this.startsSize = startSize
    }

    fun setMarging(margin: Float) {
        this.margin = margin
    }

    interface IRatingListener {
        fun onRating(rating: Int)
    }

    fun showAnimation(showAnimation: Boolean) {
        this.showAnimation = showAnimation
    }

}