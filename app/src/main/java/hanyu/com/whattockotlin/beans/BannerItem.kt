package hanyu.com.whattockotlin.beans

import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.RecycleAdapter

/**
 * Created by HanYu on 2018/8/28.
 */
class BannerItem : RecycleAdapter.IItem {
    private var bannerData: ArrayList<MoviesBean>? = null

    override fun getItemLayout(): Int {
        return R.layout.item_movie_banner
    }

    fun setData(bannerData: ArrayList<MoviesBean>) {
        this@BannerItem.bannerData = bannerData
    }

    fun ghetData(): ArrayList<MoviesBean> {
        return this@BannerItem.bannerData!!
    }
}