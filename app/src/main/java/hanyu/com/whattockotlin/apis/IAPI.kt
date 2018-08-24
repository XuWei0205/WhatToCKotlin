package hanyu.com.whattockotlin.apis

import hanyu.com.whattockotlin.beans.SubjectBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by HanYu  on 2018/8/23.
 */
interface IAPI {


    /**获取最近电影列表
     * @param start 开始条目
     * @param count 数量
     * @param city 所在城市
     * **/

    @GET("/v2/movie/in_theaters")
    fun getLatestMovie(@Query("start") start: Int, @Query("count") count: Int, @Query("city") city: String): Call<SubjectBean>


    /**根据电影类型获取电影列表
     * @param tag 电影类型 如“科幻”**/

    @GET("/v2/movie/search")
    fun searchMovieByType(@Query("tag") movieType: String, @Query("start") start: Int, @Query("count") count: Int): Call<SubjectBean>


    /**根据人名获取参演or导演作品
     * @param castsName 人名 如"昆汀·塔伦蒂诺"**/

    @GET("/v2/movie/search")
    fun searchMovieByCastsName(@Query("q") castsName: String, @Query("start") start: Int, @Query("count") count: Int): Call<SubjectBean>
}