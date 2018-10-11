package hanyu.com.whattockotlin.activities

import android.os.Bundle
import com.chaychan.library.BottomBarItem
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.fragments.BaseFragment
import hanyu.com.whattockotlin.fragments.LatestFragment
import hanyu.com.whattockotlin.fragments.LatestFragment2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var currentFragment: BaseFragment? = null
    private var fragments = arrayOfNulls<BaseFragment>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        jumpToFragment(1)
    }

    private fun initView() {
        bottom_barLayout.currentItem = 1
        bottom_barLayout.setOnItemSelectedListener { bottomBarItem: BottomBarItem, oldPosition: Int, newposition: Int ->
            val f = jumpToFragment(newposition)!!.show()
            fragments.filter { it != f }.forEach { it?.hide() }
            currentFragment = f
        }
    }


    private fun jumpToFragment(position: Int): BaseFragment? {
        val fragment = fragments[position] ?: when (position) {
        //0 ->
            1 -> addFragment(R.id.fl_main_content, LatestFragment::class.java, "最近")
            0 -> addFragment(R.id.fl_main_content, LatestFragment2::class.java, "test")
            2 -> addFragment(R.id.fl_main_content, LatestFragment2::class.java, "test")
            else -> addFragment(R.id.fl_main_content, LatestFragment::class.java, "最近")
        }
        fragments[position] = fragment
        return fragment
    }
}
