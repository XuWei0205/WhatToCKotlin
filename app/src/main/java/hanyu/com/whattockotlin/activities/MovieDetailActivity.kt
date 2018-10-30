package hanyu.com.whattockotlin.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.commons.Router
import hanyu.com.whattockotlin.commons.loadImage
import hanyu.com.whattockotlin.network.NetworkManager.getIAPIByGson
import hanyu.com.whattockotlin.network.NetworkManager.getBaseParams
import hanyu.com.whattockotlin.network.NetworkManager.request
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by HanYu on 2018/8/30.
 */
@Route(path = Router.MOVIE_DETAIL)
class MovieDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        getData(intent.getStringExtra("movieId"))

    }

    private fun getData(movieId: String) {
        if (TextUtils.isEmpty(movieId)) {
            return
        }
        request(getIAPIByGson().movieDetail(movieId, getBaseParams()), object : Callback<MoviesBean> {
            override fun onFailure(call: Call<MoviesBean>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<MoviesBean>?, response: Response<MoviesBean>) {
                requestResponse(response)
            }
        })
    }

    private fun requestResponse(response: Response<MoviesBean>) {
        imgvDetailCover.loadImage(this@MovieDetailActivity, response.body().images?.large!!)
        toolbarLayout.title = response.body().title
    }

}