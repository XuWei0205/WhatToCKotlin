package hanyu.com.whattockotlin.widgets

import android.content.Context
import android.databinding.BindingAdapter
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import hanyu.com.whattockotlin.commons.GlideRoundTransformation

/**
 * Created by HanYu on 2018/8/29.
 */
class GlideLoadImageView : ImageView {
    private var mContext: Context? = null

    companion object {
        @BindingAdapter("app:glideImgv")
        @JvmStatic
        fun set(view: GlideLoadImageView, glideImgv: String) {
            view.setGlideImgv(glideImgv)
        }

    }


    constructor(context: Context?) : super(context) {
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
        Glide.with(mContext)
                .load(glideImgv)
                .transform(GlideRoundTransformation(mContext!!, 50f))
                .into(this)


    }
}