package hanyu.com.whattockotlin.commons

import android.content.Context
import android.widget.Toast

/**
 * Created by HanYu on 2018/12/4.
 */
object Toaster {
    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}