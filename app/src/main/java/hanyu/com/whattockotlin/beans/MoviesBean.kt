package hanyu.com.whattockotlin.beans

import android.util.Log
import com.google.gson.annotations.SerializedName

/**
 * Created by HanYu on 2018/8/23.
 */
open class MoviesBean : DataBean() {

    var rating: RatingBean? = null
    var genres: ArrayList<String>? = null
    var title: String? = null
    var casts: ArrayList<CastsBean>? = null
    @SerializedName("collect_count")
    var collectCount: Long = 0
    @SerializedName("original_title")
    var originalTitle: String = ""
    var subtype: String = ""
    val directors: ArrayList<CastsBean>? = null
    var year: String = ""
    var images: ImageBean? = null
    var art: String = ""
    var id: String = ""
        set(value) {
            Log.i("setHere", "-------------$value")
            field = value
        }
    var testImage: String = ""
        get() = title ?: ""



    private fun getCastsBean(dataList: ArrayList<CastsBean>): String {
        if (dataList.size == 0) {
            return ""
        }
        val casts = StringBuilder()
        for (tmpBean in dataList) {
            casts.append("/").append(tmpBean.name)
        }
        return casts.toString().substring(1)
    }

    fun getCasts(): String {
        return getCastsBean(this.casts!!)
    }

    fun getDirectors(): String {
        return getCastsBean(this.directors!!)
    }

    fun getGenres(): String {
        if (this.genres!!.size == 0) {
            return ""
        }
        val type = StringBuilder()
        for (tmpType in this.genres!!) {
            type.append("/").append(tmpType)
        }
        return type.toString().substring(1)
    }
}

