package hanyu.com.whattockotlin.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import hanyu.com.whattockotlin.R
import kotlinx.android.synthetic.main.activity_movie_detail.*

/**
 * Created by HanYu on 2018/8/30.
 */
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

    }
}