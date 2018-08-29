package hanyu.com.whattockotlin.beans

import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.RecycleAdapter

/**
 * Created by HanYu on 2018/8/28.
 */
class ListItem : RecycleAdapter.IItem {
    var dataBean: DataBean? = null

    constructor(movieBean: MoviesBean) {
        dataBean = movieBean
    }

    override fun getBean(): DataBean {
        return this.dataBean!!
    }

    //private var movieBean: MoviesBean? = null

    //private var dataList: ArrayList<MoviesBean>? = null

    override fun getItemLayout(): Int {
        return R.layout.item_movie
    }

    //fun setBean()

    /* fun setData(dataList: ArrayList<MoviesBean>) {
         this@ListItem.dataList = dataList
     }

     fun ghetData(): ArrayList<MoviesBean> {
         return this@ListItem.dataList!!
     }*/

}
