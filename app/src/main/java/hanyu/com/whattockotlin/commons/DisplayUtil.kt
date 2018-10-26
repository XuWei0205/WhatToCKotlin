package hanyu.com.whattockotlin.commons

import android.content.Context

/**
 * Created by HanYu on 2018/10/24.
 */

    /**dp转px
     * @param dpValue dp值**/
    fun dpToPx(context: Context, dpValue: Float): Int {
        return (dpValue * getDensity(context) + 0.5f).toInt()
    }


    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }
