package hanyu.com.whattockotlin.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.MoviesBean
import hanyu.com.whattockotlin.beans.RatingBean
import hanyu.com.whattockotlin.commons.Router
import hanyu.com.whattockotlin.commons.dpToPx
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
class MovieDetailActivity : BaseActivity() {


    private lateinit var movieId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getStringExtra("movieId")
        setContentView(R.layout.activity_movie_detail)
        getData(movieId)
        rvRating.layoutManager = LinearLayoutManager(this)
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

    @SuppressLint("SetTextI18n")
    private fun requestResponse(response: Response<MoviesBean>) {
        response.body().apply {
            imgvDetailCover.loadImage(this@MovieDetailActivity, this.images?.large!!)
            toolbarLayout.title = this.title
            tvSummary.text = this.summary
            //缩进处理
            val span = SpannableStringBuilder("缩进" + tvSummary.text)
            span.setSpan(ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            tvSummary.text = span
            tvRatting.text = this.rating?.average.toString()
            setRating(this.rating)
        }


    }


    private fun setRating(ratingBean: RatingBean?) {
        ratingBean ?: return
        val list = listOf(
                ProgressItem(ratingBean.details!!.five, 5),
                ProgressItem(ratingBean.details!!.four, 4),
                ProgressItem(ratingBean.details!!.three, 3),
                ProgressItem(ratingBean.details!!.two, 2),
                ProgressItem(ratingBean.details!!.one, 1)
        )
        val adapter = ItemAdapter(R.layout.item_ratting, list, getSum(list), this)
        rvRating.adapter = adapter

    }

    private fun getSum(list: List<ProgressItem>): Int {
        var sum = 0
        for (item in list) {
            sum += item.progress
        }
        return sum
    }

    class ItemAdapter(layoutResId: Int, data: List<ProgressItem>, private val sum: Int, private val context: Context) : BaseQuickAdapter<ProgressItem, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder, item: ProgressItem) {
            helper.setProgress(R.id.pbRatingPercent, item.progress * 100 / sum)
            for (i in 0 until item.startNum) {
                val iv = ImageView(context)
                iv.layoutParams = params
                iv.setBackgroundResource(R.mipmap.ic_start_grey)
                helper.getView<LinearLayout>(R.id.ll_collection).addView(iv)
            }


        }

        private val params =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        .apply {
                            width = dpToPx(context, 12f)
                            height = dpToPx(context, 12f)
                        }


    }

    class ProgressItem(val progress: Int, val startNum: Int)


}