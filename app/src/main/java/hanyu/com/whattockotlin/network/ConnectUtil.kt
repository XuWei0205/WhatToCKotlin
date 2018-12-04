package hanyu.com.whattockotlin.network

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager

/**
 * Created by HanYu on 2018/12/3.
 */
object ConnectUtil {
    @JvmStatic
    fun isConnecting(context: Context): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        if (netInfo != null) {
            return netInfo.isAvailable
        }
        return false
    }


}