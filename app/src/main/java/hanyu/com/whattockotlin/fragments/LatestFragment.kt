package hanyu.com.whattockotlin.fragments

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import android.widget.Toast
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.apis.API
import hanyu.com.whattockotlin.apis.IAPI
import hanyu.com.whattockotlin.beans.SubjectBean
import hanyu.com.whattockotlin.commons.RecycleAdapter
import kotlinx.android.synthetic.main.fragment_latest.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HanYu on 2018/8/23.
 */
open class LatestFragment<ViewModel : ViewDataBinding> : BaseFragment<ViewModel>() {
    private var mListAdapter: RecycleAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_latest
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val rootView: View = mViewModel.root
        mListAdapter = RecycleAdapter()
        rootView.rv_main_list.adapter = mListAdapter
        getData()
    }

    private fun getData() {
        //todo 网络请求优化
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val mIAPI: IAPI = retrofit.create<IAPI>(IAPI::class.java)
        val call = mIAPI.getLatestMovie(0, 5, "广州")//暂时写死
        call.enqueue(object : Callback<SubjectBean> {
            override fun onFailure(call: Call<SubjectBean>?, t: Throwable?) {
                Toast.makeText(activity, "失败$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SubjectBean>?, response: Response<SubjectBean>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }

}