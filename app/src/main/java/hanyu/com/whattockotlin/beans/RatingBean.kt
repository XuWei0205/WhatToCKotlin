package hanyu.com.whattockotlin.beans

import com.google.gson.annotations.SerializedName

/**
 * Created by HanYu on 2018/8/23.
 */
class RatingBean {
    var max: Float = 0.0f
    var average: Float = 0.0f
    var stars: Float = 0.0f
    var min: Float = 0.0f
    var details: RatingDetailBean? = null
    var value: Int = 0
}

class RatingDetailBean {
    @SerializedName("1")
    var one: Int = 0
    @SerializedName("2")
    var two: Int = 0
    @SerializedName("3")
    var three: Int = 0
    @SerializedName("4")
    var four: Int = 0
    @SerializedName("5")
    var five: Int = 0
}
