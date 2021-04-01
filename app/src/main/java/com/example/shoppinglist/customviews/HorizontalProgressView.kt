package com.example.shoppinglist.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Typeface
import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.example.shoppinglist.R
import kotlin.math.log
// import android.view.accessibility.AccessibilityNodeInfo
import kotlin.math.min

// import androidx.core.view.AccessibilityDelegateCompat
// import androidx.core.view.ViewCompat
// import androidx.core.view.accessibility.AccessibilityNodeInfoCompat



class HorizontalProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewWidth: Float = 0.0f
    private var viewHeight: Float = 0.0f

    private val DP_IN_PX = Resources.getSystem().displayMetrics.density
    private val DEFAULT_PROGRESS_STROKE_WIDTH = 16 * DP_IN_PX

    private var widthBar: Float = 0.0f
    private var maxWidthBar = 0.0f
    private var centerX : Float = 0f
    private var centerY : Float = 0f



    private val progressPaint: Paint
    private val progressTextPaint: Paint
    private val backgroundProgressPaint: Paint

    private val animationInterpolator by lazy { DecelerateInterpolator() }

    /**
     * Represents current progress from 0 to 100.
     */
    var progress: Int = 0
        set(value) {
            field = value
            ValueAnimator.ofFloat(widthBar, maxWidthBar / 100f * progress).apply {
                interpolator = animationInterpolator
                duration = 300
                addUpdateListener { animation ->
                    widthBar = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }

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
        progress = 50
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
        viewWidth = width.toFloat()
        viewHeight = height.toFloat()

        centerX = viewWidth/ 2f
        centerY = viewHeight / 2f

        maxWidthBar = viewWidth - (2*DEFAULT_PROGRESS_STROKE_WIDTH)

        // Update init value progressBar
        progress = progress
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

        val startX = centerX - (viewWidth/2) + DEFAULT_PROGRESS_STROKE_WIDTH
        val startY = centerY
        val stopY = centerY

        // background
        val stopX = centerX + (viewWidth/2) - DEFAULT_PROGRESS_STROKE_WIDTH
        canvas.drawLine(startX,startY, stopX, stopY, backgroundProgressPaint )

        // progressBar
        var stopXProgress = widthBar + DEFAULT_PROGRESS_STROKE_WIDTH
        print(stopXProgress)

        if(progress != 0){
            canvas.drawLine(startX,startY, stopXProgress, stopY, progressPaint )
        }



    }}