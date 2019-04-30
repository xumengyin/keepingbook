package cpsdna.myapplication.keepingbook.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import com.bumptech.glide.Glide.init
import cpsdna.myapplication.keepingbook.R

class TagColorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_TAG_COLOR = 0xff000000.toInt()
        const val DEFAULT_TAG_STROKE_COLOR = 0xffCCCCCC.toInt()
        const val DEFAULT_TAG_STROKE_WIDTH = 10f
    }

    var progress = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }
    var tagColor: Int = DEFAULT_TAG_COLOR
    var strokeColor: Int = DEFAULT_TAG_STROKE_COLOR
    var strokeWidth: Float = DEFAULT_TAG_STROKE_WIDTH
    private val gouDrawable: Drawable
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.TagColorView)
        tagColor = arr.getColor(R.styleable.TagColorView_tagColor, DEFAULT_TAG_COLOR)
        strokeColor = arr.getColor(R.styleable.TagColorView_tagStrokeColor, DEFAULT_TAG_STROKE_COLOR)
        strokeWidth = arr.getDimensionOrThrow(R.styleable.TagColorView_tagStrokeWidth)
        gouDrawable = ContextCompat.getDrawable(context, R.drawable.ic_gou)!!.apply {
            setBounds(-intrinsicWidth / 2, -intrinsicHeight / 2, intrinsicWidth / 2, intrinsicHeight / 2)
        }
        bgPaint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = strokeColor
        strokePaint.strokeWidth = strokeWidth
    }

    public fun setTagColorValue(@ColorInt color: Int) {
        tagColor = color
        bgPaint.color = color
        progress = 0f
    }

    public fun setCheck(check: Boolean) {
        progress = if (check) 1f else 0f
    }

    public fun isCheck(): Boolean {
        return progress == 1f
    }

    public fun animateChoose(check: Boolean) {
        val newProgress = if (check) 1f else 0f
        if (progress != newProgress) {
            ValueAnimator.ofFloat(progress, newProgress).apply {
                addUpdateListener {
                    progress = it.animatedValue as Float
                }
                doOnEnd {
                    progress = newProgress
                }
                interpolator = BounceInterpolator()
                duration = 500
            }.start()
        }
    }

    private fun drawBg(canvas: Canvas) {
        val radius = (measuredWidth - strokeWidth * 2) / 2f
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, bgPaint)
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, strokePaint)
    }

    private fun drawForground(canvas: Canvas) {
        if (progress > 0) {
            canvas.withTranslation(measuredWidth / 2f, measuredHeight / 2f) {
                canvas.withScale(progress, progress) {
                    // draw
                    gouDrawable.draw(canvas)
                }
            }

        }
    }

    private fun drawForgroundTest(canvas: Canvas) {

        canvas.withTranslation(measuredWidth / 2f, measuredHeight / 2f) {
            canvas.withScale(0.5f, 0.5f) {
                gouDrawable.draw(canvas)
            }

        }


    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawBg(this)
            drawForground(this)
            // drawForgroundTest(canvas)
        }
    }
}
