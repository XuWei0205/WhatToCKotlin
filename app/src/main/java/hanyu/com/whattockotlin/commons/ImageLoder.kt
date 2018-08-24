package hanyu.com.whattockotlin.commons

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by HanYu on 2018/8/22.
 */

open class ImageLoder {
    companion object {
        fun loadImage(context: Context, viewTarget: ImageView, imageUrl: String) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(viewTarget)
        }

        /**加载圆角图片四角相同
         * @param roundValue 圆角值 单位dp**/
        fun loadRoundImage(context: Context, viewTarget: ImageView, imageUrl: String, roundValue: Float) {
            Glide.with(context)
                    .load(imageUrl)
                    .transform(GlideRoundTransformation(context, roundValue))
                    .into(viewTarget)
        }
    }

}
