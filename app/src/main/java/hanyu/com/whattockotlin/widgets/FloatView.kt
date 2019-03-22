package hanyu.com.whattockotlin.widgets

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.MotionEvent
import hanyu.com.whattockotlin.commons.SuperLog

/**
 * Created by HanYu on 2019/2/15.
 */
class FloatView : FloatingActionButton {
    private var mX: Float = 0f
    private var mY: Float = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    //  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    // constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP -> performClick()
            MotionEvent.ACTION_MOVE -> {
                SuperLog.LogI("x--->${ev.x}----->${ev.y}")
            }
        }
        SuperLog.LogI("${ev?.action}")
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }


}