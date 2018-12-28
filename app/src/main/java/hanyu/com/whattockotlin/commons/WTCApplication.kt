package hanyu.com.whattockotlin.commons

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.jessyan.autosize.AutoSizeConfig

/**
 * Created by HanYu on 2018/10/26.
 */
class WTCApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        initARouter()
        initAutoSize()
    }

    private fun initARouter() {
        if (Debug.DEVELOP_MODE) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initAutoSize(){
        AutoSizeConfig.getInstance().isCustomFragment = true
    }

    companion object {
        @JvmStatic
        lateinit var app: WTCApplication
    }
}