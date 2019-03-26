package hanyu.com.whattockotlin.widgets.dialogs

import android.app.DialogFragment


/**
 * Created by HanYu on 2019/3/25.
 */
abstract class BaseDialog : DialogFragment() {

    abstract fun getLayouId(): Int
}