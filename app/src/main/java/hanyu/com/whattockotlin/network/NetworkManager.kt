package hanyu.com.whattockotlin.network

import hanyu.com.whattockotlin.commons.Debug
import hanyu.com.whattockotlin.commons.SuperLog.LogI
import hanyu.com.whattockotlin.network.apis.API
import hanyu.com.whattockotlin.network.apis.IAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HanYu on 2018/10/26.
 */
object NetworkManager {
    private val httpClientBuilder = OkHttpClient.Builder()
    @JvmStatic
    fun <T> request(call: Call<T>, callback: Callback<T>) {
        call.enqueue(callback)
    }

    @JvmStatic
    fun getIAPIByGson(): IAPI {
        printLog()
        return Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IAPI::class.java)
    }

    @JvmStatic
    fun getIAPIBy(factory: Converter.Factory): IAPI {
        printLog()
        return Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(API.BASE_URL)
                .addConverterFactory(factory)
                .build().create(IAPI::class.java)
    }

    private fun printLog() {
        if (Debug.DEVELOP_MODE) {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                LogI(message)
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
    }
}