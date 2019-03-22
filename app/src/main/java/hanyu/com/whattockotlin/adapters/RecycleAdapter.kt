package hanyu.com.whattockotlin.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.beans.DataBean


/**
 * Created by HanYu on 2018/8/24.
 */
class RecycleAdapter(layoutId: Int, private var bindListener: IBindData, private var variableId: Int = 0) : BaseQuickAdapter<DataBean, RecycleAdapter.ItemViewHolder>(layoutId) {


    override fun convert(helper: ItemViewHolder, dataBean: DataBean?) {
        if (dataBean != null) {
            val binding = helper.getBinding()
            helper.bindTo(dataBean, binding)
        }
    }


    interface IBindData {
        fun onBind(binding: ViewDataBinding, dataBean: DataBean)
    }


    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        return super.onCreateDefViewHolder(parent, viewType).setBindingListener(bindListener).setVariableId(variableId)
    }


    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }


    class ItemViewHolder(view: View) : BaseViewHolder(view) {
        private lateinit var bindingListener: IBindData
        private var variableId: Int = 0


        fun setBindingListener(bindingListener: IBindData): ItemViewHolder {
            this.bindingListener = bindingListener
            return this
        }


        fun setVariableId(variableId: Int): ItemViewHolder {
            this.variableId = variableId
            return this
        }


        fun getBinding(): ViewDataBinding {
            return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
        }


        fun bindTo(dataBean: DataBean, binding: ViewDataBinding) {
            bindingListener.onBind(binding, dataBean)
            if (variableId == 0) return
            binding.setVariable(variableId, dataBean)
            binding.executePendingBindings()
        }

    }

}