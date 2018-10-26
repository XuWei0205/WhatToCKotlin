package hanyu.com.whattockotlin.commons

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by HanYu on 2018/10/26.
 */
class WTCApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initARouter()
    }

    private fun initARouter() {
        if (Debug.DEVELOP_MODE) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}