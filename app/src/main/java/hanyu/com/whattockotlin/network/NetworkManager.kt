package hanyu.com.whattockotlin.network

import hanyu.com.whattockotlin.commons.Debug
import hanyu.com.whattockotlin.commons.SuperLog.LogI
import hanyu.com.whattockotlin.commons.WTCApplication
import hanyu.com.whattockotlin.network.apis.API
import hanyu.com.whattockotlin.network.apis.IAPI
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by HanYu on 2018/10/26.
 */
object NetworkManager {
    private var params = HashMap<String, Any>()
    private var cacheSize = 10 * 1024 * 1024L // 10 MiB
    private var timeout = 10 * 1000L
    private var cache = Cache(WTCApplication.app.cacheDir, cacheSize)
    private val internetInterceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build()
        } else {
            originalResponse
        }
    }

    private val offlineInterceptor = Interceptor { chain ->
        val resp: Response
        val req: Request
        if (ConnectUtil.isConnecting(WTCApplication.app)) {
            //有网络,检查10秒内的缓存
            req = chain.request()
                    .newBuilder()
                    .cacheControl(CacheControl.Builder()
                            .maxAge(10, TimeUnit.SECONDS)
                            .build())
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control", "public, max-age=" + 60)
                    .build()
        } else {
            //无网络,检查30天内的缓存,即使是过期的缓存
            req = chain.request().newBuilder()
                    .cacheControl(CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(30, TimeUnit.DAYS)
                            .build())
                    .removeHeader("Pragma")
                    .addHeader("Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28)
                    .build()
        }
        resp = chain.proceed(req)
        resp.newBuilder().build()
    }
    private var client = OkHttpClient().newBuilder()
            .cache(cache)
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor(internetInterceptor)
            .addInterceptor(offlineInterceptor)

    @JvmStatic
    fun <T> request(call: Call<T>, callback: Callback<T>) {
        call.enqueue(callback)
    }

    /**默认Gson格式**/
    @JvmStatic
    fun getIAPI(factory: Converter.Factory = GsonConverterFactory.create()): IAPI {
        printLog()
        return Retrofit.Builder()
                .client(client.build())
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
            client.addInterceptor(httpLoggingInterceptor)
        }
    }

    fun getBaseParams(): HashMap<String, Any> {
        params["apikey"] = API.DOUBAN_APP_API_KEY
        return params
    }

    fun HashMap<String, Any>.putParam(key: String, value: Any): HashMap<String, Any> {
        params[key] = value
        return params
    }
}