package hanyu.com.whattockotlin.commons

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import hanyu.com.whattockotlin.activities.BaseActivity
import hanyu.com.whattockotlin.fragments.BaseFragment

/**
 * Created by HanYu on 2018/10/26.
 */
object RouterManager {

    fun BaseFragment.jumpTo(url: String): Postcard = ARouter.getInstance().build(url)

    fun BaseActivity.jumpTo(url: String): Postcard = ARouter.getInstance().build(url)
}