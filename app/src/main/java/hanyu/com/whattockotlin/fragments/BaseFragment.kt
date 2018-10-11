package hanyu.com.whattockotlin.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import java.lang.ref.WeakReference

/**
 * Created by HanYu on 2018/8/23.
 */
open abstract class BaseFragment : Fragment() {
    var name = toString()
    //private var mEventBus: EventBus? =  EventBus.getDefault()



    fun show():BaseFragment{
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.beginTransaction()?.show(this)?.commitAllowingStateLoss()
        return this
    }

    fun hide(){
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.beginTransaction()?.hide(this)?.commitAllowingStateLoss()
    }




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