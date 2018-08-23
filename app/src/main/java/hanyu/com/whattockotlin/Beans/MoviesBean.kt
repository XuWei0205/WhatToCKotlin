package hanyu.com.whattockotlin.Beans

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2018/8/23.
 */
class MoviesBean {
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
}

