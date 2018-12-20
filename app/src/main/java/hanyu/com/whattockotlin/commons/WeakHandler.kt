package hanyu.com.whattockotlin.commons

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by HanYu on 2018/12/20.
 */
class WeakHandler<T>(t: Class<T>, private val callback: IWeakCallBack) : Handler() {
    private var weakReference: WeakReference<Class<T>> = WeakReference(t)
    private var theT = weakReference.get()
    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        if (theT == null) {
            return
        }
        callback.onHandleMessage()
    }

    interface IWeakCallBack {
        fun onHandleMessage()
    }
}
