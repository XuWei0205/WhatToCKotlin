package hanyu.com.whattockotlin.commons

import android.support.v4.view.ViewPager
import android.view.View

/**
 * Created by HanYu on 2018/12/20.
 */
class MyPageTransformer: ViewPager.PageTransformer {
    companion object {
        private const val MAX_ALPHA = 0.5f
        private const val MAX_SCALE = 0.9f
    }
    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {
            //不可见区域
            page.alpha = MAX_ALPHA
            page.scaleX = MAX_SCALE
            page.scaleY = MAX_SCALE
        } else {
            //可见区域，透明度效果
            if (position <= 0) {
                //pos区域[-1,0)
                page.alpha = MAX_ALPHA + MAX_ALPHA * (1 + position)
            } else {
                //pos区域[0,1]
                page.alpha = MAX_ALPHA + MAX_ALPHA * (1 - position)
            }
            //可见区域，缩放效果
            val scale = Math.max(MAX_SCALE, 1 - Math.abs(position))
            page.scaleX = scale
            page.scaleY = scale
        }
    }
}