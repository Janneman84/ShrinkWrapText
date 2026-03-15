package shrinkwrap.xml

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

/**
 * This subclass fixes a sizing issue from the regular `TextView`.
 * Normally, when the width is set to WRAP_CONTENT, the width will be maxed out when the text doesn't fit on one line.
 * This often causes the view not to tightly fit the text, but cause a gap at the end instead.
 * This commonly causes chat bubbles to look a bit different than they do on iOS.
 * `ShrinkWrapTextView` addresses the issue.
 *  Simply replace your regular `TextView` with this and the size will shrink to always tightly fit its content.
 *  You can use the `shrinkWrap` property or the `custom:shrinkWrap` attribute to disable the shrink-wrapping.
 *  Alternatively, instead of using this class you can also use the `measureShrinkWrappedWidth()` function (see its doc for more info).
 */

open class ShrinkWrapTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink-wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth(widthMeasureSpec)
        if (width != measuredWidth) {
            setMeasuredDimension(width, measuredHeight)
        }
    }
}

/**
 * This subclass fixes a sizing issue from the regular `TextView`.
 * Normally, when the width is set to WRAP_CONTENT, the width will be maxed out when the text doesn't fit on one line.
 * This often causes the view not to tightly fit the text, but cause a gap at the end instead.
 * This commonly causes chat bubbles to look a bit different than they do on iOS.
 * `ShrinkWrapTextView` addresses the issue.
 *  Simply replace your regular `TextView` with this and the size will shrink to always tightly fit its content.
 *  You can use the `shrinkWrap` property or the `custom:shrinkWrap` attribute to disable the shrink-wrapping.
 *  Alternatively, instead of using this class you can also use the `measureShrinkWrappedWidth()` function (see its doc for more info).
 */

open class ShrinkWrapMaterialTextView : MaterialTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink-wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth(widthMeasureSpec)
        if (width != measuredWidth) {
            setMeasuredDimension(width, measuredHeight)
        }
    }
}

/**
 * Button version of `ShrinkWrapTextView`, check its documentation for information.
 */
// Button is a subclass of TextView, so the code of this class is exactly the same as ShrinkWrapTextView.
open class ShrinkWrapButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink-wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth(widthMeasureSpec)
        if (width != measuredWidth) {
            setMeasuredDimension(width, measuredHeight)
        }
    }
}

/**
 * Button version of `ShrinkWrapTextView`, check its documentation for information.
 */
// Button is a subclass of TextView, so the code of this class is exactly the same as ShrinkWrapTextView.
open class ShrinkWrapMaterialButton : MaterialButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink-wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth(widthMeasureSpec)
        if (width != measuredWidth) {
            setMeasuredDimension(width, measuredHeight)
        }
    }
}

/**
 * If you want to shrink-wrap your TextView but you can't use the ShrinkWrapTextView subclass,
 * you can use this function instead. Just override onMeasure() in your own TextView subclass exactly like this:
 *
 * Kotlin:
 *
 *     override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
 *         super.onMeasure(widthMeasureSpec, heightMeasureSpec) // Call super first!
 *         setMeasuredDimension(measureShrinkWrappedWidth(widthMeasureSpec), measuredHeight)
 *     }
 *
 * Java:
 *
 *     @Override
 *     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 *         super.onMeasure(widthMeasureSpec, heightMeasureSpec); // Call super first!
 *         setMeasuredDimension(ShrinkWrapTextViewKt.measureShrinkWrappedWidth(this, widthMeasureSpec, true), getMeasuredHeight());
 *     }
 *
 * */
fun TextView.measureShrinkWrappedWidth(widthMeasureSpec: Int, shrinkWrap: Boolean = true): Int {

    assert(this !is ShrinkWrapTextView, {
        "\n\nMake sure measureShrinkWrappedWidth() is not called from an instance or subclass of ShrinkWrapTextView.\nEither remove the call or change the class.\n"
    })
    assert(layout != null, {
        "\n\nMake sure to only call measureShrinkWrappedWidth() inside onMeasure(), after calling super.onMeasure().\n"
    })

    return if (shrinkWrap) actuallyMeasureShrinkWrappedWidth(widthMeasureSpec) else measuredWidth
}

private fun TextView.checkShrinkWrapAttribute(attrs: AttributeSet?): Boolean {
    val attributes = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.ShrinkWrapTextView,
        0, 0
    )

    try {
        return attributes.getBoolean(R.styleable.ShrinkWrapTextView_shrinkWrap, true)
    } finally {
        attributes.recycle()
    }
}

private fun TextView.actuallyMeasureShrinkWrappedWidth(widthMeasureSpec: Int): Int {

    // No magic needed if there is only 1 line.
    if (layout.lineCount <= 1 || isGone || View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.AT_MOST) {
        return measuredWidth
    }

    val layoutWidthF = layout.width.toFloat()
    var maxCenterWidth = 0f
    var hasLeft = false
    var hasRight = false
    var hasCenter = false
    var maxLineWidth: Float = (minimumWidth - (measuredWidth - layout.width)).toFloat()

    var alignment = Layout.Alignment.ALIGN_NORMAL
    var textDirection = 1

    for (i in 0 until layout.lineCount) {

        // Check for situations that don't have a clear alignment.
        if (layout.getLineLeft(i) < 0f || layout.getLineRight(i) > layoutWidthF) {
            return measuredWidth
        }

        val lineWidth = layout.getLineMax(i)
        if (lineWidth > maxLineWidth ) {
            maxLineWidth = lineWidth
        }

        // If line is wrapped it will have same alignment as previous line, so skip
        val ls = layout.getLineStart(i)
        if (i == 0 || ls <= 0 || layout.text[ls - 1] == '\n') {
            alignment = layout.getParagraphAlignment(i)
            textDirection = layout.getParagraphDirection(i)
        }

        when (alignment) {
            Layout.Alignment.ALIGN_CENTER -> {
                hasCenter = true
                maxCenterWidth = max(maxCenterWidth, lineWidth)
            }
            Layout.Alignment.ALIGN_NORMAL -> {
                when (textDirection) {
                    1 -> hasLeft = true
                    -1 -> hasRight = true
                    else -> return measuredWidth
                }
            }
            Layout.Alignment.ALIGN_OPPOSITE -> {
                when (textDirection) {
                    -1 -> hasLeft = true
                    1 -> hasRight = true
                    else -> return measuredWidth
                }
            }
        }
    }

    val maxLayoutWidth = { min(
        View.MeasureSpec.getSize(widthMeasureSpec).toFloat(),
        (if (maxWidth >=0) maxWidth.toFloat() else maxEms*textSize)
    ) - (measuredWidth - layout.width) }

    if ((hasLeft xor hasRight) && hasCenter) {
        maxLineWidth = max(maxLineWidth, ((maxLayoutWidth() - maxCenterWidth) * 0.5f) + maxCenterWidth)
    }
    else if (hasLeft && hasRight) {
        maxLineWidth = maxLayoutWidth()
    }

    // Replace full text width with shrink-wrapped text width.
    val shrinkWrappedWidth = measuredWidth - layout.width + ceil(maxLineWidth).toInt()

    if (shrinkWrappedWidth == measuredWidth) {
        return measuredWidth
    }

    // Apparently this somehow invalidates/triggers some layout stuff.
    // Without this right aligned text would get clipped, unless the TextView is wrapped inside a RelativeLayout.
    // Alternatively, `text=text` works too
    setPadding(paddingLeft - 1, paddingTop, paddingRight + 1, paddingBottom)
    setPadding(paddingLeft + 1, paddingTop, paddingRight - 1, paddingBottom)

    return shrinkWrappedWidth
}