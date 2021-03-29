
package com.example.shoppinglist
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Typeface
import android.view.accessibility.AccessibilityNodeInfo
import android.view.animation.DecelerateInterpolator
import androidx.core.content.withStyledAttributes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.example.shoppinglist.R
// import android.view.accessibility.AccessibilityNodeInfo
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
// import androidx.core.view.AccessibilityDelegateCompat
// import androidx.core.view.ViewCompat
// import androidx.core.view.accessibility.AccessibilityNodeInfoCompat



class ProgressCustom @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val DP_IN_PX = Resources.getSystem().displayMetrics.density
    private val DEFAULT_PROGRESS_STROKE_WIDTH = 16 * DP_IN_PX

    private lateinit var progressBounds: RectF
    private var sweepAngle: Float = 250f
    private var centerX : Float = 0f
    private var centerY : Float = 0f



    private val progressPaint: Paint
    private val progressTextPaint: Paint
    private val backgroundProgressPaint: Paint
    private val progressStrokeWidth: Float = DEFAULT_PROGRESS_STROKE_WIDTH

    private val animationInterpolator by lazy { DecelerateInterpolator() }

    /**
     * Represents current progress from 0 to 100.
     */
    var progress: Int = 0
        set(value) {
            field = value
            ValueAnimator.ofFloat(sweepAngle, 360f / 100f * progress).apply {
                interpolator = animationInterpolator
                duration = 300
                addUpdateListener { animation ->
                    sweepAngle = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Paint styles used for rendering are initialized here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private var radius = 0.0f                  // Radius of the circle.

    init {
        isClickable = true

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.ProgressCustom)
        val colorBackground= typedArray.getColor(R.styleable.ProgressCustom_colorBackground, Color.GRAY)
        val colorProgress= typedArray.getColor(R.styleable.ProgressCustom_colorProgress, Color.GREEN)

        progressPaint = Paint().apply {
            isAntiAlias = true
            color = colorProgress
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 16 * Resources.getSystem().displayMetrics.density
        }

        backgroundProgressPaint = Paint().apply {
            isAntiAlias = true
            color = colorBackground
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 16 * Resources.getSystem().displayMetrics.density
        }

        progressTextPaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            strokeWidth = 0f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create("cabin", Typeface.BOLD)
            textSize = 120f
        }

        progress = 30
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * the view was just added to the view hierarchy, it is called with the old
     * values of 0. The code determines the drawing bounds for the custom view.
     *
     * @param width    Current width of this view.
     * @param height    Current height of this view.
     * @param oldWidth Old width of this view.
     * @param oldHeight Old height of this view.
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        radius = (min(width, height) / 2.0 * 0.7).toFloat()
        val viewWidth = layoutParams.width.toFloat()
        val viewHeight = layoutParams.height.toFloat()

        val arcStart = progressStrokeWidth +50
        val arcEnd = viewWidth - arcStart
        progressBounds = RectF(arcStart, arcStart, arcEnd, arcEnd)

        centerX = viewWidth/ 2f
        centerY = viewHeight / 2f
    }

    /**
     * Renders view content: an outer circle to serve as the "dial",
     * and a smaller black circle to server as the indicator.
     * The position of the indicator is based on fanSpeed.
     *
     * @param canvas The canvas on which the background will be drawn.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        canvas.drawArc(progressBounds, 90f, 360f, false, backgroundProgressPaint)

        canvas.drawArc(progressBounds, 90f, sweepAngle, false, progressPaint)

        canvas.drawText("$progress%", centerX, centerY + 35f, progressTextPaint)



}}
