package hanyu.com.whattockotlin.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.network.apis.API
import hanyu.com.whattockotlin.network.apis.IAPI
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.commons.Router
import hanyu.com.whattockotlin.commons.loadImage
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val mIAPI: IAPI = retrofit.create<IAPI>(IAPI::class.java)
        val call = mIAPI.movieDetail(movieId)//暂时写死
        Log.i("timeTimeCurrent1", System.currentTimeMillis().toString())
        call.enqueue(object : Callback<MoviesBean> {
            override fun onResponse(call: Call<MoviesBean>?, response: Response<MoviesBean>?) {
                if (response != null) {
                    requestResponse(response)
                    Log.i("timeTimeCurrent2", System.currentTimeMillis().toString())
                    Log.i("response", response.toString())
                }
            }

            override fun onFailure(call: Call<MoviesBean>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun requestResponse(response: Response<MoviesBean>) {
        Log.i("timeTimeCurrent3", System.currentTimeMillis().toString())
        imgv_detail_cover.loadImage(this@MovieDetailActivity, response.body().images?.large!!)

    }

}