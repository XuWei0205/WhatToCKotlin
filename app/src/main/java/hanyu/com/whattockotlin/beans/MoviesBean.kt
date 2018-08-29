package hanyu.com.whattockotlin.beans

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


    private fun getCastsBean(dataList: ArrayList<CastsBean>): String {
        val type = StringBuilder()
        for (tmpType in dataList) {
            type.append("/").append(tmpType)
        }
        return type.toString().substring(1)
    }

    fun getCasts(): String {
        return getCastsBean(this.casts!!)
    }

    fun getDirectors(): String {
        return getCastsBean(this.directors!!)
    }

    fun getGenres(): String {
        val type = StringBuilder()
        for (tmpType in this.genres!!) {
            type.append("/").append(tmpType)
        }
        return type.toString().substring(1)
    }
}

