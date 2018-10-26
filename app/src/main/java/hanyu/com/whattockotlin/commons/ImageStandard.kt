package hanyu.com.whattockotlin.commons

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by HanYu on 2018/10/26.
 */
fun ImageView.loadImage(context: Context, imageUrl: String) {
    Glide.with(context)
            .load(imageUrl)
            .into(this)
}

/**加载圆角图片四角相同
 * @param roundValue 圆角值 单位dp**/
fun ImageView.loadRoundImage(context: Context, imageUrl: String, roundValue: Float) {
    Glide.with(context)
            .load(imageUrl)
            .transform(GlideRoundTransformation(context, roundValue))
            .into(this)
}