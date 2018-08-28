package hanyu.com.whattockotlin.fragments

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus

/**
 * Created by HanYu on 2018/8/23.
 */
open abstract class BaseFragment : Fragment() {
    private var mEventBus: EventBus? = null



    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEventBus!!.register(this)
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
        mEventBus!!.unregister(this)
    }

    open fun getLayoutView(): View? {
        return null
    }

    abstract fun getLayoutResource(): Int
}