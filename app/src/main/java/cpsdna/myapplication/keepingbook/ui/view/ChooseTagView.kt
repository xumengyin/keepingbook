package cpsdna.myapplication.keepingbook.ui.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.animation.addListener
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.graphics.withTranslation
import cpsdna.myapplication.keepingbook.R
import cpsdna.myapplication.keepingbook.util.textWidth

class ChooseTagView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_COLOR = 0x00000000.toInt()
        const val DEFAULT_STROKE_COLOR = 0xCCCCCC.toInt()
        const val DEFAULT_TEXT_COLOR = Color.BLACK
    }

    var tagText: CharSequence? = null
        set(value) {
            field = value
            requestLayout()
        }
    var progress: Float = 0f
    set(value) {
        field=value
        postInvalidate()
    }

    private var tagColor: Int
    var strokeColor: Int
    var strokeSize: Int
    var tagTextSize: Int
    var dotRadius: Int
    var tagPadding: Int

    val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val forground:Drawable
    private lateinit var textLayout: StaticLayout

    init {
        var atts = context.obtainStyledAttributes(attrs, R.styleable.ChooseTagView)
        tagColor = atts.getColor(R.styleable.ChooseTagView_chooseTagColor, DEFAULT_COLOR)
        strokeColor = atts.getColor(R.styleable.ChooseTagView_chooseTagStrokeColor, DEFAULT_COLOR)
        tagTextSize = atts.getDimensionPixelOffset(R.styleable.ChooseTagView_chooseTagTextSize, 10)
        strokeSize = atts.getDimensionPixelOffset(R.styleable.ChooseTagView_chooseTagStrokeSize, 10)
        forground = atts.getDrawableOrThrow(R.styleable.ChooseTagView_chooseTagForground).apply {
            callback=this@ChooseTagView
        }

        //测试属性
        tagPadding = 30
        dotRadius = 12 * 3
        initPaint()
        atts.recycle()
        clipToOutline = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var noTextWidth = tagPadding * 3 + (dotRadius + strokeSize) * 2
        val width = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec) - noTextWidth
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(widthMeasureSpec) - noTextWidth
            else -> Int.MAX_VALUE
        }
        createStaticLayout(width)
        val w = noTextWidth + textLayout.textWidth()
        val h = textLayout.height + tagPadding * 2
        setMeasuredDimension(w, h)
        forground.setBounds(0, 0, w, h)
        //设置点击效果的范围，不然是个矩形 clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, w, h, h / 2f)
            }
        }
    }

    private fun createStaticLayout(width: Int) {
        textLayout = StaticLayout.Builder.obtain(tagText, 0, tagText?.length!!, textPaint, width).build()

    }

    private fun initPaint() {

        textPaint.color = DEFAULT_TEXT_COLOR
        textPaint.textSize = tagTextSize.toFloat()
        dotPaint.color = tagColor
        dotPaint.style = Paint.Style.FILL
        bgPaint.color = strokeColor
        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = strokeSize.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawBG(this)
            drawDot(this)
            drawTagText(this)
            drawForground(this)
        }
    }

    override fun verifyDrawable(who: Drawable?): Boolean {
        return super.verifyDrawable(who) || who == forground
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        forground.state = drawableState
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        forground.jumpToCurrentState()
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        forground.setHotspot(x, y)
    }


    private fun drawForground(canvas: Canvas)
    {
        forground.draw(canvas)
    }
    private fun drawTagText(canvas: Canvas) {
        canvas.withTranslation(
            x = (strokeSize + tagPadding * 2 + dotRadius * 2)-(dotRadius/2f+tagPadding)*progress,
            y = (height - textLayout.height) / 2f
        )
        {
            textLayout.draw(canvas)
        }
    }

    private fun drawBG(canvas: Canvas) {
        canvas.drawRoundRect(
            strokeSize / 2f,
            strokeSize / 2f,
            width - strokeSize / 2f,
            height - strokeSize / 2f,
            (height - strokeSize * 2f) / 2,
            (height - strokeSize * 2f) / 2,
            bgPaint
        )
    }


    private fun drawDot(canvas: Canvas) {
        canvas.drawCircle((strokeSize + tagPadding + dotRadius).toFloat(), height / 2f, dotRadius+(progress*(width-tagPadding)), dotPaint)

    }

    public fun animateProgress() {
        val startValue = if (progress == 0f) 0f else 1f
        val endValue = if (progress == 0f) 1f else 0f
        ObjectAnimator.ofFloat(this, "progress", startValue, endValue).apply {

            addListener(onEnd = {
                progress = endValue
            })
            duration=500
            start()
        }
    }
}