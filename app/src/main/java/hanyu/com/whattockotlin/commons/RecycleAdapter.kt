package hanyu.com.whattockotlin.commons

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by HanYu on 2018/8/24.
 */
class RecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var mData: ArrayList<IItem>? = arrayListOf()

    constructor(VH: RecyclerView.ViewHolder) {

    }

    interface IItem {
        fun getItemLayout(): Int
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getItemViewType(position: Int): Int {
        return mData?.get(position)!!.getItemLayout()
    }

    fun setItem(item: IItem) {
        clearItem()
        mData?.add(item)
    }

    fun setItem(items: List<IItem>) {
        clearItem()
        mData?.addAll(items)
    }

    fun addItem(item: IItem, index: Int) {
        mData?.add(index, item)
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


}