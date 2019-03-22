package hanyu.com.whattockotlin.activities

import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.adapters.RecycleAdapter
import hanyu.com.whattockotlin.beans.DataBean
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.RatingBean
import hanyu.com.whattockotlin.commons.Router
import hanyu.com.whattockotlin.commons.loadImage
import hanyu.com.whattockotlin.network.NetworkManager.getBaseParams
import hanyu.com.whattockotlin.network.NetworkManager.getIAPI
import hanyu.com.whattockotlin.network.NetworkManager.request
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 2018/8/30.
 */
@Route(path = Router.MOVIE_DETAIL)
class MovieDetailActivity : BaseActivity(), RecycleAdapter.IBindData {


    private lateinit var movieId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getStringExtra("movieId")
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
        getData(movieId)
    }

    private fun getData(movieId: String) {
        if (TextUtils.isEmpty(movieId)) {
            return
        }
        request(getIAPI().movieDetail(movieId, getBaseParams()), object : Callback<MoviesBean> {
            override fun onFailure(call: Call<MoviesBean>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<MoviesBean>?, response: Response<MoviesBean>) {
                requestResponse(response)
            }
        })
    }

    private fun requestResponse(response: Response<MoviesBean>) {
        response.body().apply {
            imgvDetailCover.loadImage(this@MovieDetailActivity, this.images?.large!!)
            toolbarLayout.title = this.title
            tvSummary.text = this.summary
            setRating(this.rating)
        }


    }


    private fun setRating(ratingBean: RatingBean?) {
        ratingBean ?: return
        val adapter = RecycleAdapter(R.layout.item_movie, this)
        val list = listOf<Int>(ratingBean.details!!.one,
                ratingBean.details!!.two,
                ratingBean.details!!.three,
                ratingBean.details!!.four,
                ratingBean.details!!.five)
        //todo 拓展data类型
        //adapter.addData(list)
        rvRating.adapter = adapter



    }

    override fun onBind(binding: ViewDataBinding, dataBean: DataBean) {

    }
}