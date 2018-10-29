package hanyu.com.whattockotlin.fragments

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.activities.MovieDetailActivity
import hanyu.com.whattockotlin.beans.DataBean
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.commons.RecycleAdapter
import hanyu.com.whattockotlin.databinding.LatestFragmentDataBinding
import hanyu.com.whattockotlin.network.NetworkManager
import kotlinx.android.synthetic.main.item_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 2018/8/23.
 */
open class LatestFragment2 : BaseFragment(), RecycleAdapter.IBindData {

    private lateinit var mListAdapter: RecycleAdapter
    private lateinit var recyclerView: RecyclerView

    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mViewModel: LatestFragmentDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
        return mViewModel.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rvMainList)
        getData()
    }

    private fun getData() {
        val params = mapOf("start" to 0, "count" to 20, "city" to "北京")
        NetworkManager.request(NetworkManager.getIAPIByGson().getLatestMovie(params = params), object : Callback<SubjectBean> {
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
            Toast.makeText(activity, dataList[position].title, Toast.LENGTH_LONG).show()
            startActivity(Intent(activity, MovieDetailActivity::class.java).putExtra("movieId", dataList[position].id))
        }
        recyclerView.adapter = mListAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    override fun onBind(binding: ViewDataBinding, dataBean: DataBean) {
        if (dataBean is MoviesBean) {
            binding.root.tvItemMovieType.text = dataBean.getGenres()
            binding.root.tvItemMovieCasts.text = dataBean.getCasts()
            binding.root.tvItemMovieDirectors.text = dataBean.getDirectors()
        }

    }
}