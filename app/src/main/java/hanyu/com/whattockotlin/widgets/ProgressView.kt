package hanyu.com.whattockotlin.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import hanyu.com.whattockotlin.R


/**
 * Created by HanYu on 2019/2/21.
 */
class ProgressView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**分段颜色 */
    //private val SECTION_COLORS = intArrayOf(R.color.colorStart, R.color.colorEnd)
    private val SECTION_COLORS = intArrayOf(Color.GREEN,Color.YELLOW,Color.RED)
    /**进度条最大值 */
    private var maxCount: Float = 100f
    /**进度条当前值 */
    private var currentCount: Float = 1f
    /**画笔 */
    private var mPaint: Paint? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        val round = (mHeight / 2).toFloat()
        println("max=$maxCount  current=$currentCount")
        mPaint!!.color = Color.rgb(71, 76, 80)
        val rectBg = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        canvas.drawRoundRect(rectBg, round, round, mPaint)
        mPaint!!.color = Color.BLACK
        val rectBlackBg = RectF(2f, 2f, (mWidth - 2).toFloat(), (mHeight - 2).toFloat())
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint)

        val section = currentCount / maxCount
        val rectProgressBg = RectF(3f, 3f, (mWidth - 3) * section, (mHeight - 3).toFloat())
        if (section <= 1.0f / 3.0f) {
            if (section != 0.0f) {
                mPaint!!.color = SECTION_COLORS[0]
            } else {
                mPaint!!.color = Color.TRANSPARENT
            }
        } else {
            val count = if (section <= 1.0f / 3.0f * 2) 2 else 3
            val colors = IntArray(count)
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count)
            val positions = FloatArray(count)
            if (count == 2) {
                positions[0] = 0.0f
                positions[1] = 1.0f - positions[0]
            } else {
                positions[0] = 0.0f
                positions[1] = maxCount / 3 / currentCount
                positions[2] = 1.0f - positions[0] * 2
            }
            positions[positions.size - 1] = 1.0f
            val shader = LinearGradient(3f, 3f, (mWidth - 3) * section, (mHeight - 3).toFloat(), colors, null, Shader.TileMode.MIRROR)
            mPaint!!.shader = shader
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint)
    }

    private fun dipToPx(dip: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dip * scale + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    fun setMaxCount(maxCount: Float) {
        this.maxCount = maxCount
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    fun setCurrentCount(currentCount: Float) {
        this.currentCount = if (currentCount > maxCount) maxCount else currentCount
        invalidate()
    }

    fun getMaxCount(): Float {
        return maxCount
    }

    fun getCurrentCount(): Float {
        return currentCount
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        mWidth = if (widthSpecMode == View.MeasureSpec.EXACTLY || widthSpecMode == View.MeasureSpec.AT_MOST) {
            widthSpecSize
        } else {
            0
        }
        mHeight = if (heightSpecMode == View.MeasureSpec.AT_MOST || heightSpecMode == View.MeasureSpec.UNSPECIFIED) {
            dipToPx(15)
        } else {
            heightSpecSize
        }
        setMeasuredDimension(mWidth, mHeight)
    }


}