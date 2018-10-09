package hanyu.com.whattockotlin.activities

import android.support.v7.app.AppCompatActivity
import hanyu.com.whattockotlin.fragments.BaseFragment

/**
 * Created by HanYu on 2018/8/22.
 */
open class BaseActivity : AppCompatActivity(){

    fun addFragment(containerResourceId: Int, fc: Class<out BaseFragment>, tag: String) {
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

    }

}