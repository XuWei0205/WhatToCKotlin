package hanyu.com.whattockotlin.activities

import android.os.Bundle
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.fragments.LatestFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        jumpToFragment(1)
    }

    private fun initView() {
        bottom_barLayout.currentItem = 1
    }

    private fun jumpToFragment(position: Int) {
        when (position) {
        //0 ->
            1 -> addFragment(R.id.fl_main_content, LatestFragment::class.java, "最近")

        }
    }
}
