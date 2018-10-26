package hanyu.com.whattockotlin.widgets

import android.content.Context
import android.databinding.BindingAdapter
import android.util.AttributeSet
import android.widget.ImageView
import hanyu.com.whattockotlin.commons.loadRoundImage


/**
 * Created by HanYu on 2018/8/29.
 */
class GlideLoadImageView : ImageView {
    private lateinit var mContext: Context

    companion object {
        @BindingAdapter("app:glideImgv")
        @JvmStatic
        fun set(view: GlideLoadImageView, glideImgv: String) {
            view.setGlideImgv(glideImgv)
        }

    }


    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {

    }


    fun setGlideImgv(glideImgv: String) {
        loadRoundImage(mContext, glideImgv, 5f)
    }
}