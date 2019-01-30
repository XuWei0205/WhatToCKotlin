package hanyu.com.whattockotlin.widgets

import android.content.Context
import android.databinding.BindingAdapter
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.dpToPx


/**
 * Created by HanYu on 2018/8/24.
 */
class RatingBar : LinearLayout {
    private val starts: ArrayList<ImageView>? = arrayListOf()

    companion object {
        private const val NORMAL_START_DRAWABLE_RESOURCE: Int = R.mipmap.ic_start_grey
        private const val SELECTED_START_DRAWABLE_RESOURCE: Int = R.mipmap.ic_start_red
        private const val HALF_START_DRAWABLE_RESOURCE: Int = R.mipmap.ic_start_red

        @BindingAdapter("app:start")
        @JvmStatic
        fun set(ratingBar: RatingBar, start: Int) {
            ratingBar.setStart(start)
        }
    }

    constructor(context: Context) : super(context) {
        initRatingView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initRatingView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initRatingView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initRatingView(context)
    }

    private fun initRatingView(context: Context) {
        val params: LinearLayout.LayoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val viewSize: Int = dpToPx(context, 2f)
        params.setMargins(viewSize, viewSize, viewSize, viewSize)
        params.weight = 1f
        for (i in 0..4) {
            val tmpImageView = ImageView(context)
            tmpImageView.layoutParams = params
            tmpImageView.setBackgroundResource(NORMAL_START_DRAWABLE_RESOURCE)
            this@RatingBar.addView(tmpImageView)
            starts?.add(tmpImageView)
        }

    }


    private fun setStart(start: Int) {
        val mRate = when {
            start < 0 -> 0
            start > 5 -> 5
            else -> start
        }

        for (i in 0..4) {
            val mResource: Int = if (i < mRate) SELECTED_START_DRAWABLE_RESOURCE else NORMAL_START_DRAWABLE_RESOURCE
            starts?.get(i)?.setImageResource(mResource)
        }


    }

    fun setHalfStart(rating: Float) {
        var count = rating
        if (count < 0) {
            count = 0f
        } else if (count > 5) {
            count = 5f
        }
        var boolean = (rating % 1) != 0f
        for (i in 0..4) {
            val mResource: Int = when {
                i + 1 < count -> NORMAL_START_DRAWABLE_RESOURCE
                i + 1 > count && boolean -> {
                    boolean = false
                    HALF_START_DRAWABLE_RESOURCE
                }
                else -> NORMAL_START_DRAWABLE_RESOURCE
            }
            starts?.get(i)?.setImageResource(mResource)
        }

    }


}