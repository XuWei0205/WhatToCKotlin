package hanyu.com.whattockotlin.beans

import com.google.gson.annotations.SerializedName

/**
 * Created by HanYu on 2018/8/30.
 */

class MovieDetailBean {
    var rating: RatingBean? = null
    @SerializedName("reviews_count")
    var reviewsCount: Int = 0
    @SerializedName("wish_count")
    var wishCount: Int = 0
    @SerializedName("douba_site")
    var doubanSite: String = ""
    var year: String = ""
    var images: ImageBean? = null
    var alt: String = ""
    var id: String = ""
    @SerializedName("mobile_url")
    var mobileUrl: String = ""
    var title: String = ""
    @SerializedName("doCount")
    var do_cout = null
    @SerializedName("share_url")
    var shareUrl: String = ""
    @SerializedName("seasons_count")
    var seasonsCount = null
    @SerializedName("schedule_url")
    var scheduleUrl: String = ""
    @SerializedName("episodes_count")
    var episodesCount = null
    var countries: ArrayList<String>? = null
    var genres: ArrayList<String>? = null
    @SerializedName("collectCount")
    var collectCount: Int = 0
    var casts: ArrayList<CastsBean>? = null
    @SerializedName("current_season")
    var currentSeason = null
    @SerializedName("original_title")
    var originalTilte: String = ""
    var summary: String = ""
    var subtyoe: String = ""
    var directors: ArrayList<CastsBean>? = null
    @SerializedName("commentCount")
    var commontCount: Int = 0
    @SerializedName("rating_count")
    var ratingCount: Int = 0
    var aka: ArrayList<String>? = null


}
