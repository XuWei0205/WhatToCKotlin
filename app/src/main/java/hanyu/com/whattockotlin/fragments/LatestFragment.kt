package hanyu.com.whattockotlin.fragments

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
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
    private val dataList = arrayListOf<MoviesBean>()
    private var bannerAdapter = BannerAdapter(bannerList)
    private var mListAdapter = RecycleAdapter(R.layout.item_movie, dataList, this, BR.item_movie)
    private var vp: ViewPager? = null
    private var index = 0
    private var bannerPosition = 1

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
        dataList.addAll(response.body().subjects!!)
        //bannerList.add(dataList[4])
        bannerList.addAll(dataList.subList(0, 5))
        // bannerList.add(dataList[0])
        // vp?.currentItem = 1
        bannerAdapter.notifyDataSetChanged()
        mListAdapter.notifyDataSetChanged()
        mListAdapter.setOnItemClickListener { _, _, position ->
            jumpTo(Router.MOVIE_DETAIL).withString("movieId", dataList[position].id).navigation()
        }
        rvMainList.adapter = mListAdapter
        rvMainList.layoutManager = LinearLayoutManager(activity)
        mListAdapter.openLoadAnimation()
        weakHandler.sendEmptyMessage(1)

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
                    position.let {
                        if (position == 0) {
                        }
                    }
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
        /* when (msg?.what) {
             1 -> {
                 if (bannerList.size == 0) {
                     return
                 }
                 vp!!.currentItem = when (bannerPosition) {
                     bannerList.size - 2 -> {
                         index += 2
                         1
                     }
                     else -> {
                         index++
                         index % bannerList.size
                     }
                 }
                 weakHandler.sendEmptyMessageDelayed(1, 3000)

             }
         }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        weakHandler.removeMessages(1)
    }


}