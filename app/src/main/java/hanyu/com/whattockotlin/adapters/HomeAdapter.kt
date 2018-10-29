package hanyu.com.whattockotlin.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.MoviesBean

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class HomeAdapter(layoutResId: Int, data: List<MoviesBean>) : BaseQuickAdapter<MoviesBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MoviesBean) {
        helper.setText(R.id.tvItemMovieType,item.getGenres())
        helper.setText(R.id.tvItemMovieCasts,item.getCasts())
        helper.setText(R.id.tvItemMovieDirectors,item.getDirectors())

    }
}
