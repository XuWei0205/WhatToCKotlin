package hanyu.com.whattockotlin.commons

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by HanYu on 2018/10/26.
 */

fun jumpTo(url: String): Postcard = ARouter.getInstance().build(url)
