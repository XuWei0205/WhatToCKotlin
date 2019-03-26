package hanyu.com.whattockotlin.beans

import com.google.gson.annotations.SerializedName

/**
 * Created by HanYu on 2019/3/26.
 */
class CommendBean {
    var rating: RatingBean? = null
    var title: String = ""
    @SerializedName("subject_id")
    var subjectId: String = ""
    var author: AuthorBean? = null
    var summary: String = ""
    var alt: String = ""
    var id: String = ""


}