package hanyu.com.whattockotlin.commons

import android.content.Context
import android.graphics.*
import android.util.Log
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Created by HanYu on 2018/8/24.
 */
class GlideRoundTransformation : BitmapTransformation {
    private var radius: Float = 0.0f


    constructor(context: Context, radius: Float) : super(context) {
        this@GlideRoundTransformation.radius = radius
    }

    constructor(context: Context) : super(context) {
        this@GlideRoundTransformation.radius = 5f
    }


    override fun getId(): String {
        return javaClass.name
    }

    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap {
        return doTransform(pool!!, toTransform!!)
    }

    private fun doTransform(pool: BitmapPool, toTransform: Bitmap): Bitmap {
       // Log.i("glideTest", "doTransform")
        var result: Bitmap? = pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, toTransform.width.toFloat(), toTransform.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }
}