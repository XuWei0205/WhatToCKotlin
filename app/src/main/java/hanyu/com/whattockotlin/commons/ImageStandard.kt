package hanyu.com.whattockotlin.commons

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions



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
fun ImageView.loadRoundImage(context: Context, imageUrl: String, roundValue: Int) {
    Glide.with(context).load(imageUrl).apply(RequestOptions.bitmapTransform(RoundedCorners(roundValue))).into(this)
}

fun ImageView.loadCircleImage(context: Context, imageUrl: String) {
    Glide.with(context).load(imageUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}