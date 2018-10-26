package hanyu.com.whattockotlin.fragments

import hanyu.com.whattockotlin.R

/**
 * Created by HanYu on 2018/10/25.
 */
class BaseRefreshFragment : BaseFragment() {
    override fun getLayoutResource(): Int {
        return R.layout.fragment_pull_to_refresh
    }

}