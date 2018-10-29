package hanyu.com.whattockotlin.fragments

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.DataBean
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.commons.RecycleAdapter
import hanyu.com.whattockotlin.commons.Router
import hanyu.com.whattockotlin.commons.jumpTo
import hanyu.com.whattockotlin.databinding.LatestFragmentDataBinding
import hanyu.com.whattockotlin.network.NetworkManager.getIAPIByGson
import hanyu.com.whattockotlin.network.NetworkManager.request
import kotlinx.android.synthetic.main.fragment_latest.*
import kotlinx.android.synthetic.main.item_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 2018/8/23.
 */
open class LatestFragment : BaseFragment(), RecycleAdapter.IBindData {
    private lateinit var mListAdapter: RecycleAdapter


    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mViewModel: LatestFragmentDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
        return mViewModel.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getData()
    }

    private fun getData() {
        val params = mapOf("start" to 0, "count" to 20, "city" to "广州")
        request(getIAPIByGson().getLatestMovie(params = params), object : Callback<SubjectBean> {
            override fun onResponse(call: Call<SubjectBean>?, response: Response<SubjectBean>) {
                requestResponse(response)
            }
            override fun onFailure(call: Call<SubjectBean>?, t: Throwable?) {
                Toast.makeText(activity, "失败$t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun requestResponse(response: Response<SubjectBean>) {
        val dataList: ArrayList<MoviesBean> = response.body().subjects!!
        mListAdapter = RecycleAdapter(R.layout.item_movie, dataList,this, BR.item_movie)
        mListAdapter.setOnItemClickListener { _, _, position ->
            jumpTo(Router.MOVIE_DETAIL).withString("movieId", dataList[position].id).navigation()
        }
        rvMainList.adapter = mListAdapter
        rvMainList.layoutManager = LinearLayoutManager(activity)
        mListAdapter.openLoadAnimation()

    }

    override fun onBind(binding: ViewDataBinding, dataBean: DataBean) {
        if (dataBean is MoviesBean) {
            binding.root.tvItemMovieType.text = dataBean.getGenres()
            binding.root.tvItemMovieCasts.text = dataBean.getCasts()
            binding.root.tvItemMovieDirectors.text = dataBean.getDirectors()
        }

    }

}