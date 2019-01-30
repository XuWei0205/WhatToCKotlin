package hanyu.com.whattockotlin.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hanyu.com.whattockotlin.R
import hanyu.com.whattockotlin.commons.dpToPx


/**
 * Created by HanYu on 2019/1/2.
 */
class LikeItView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var likeNumber = 0
    private var textRound: Rect? = null
    private var width = 8f
    private var bitmapPaint: Paint? = null
    private var textPaint: Paint? = null
    private var oldTextPaint: Paint? = null
    private var likeBitmap: Bitmap? = null
    private var unlikeBitmap: Bitmap? = null
    private var shiningBitmap: Bitmap? = null
    private var isLike: Boolean = false
    private var handScale = 1f//缩放比例
    private var alphaScale = 0f
    private var shiningScale = 0f
    private var isFirst = false
    private var textAlpha = 0f
    private var maxTextMove = 0f
    private var textDistance = 0f
    private var widths: FloatArray = FloatArray(8)
    private var animationDuration: Long = 250
    private var shiningAlpha = 0f
    init {
        val typedArray: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.LikeItView)
        likeNumber = typedArray.getInt(R.styleable.LikeItView_like_num, 0)
        maxTextMove = dpToPx(context, 20f).toFloat()
        typedArray.recycle()
        initPaint()

    }

    private fun initPaint() {
        textRound = Rect()
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        oldTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint?.also {
            it.color = resources.getColor(R.color.typeColor)
            it.textSize = dpToPx(context, 14f).toFloat()
        }

        oldTextPaint?.also {
            it.color = resources.getColor(R.color.typeColor)
            it.textSize = dpToPx(context, 14f).toFloat()
        }

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //构造Bitmap对象，通过BitmapFactory工厂类的static Bitmap decodeResource根据给定的资源id解析成位图
        likeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like)
        unlikeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_unlike)
        shiningBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like_shining)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        //释放bitmap资源
        likeBitmap?.recycle()
        unlikeBitmap?.recycle()
        shiningBitmap?.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidthMeasureSpec: Int
        val mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(unlikeBitmap?.height!!.plus(dpToPx(context, 20f)), View.MeasureSpec.EXACTLY)
        val textNumber = likeNumber.toString()
        val textWidth = textPaint?.measureText(textNumber, 0, textNumber.length)
        mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(likeBitmap?.width!!.plus(textWidth!!).plus(dpToPx(context, 30f)).toInt(), View.MeasureSpec.EXACTLY)

        super.onMeasure(mWidthMeasureSpec, mHeightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /*====画点赞的小手====*/
        drawHand(canvas)
        /*====画出点赞时出现在小手上的四个小点====*/
        drawShining(canvas)
        /*====画出点赞数====*/
        drawText(canvas)


    }

    private fun drawHand(canvas: Canvas) {
        val height = height
        val centerY = height / 2
        val drawBitmap = if (isLike) likeBitmap else unlikeBitmap
        val drawBitmapWidth = drawBitmap?.width
        val drawBitmapHeight = drawBitmap?.height
        val drawTop = (height - drawBitmapHeight!!) / 2
        canvas.save()
        if (drawBitmapWidth != null) {
            //根据bitmap中心进行缩放
            canvas.scale(handScale, handScale, (drawBitmapWidth / 2).toFloat(), centerY.toFloat())
        }
        //画bitmap小手，第一个是参数对应的bitmap，第二个参数是左上角坐标，第三个参数上顶部坐标，第四个是画笔
        canvas.drawBitmap(drawBitmap, dpToPx(context, 10f).toFloat(), drawTop.toFloat(), bitmapPaint)
        //读取没有缩放之前的画布状态
        canvas.restore()
        /*
        canvas.save()和canvas.restore()因为整个点赞效果是有动画效果的,对画布进行缩放,
        如果不保存画布之前的状态，缩放后继续绘制其他图像效果并不是你想要的。
        * */

    }


    private fun drawShining(canvas: Canvas) {
        val top = (height - likeBitmap?.height!!) / 2 - shiningBitmap!!.height + dpToPx(context, 17f)
        //设置透明度
        bitmapPaint?.alpha = (225 * alphaScale).toInt()
        //保存画布状态
        canvas.save()
        //设置缩放
        canvas.scale(shiningScale, shiningScale, (likeBitmap!!.width / 2).toFloat(), top.toFloat())
        canvas.restore()
        //恢复透明度？？？
        bitmapPaint?.alpha = 225
        if (isLike) {
            canvas.drawBitmap(shiningBitmap, dpToPx(context, 50f).toFloat(), top.toFloat(), bitmapPaint)
        } else {
            canvas.save()
            bitmapPaint?.alpha = 0
            canvas.drawBitmap(shiningBitmap, dpToPx(context, 15f).toFloat(), top.toFloat(), bitmapPaint)
            canvas.restore()
            bitmapPaint?.alpha = 225
        }

    }

    private fun drawText(canvas: Canvas) {
        maxTextMove = dpToPx(context, 20f).toFloat()
        val likeValue = likeNumber.toString()
        val changeValue: String
        if (isLike) {
            changeValue = (likeNumber - 1).toString()
        } else {
            if (isFirst) {
                changeValue = (likeNumber + 1).toString()
            } else {
                isFirst = true
                changeValue = likeValue
            }
        }

        //获取文字边界
        textPaint?.getTextBounds(likeValue, 0, likeValue.length, textRound)
        //文字x轴坐标
        var textX = likeBitmap!!.width + dpToPx(context, 20f)
        val textY = (height - textRound!!.top - textRound!!.bottom) / 2//???
        //点赞前后数字位数发生变化 99->100 999->1000
        if (likeValue.length != changeValue.length || maxTextMove == 0f) {
            oldTextPaint?.alpha = (225 * (1 - textAlpha)).toInt()
            val moveRange = if (isLike) {
                (textY - maxTextMove + textDistance)
            } else {
                (textY + maxTextMove + textDistance)
            }
            canvas.drawText(changeValue, textX.toFloat(), moveRange, oldTextPaint)
            textPaint?.alpha = (225 * textAlpha).toInt()
            canvas.drawText(likeValue, textX.toFloat(), (textY + textDistance), textPaint)
        }else{
            //点赞前后点赞位数不发生变化
            textPaint?.getTextWidths(likeValue, widths)
            val chars = likeValue.toCharArray()
            val oldChars = changeValue.toCharArray()
            chars.forEachIndexed { index, c ->
                if (oldChars[index] == c) {
                    textPaint?.alpha = 225
                    canvas.drawText(chars[index].toString(), textX.toFloat(), textY.toFloat(), textPaint)
                } else {
                    oldTextPaint?.alpha = (225 * (1 - textAlpha)).toInt()
                    val textMoveRange = if (isLike) {
                        (textY - maxTextMove + textDistance)
                    } else {
                        (textY + maxTextMove + textDistance)
                    }
                    canvas.drawText(oldChars[index].toString(), textX.toFloat(), textMoveRange, oldTextPaint)
                    textPaint?.alpha = (225 * textAlpha).toInt()
                    canvas.drawText(c.toString(), textX.toFloat(), (textY + textDistance).toFloat(), textPaint)
                }
                textX += widths[index].toInt()
            }

        }

    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                clickLickIt()
            }
            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun clickLickIt() {
        isLike = !isLike
        //点击时手指放大与缩小动画
        val handScaleAnimator = ObjectAnimator.ofFloat(this, "handScale", 1f, 0.8f, 1f)
        handScaleAnimator.duration = animationDuration
        if (isLike) {
            ++likeNumber
            val shiningScaleAnimator = ObjectAnimator.ofFloat(this, "shiningScale", 0f, 1f)
            val shiningAlphaAnimator = ObjectAnimator.ofFloat(this, "shiningAlpha", 0f, 1f)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(handScaleAnimator, shiningScaleAnimator, shiningAlphaAnimator)
            animatorSet.start()
        } else {
            --likeNumber
            handScaleAnimator.start()
            setShiningAlpha(0f)
        }
        setLikeNumberAnimation()


    }

    //设置数字变化动画
    private fun setLikeNumberAnimation() {
        val animationY = if (isLike) {
            maxTextMove
        } else {
            -maxTextMove
        }

        val textAlphaAnimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "textAlpha", 0f, 1f)
        textAlphaAnimator.duration = animationDuration
        val textMoveAnimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "textTranslate", animationY, 0f)
        textMoveAnimator.duration = animationDuration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(textAlphaAnimator, textMoveAnimator)
        animatorSet.start()

    }

    fun setAlphaScale(alphaScale: Float) {
        this.alphaScale = alphaScale
        invalidate()
    }


    fun setTextAlpha(textAlpha: Float) {
        this.textAlpha = textAlpha
        invalidate()
    }

    fun setTextTranslate(textTranslate: Float) {
        textDistance = textTranslate
        invalidate()
    }

    fun setHandScale(handScale: Float) {
        this.handScale = handScale
        invalidate()
    }

    fun setShiningScale(shiningScale: Float) {
        this.shiningScale = shiningScale
        invalidate()
    }

    fun setShiningAlpha(shiningAlpha: Float) {
        this.shiningAlpha = shiningAlpha
        invalidate()
    }

    fun setLikeNum(likeNum: Int) {
        this.likeNumber = likeNum
    }


}
