package hanyu.com.whattockotlin.fragments

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.view.ViewPager
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
import kotlinx.android.synthetic.main.fragment_latest.*
import kotlinx.android.synthetic.main.item_movie.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 218/8/23.
 */
class LatestFragment : BaseFragment(), RecycleAdapter.IBindData, WeakHandler.IWeakCallBack {

    private var weakHandler = WeakHandler(this.javaClass, this)
    private var bannerList = arrayListOf<MoviesBean>()
    private var bannerAdapter = BannerAdapter(bannerList)
    private var mListAdapter = RecycleAdapter(R.layout.item_movie, this, BR.item_movie)
    private var vp: ViewPager? = null
    private var index = 0
    private var bannerPosition = 1
    private var pageIndex = 0

    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mViewModel: LatestFragmentDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
        return mViewModel.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvMainList.adapter = mListAdapter
        rvMainList.layoutManager = LinearLayoutManager(activity!!)
        mListAdapter.openLoadAnimation()
        mListAdapter.setOnItemClickListener { _, _, position ->
            jumpTo(Router.MOVIE_DETAIL).withString("movieId", (mListAdapter.getItem(position) as MoviesBean).id).navigation()
        }
        requestData(false)
        initRefreshLayout()
        initBanner()
    }

    private fun requestData(isLoadMore: Boolean) {
        if (!isLoadMore) {
            pageIndex = 0
            mListAdapter.setEnableLoadMore(false)
        }
        val params = NetworkManager.getBaseParams()
                .putParam("start", pageIndex * 20)
                .putParam("count", 20)
                .putParam("city", "广州")
        request(getIAPI().getLatestMovie(params = params), object : Callback<SubjectBean> {
            override fun onResponse(call: Call<SubjectBean>?, response: Response<SubjectBean>) {
                if (isLoadMore) {
                    mListAdapter.addData((response.body().subjects as List<DataBean>?)!!)
                } else {
                    mListAdapter.setNewData(response.body().subjects as List<DataBean>?)
                    bannerList.addAll(response.body().subjects!!.subList(0, 5))
                    bannerAdapter.notifyDataSetChanged()
                    weakHandler.removeMessages(1)
                    weakHandler.sendEmptyMessage(1)
                    mListAdapter.setEnableLoadMore(true)
                    swipeLayout.isRefreshing = false
                }
                if (response.body().subjects!!.size < 20) {
                    mListAdapter.loadMoreEnd(false)
                } else {
                    mListAdapter.loadMoreComplete()
                }
                pageIndex++
            }

            override fun onFailure(call: Call<SubjectBean>?, t: Throwable?) {
                Toaster.toast(activity!!, t.toString())
                if (isLoadMore) {
                    mListAdapter.loadMoreFail()
                } else {
                    mListAdapter.setEnableLoadMore(true)
                    swipeLayout.isRefreshing = false
                }
            }
        })
    }

    override fun onBind(binding: ViewDataBinding, dataBean: DataBean) {
        if (dataBean is MoviesBean) {
            binding.root.tvItemMovieType.text = dataBean.getGenres()
            binding.root.tvItemMovieCasts.text = dataBean.getCasts()
            binding.root.tvItemMovieDirectors.text = dataBean.getDirectors()
        }

    }

    private fun initBanner() {
        val view = layoutInflater.inflate(R.layout.banner_content, rvMainList.parent as ViewGroup, false)
        mListAdapter.addHeaderView(view)
        vp = view.findViewById<ViewPager>(R.id.vpMainBanner).apply {
            pageMargin = 4
            offscreenPageLimit = 3
            setPageTransformer(true, MyPageTransformer())
            adapter = bannerAdapter
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                   bannerPosition = position
                }
            })

        }

    }

    override fun onHandleMessage(msg: Message?) {
        when (msg?.what) {
            1 -> {
                if (bannerList.size == 0) {
                    return
                }
                vp!!.currentItem = index % bannerList.size
                weakHandler.sendEmptyMessageDelayed(1, 5000)
                index++
            }
        }

    }

    private fun initRefreshLayout() {
        swipeLayout.apply {
            setColorSchemeColors(Color.rgb(193, 29, 84))
            isRefreshing = true
            setOnRefreshListener { pageIndex = 0; requestData(false) }
        }
        mListAdapter.apply {
            setOnLoadMoreListener({ requestData(true) }, rvMainList)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        weakHandler.removeMessages(1)
    }


}