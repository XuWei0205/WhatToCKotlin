package hanyu.com.whattockotlin.fragments

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by HanYu on 2018/8/23.
 */
open abstract class BaseFragment : Fragment() {
    var name = toString()
    //private var mEventBus: EventBus? =  EventBus.getDefault()



    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mEventBus!!.register(this)
    }

    /*override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = getLayoutView()
        if (view == null) {
            mViewModel = DataBindingUtil.inflate(inflater, getLayoutResource(), null, false)
            view = mViewModel.root
        } else {
            mViewModel = DataBindingUtil.bind(view)
        }
        return view
    }*/


    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        //mEventBus!!.unregister(this)
    }

    open fun getLayoutView(): View? {
        return null
    }

    abstract fun getLayoutResource(): Int
}