package hanyu.com.whattockotlin.commons

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import hanyu.com.whattockotlin.BR
import hanyu.com.whattockotlin.beans.DataBean


/**
 * Created by HanYu on 2018/8/24.
 */
class RecycleAdapter : RecyclerView.Adapter<RecycleAdapter.ItemViewHolder>() {


    var mData: ArrayList<IItem>? = arrayListOf()
    var bindListener: IBindData? = null

    interface IItem {
        fun getBean(): DataBean
        fun getItemLayout(): Int
    }

    interface IBindData {
        fun onBind(binding: ViewDataBinding, item: IItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, viewType, bindListener!!)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(mData!![position])
    }

    override fun getItemViewType(position: Int): Int {
        return mData?.get(position)!!.getItemLayout()
    }

    fun setBindDataListener(listener: IBindData) {
        this@RecycleAdapter.bindListener = listener
    }

    fun setItem(item: IItem) {
        clearItem()
        mData?.add(item)
        notifyDataSetChanged()
    }

    fun setItem(items: List<IItem>) {
        clearItem()
        mData?.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: IItem, index: Int) {
        mData?.add(index, item)
    }

    fun addItem(item: IItem) {
        mData?.add(item)
    }

    fun addItems(items: List<IItem>, index: Int) {
        mData?.addAll(index, items)
    }

    fun addItems(items: List<IItem>) {
        mData?.addAll(items)
    }

    fun removeItem(item: IItem) {
        mData?.remove(item)
    }


    fun clearItem() {
        mData?.clear()
    }

    class ItemViewHolder(private val binding: ViewDataBinding, private val bindingListener: IBindData) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: IItem) {
            bindingListener.onBind(binding,item)
            binding.setVariable(BR.item_movie, item.getBean())
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