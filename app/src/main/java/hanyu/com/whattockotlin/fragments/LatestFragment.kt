package hanyu.com.whattockotlin.fragments

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.adapters.BannerAdapter
import hanyu.com.whattockotlin.adapters.RecycleAdapter
import hanyu.com.whattockotlin.beans.DataBean
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.commons.*
import hanyu.com.whattockotlin.databinding.LatestFragmentDataBinding
import hanyu.com.whattockotlin.network.NetworkManager
import hanyu.com.whattockotlin.network.NetworkManager.getIAPI
import hanyu.com.whattockotlin.network.NetworkManager.putParam
import hanyu.com.whattockotlin.network.NetworkManager.request
import kotlinx.android.synthetic.main.banner_content.*
import kotlinx.android.synthetic.main.fragment_latest.*
import kotlinx.android.synthetic.main.item_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 2018/8/23.
 */
class LatestFragment : BaseFragment(), RecycleAdapter.IBindData, WeakHandler.IWeakCallBack {


    private lateinit var mListAdapter: RecycleAdapter
    private var weakHandler = WeakHandler(this.javaClass, this)
    private var bannerList = arrayListOf<MoviesBean>()
    private var bannerAdapter = BannerAdapter(bannerList)


    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mViewModel: LatestFragmentDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
        return mViewModel.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getData()
        initBanner()
    }

    private fun getData() {
        val params = NetworkManager.getBaseParams()
                .putParam("start", 0)
                .putParam("count", 20)
                .putParam("city", "广州")
        request(getIAPI().getLatestMovie(params = params), object : Callback<SubjectBean> {
            override fun onResponse(call: Call<SubjectBean>?, response: Response<SubjectBean>) {
                requestResponse(response)
            }
            override fun onFailure(call: Call<SubjectBean>?, t: Throwable?) {
                Toaster.toast(activity!!, t.toString())
            }
        })
    }

    fun requestResponse(response: Response<SubjectBean>) {
        val dataList: ArrayList<MoviesBean> = response.body().subjects!!
        bannerList.addAll(dataList.subList(0, 5))
        bannerAdapter.notifyDataSetChanged()
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

    private fun initBanner() {
        vpMainBanner.apply {
            pageMargin = 40
            offscreenPageLimit = 3
            setPageTransformer(true, MyPageTransformer())
            adapter = bannerAdapter

        }

    }

    override fun onHandleMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}