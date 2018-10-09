package hanyu.com.whattockotlin.commons

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.beans.DataBean


/**
 * Created by HanYu on 2018/8/24.
 */
class RecycleAdapter(layoutId: Int, data: List<DataBean>) : BaseQuickAdapter<DataBean, RecycleAdapter.ItemViewHolder>(layoutId, data) {

    override fun convert(helper: ItemViewHolder?, dataBean: DataBean?) {
        if (dataBean != null) {
            helper?.bindTo(dataBean)
        }
    }

    private var bindListener: IBindData? = null


    interface IBindData {
        fun onBind(binding: ViewDataBinding, dataBean: DataBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, mLayoutResId, bindListener!!)
    }

    fun setBindDataListener(listener: IBindData) {
        this@RecycleAdapter.bindListener = listener
    }

    class ItemViewHolder(private val binding: ViewDataBinding, private val bindingListener: IBindData) : BaseViewHolder(binding.root) {

        fun bindTo(dataBean: DataBean) {
            bindingListener.onBind(binding, dataBean)
            binding.setVariable(BR.item_movie, dataBean)
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup, viewType: Int, bindListener: IBindData): ItemViewHolder {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                        viewType, parent, false)
                return ItemViewHolder(binding, bindListener)
            }
        }
    }

}