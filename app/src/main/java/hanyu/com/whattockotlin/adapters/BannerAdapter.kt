package hanyu.com.whattockotlin.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.MoviesBean


/**
 * Created by HanYu on 2018/12/20.
 */
class BannerAdapter(private val bannerList: ArrayList<MoviesBean>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return bannerList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = DataBindingUtil.inflate<ViewDataBinding> (LayoutInflater.from(container.context), R.layout.item_banner, container, false).root
        val bind: ViewDataBinding = DataBindingUtil.bind(view)
        bind.setVariable(BR.item_banner, bannerList[position])
        container.addView(view)
        return view
    }

}