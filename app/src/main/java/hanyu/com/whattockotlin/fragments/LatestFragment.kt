package hanyu.com.whattockotlin.fragments

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.apis.API
import hanyu.com.whattockotlin.apis.IAPI
import hanyu.com.whattockotlin.beans.ListItem
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.commons.RecycleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HanYu on 2018/8/23.
 */
open class LatestFragment : BaseFragment() {
    private var mListAdapter: RecycleAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_main_list)
        mListAdapter = RecycleAdapter()
        recyclerView!!.adapter = mListAdapter
        getData()
    }

    private fun getData() {
        //todo 网络请求优化
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val mIAPI: IAPI = retrofit.create<IAPI>(IAPI::class.java)
        val call = mIAPI.getLatestMovie(0, 10, "广州")//暂时写死
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
        val listItem: ListItem? = null
        listItem?.setData(response.body().subjects!!)
        mListAdapter?.setItem(ListItem())


    }

}