package hanyu.com.whattockotlin.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import hanyu.com.whattockotlin.fragments.BaseFragment
import me.jessyan.autosize.AutoSize

/**
 * Created by HanYu on 2018/8/22.
 */
abstract class BaseActivity : AppCompatActivity() {

    fun addFragment(containerResourceId: Int, fc: Class<out BaseFragment>, tag: String): BaseFragment? {
        val fragmentManager = supportFragmentManager

        var fragment: BaseFragment? = if (fragmentManager.findFragmentByTag(tag) != null) {
            fragmentManager.findFragmentByTag(tag) as BaseFragment
        } else {
            null
        }

        val fragmentTransaction = fragmentManager.beginTransaction()
        if (fragment != null) {
            fragmentTransaction.attach(fragment)
            fragmentTransaction.show(fragment)
            fragment.name = tag
        } else {
            fragment = fc.newInstance()
            fragment.name = tag
            fragmentTransaction.add(containerResourceId, fragment, tag)
        }
        fragmentTransaction.commitAllowingStateLoss()
        return fragment

    }

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        fixStatusBar()
    }


    override fun onResume() {
        super.onResume()
        AutoSize.autoConvertDensityOfGlobal(this)
    }

    open fun fixStatusBar() {
        ImmersionBar.with(this)
                .init()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }


}