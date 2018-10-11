package hanyu.com.whattockotlin.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.adapters.HomeAdapter
import hanyu.com.whattockotlin.apis.API
import hanyu.com.whattockotlin.apis.IAPI
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.databinding.LatestFragmentDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HanYu on 2018/8/23.
 */
open class LatestFragment2 : BaseFragment() {


    //private var mListAdapter: RecycleAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var dataList: ArrayList<MoviesBean> = arrayListOf()
    private var homeAdapter: HomeAdapter? = null
    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mViewModel: LatestFragmentDataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
        return mViewModel.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_main_list)
        initView()
        //initAdapter()
        getData()

    }

    private fun initView() {

    }

    private fun getData() {
        //todo 网络请求优化
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val mIAPI: IAPI = retrofit.create<IAPI>(IAPI::class.java)
        val call = mIAPI.getLatestMovie(0, 10, "长沙")//暂时写死
        call.enqueue(object : Callback<SubjectBean> {
            override fun onFailure(call: Call<SubjectBean>?, t: Throwable?) {
                Toast.makeText(activity, "失败$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SubjectBean>?, response: Response<SubjectBean>?) {
                requestResponse(response!!)
            }
        })

    }


    fun requestResponse(response: Response<SubjectBean>) {
        dataList = response.body().subjects!!
        initAdapter()
        /* val dataList: ArrayList<MoviesBean> = response.body().subjects!!
         mListAdapter = RecycleAdapter(R.layout.item_movie, dataList)
         mListAdapter!!.setOnItemClickListener { _, _, position ->
             Toast.makeText(activity, dataList[position].title, Toast.LENGTH_LONG).show()
             startActivity(Intent(activity, MovieDetailActivity::class.java).putExtra("movieId", dataList[position].id))
         }
         recyclerView!!.adapter = mListAdapter
         recyclerView!!.layoutManager = LinearLayoutManager(activity)
         mListAdapter?.setBindDataListener(this)*/
    }


    private fun initAdapter() {
        homeAdapter = HomeAdapter(R.layout.item_movie, dataList)
        homeAdapter!!.openLoadAnimation()

        homeAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            Toast.makeText(activity, "id--->" + dataList[position], Toast.LENGTH_LONG).show()
        }

        recyclerView!!.adapter = homeAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
    }

}