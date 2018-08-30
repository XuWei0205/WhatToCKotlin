package hanyu.com.whattockotlin.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.fragments.LatestFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var manager = fragmentManager
        var transaction = manager.beginTransaction()
        transaction.add(R.id.fl_main_content, LatestFragment())
        transaction.commit()
        var fb: FloatingActionButton = findViewById(R.id.fb_test)
        fb.setOnClickListener({
            startActivity(Intent(this, MovieDetailActivity::class.java))
        })
    }
}
