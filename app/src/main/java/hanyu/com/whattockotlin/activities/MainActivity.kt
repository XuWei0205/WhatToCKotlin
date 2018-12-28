package hanyu.com.whattockotlin.activities

import android.os.Bundle
import com.chaychan.library.BottomBarItem
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.Toaster.toast
import hanyu.com.whattockotlin.fragments.BaseFragment
import hanyu.com.whattockotlin.fragments.LatestFragment
import hanyu.com.whattockotlin.fragments.LatestFragment2
import hanyu.com.whattockotlin.network.ConnectUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var currentFragment: BaseFragment? = null
    private var fragments = arrayOfNulls<BaseFragment>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toast(this, ConnectUtil.isConnecting(this).toString())
        setContentView(R.layout.activity_main)
        initView()
        jumpToFragment(1)
    }

    private fun initView() {
        bottomBarLayout.currentItem = 1
        bottomBarLayout.setOnItemSelectedListener { _: BottomBarItem, _: Int, newPosition: Int ->
            val f = jumpToFragment(newPosition)!!.show()
            fragments.filter { it != f }.forEach { it?.hide() }
            currentFragment = f
        }
    }


    private fun jumpToFragment(position: Int): BaseFragment? {
        val fragment = fragments[position] ?: when (position) {
        //0 ->
            1 -> addFragment(R.id.flMainContent, LatestFragment::class.java, "最近")
            0 -> addFragment(R.id.flMainContent, LatestFragment2::class.java, "test")
            2 -> addFragment(R.id.flMainContent, LatestFragment2::class.java, "test")
            else -> addFragment(R.id.flMainContent, LatestFragment::class.java, "最近")
        }
        fragments[position] = fragment
        return fragment
    }
/*
    override fun onResume() {
        super.onResume()
        val tu = TestUtil()
        tu.getStatusBarHeight(this)
    }*/
}
