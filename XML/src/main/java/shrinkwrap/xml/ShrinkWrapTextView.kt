package shrinkwrap.xml

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlin.math.ceil

/**
 * This subclass fixes a sizing issue from the regular `TextView`.
 * Normally, when the width is set to WRAP_CONTENT, the width will be maxed out when the text doesn't fit on one line.
 * This often causes the view not to tightly fit the text, but cause a gap at the end instead.
 * This commonly causes chat bubbles to look a bit different than they do on iOS.
 * `ShrinkWrapTextView` addresses the issue.
 *  Simply replace your regular `TextView` with this and the size will shrink to always tightly fit its content.
 *  You can use the `shrinkWrap` property or the `custom:shrinkWrap` attribute to disable the shrink wrapping.
 *  Alternatively, instead of using this class you can also use the `measureShrinkWrappedWidth()` function (see its doc for more info).
 */

open class ShrinkWrapTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth()
        if (width < measuredWidth) {
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
 *  You can use the `shrinkWrap` property or the `custom:shrinkWrap` attribute to disable the shrink wrapping.
 *  Alternatively, instead of using this class you can also use the `measureShrinkWrappedWidth()` function (see its doc for more info).
 */

open class ShrinkWrapMaterialTextView : MaterialTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { shrinkWrap = checkShrinkWrapAttribute(attrs) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { shrinkWrap = checkShrinkWrapAttribute(attrs) }

    /** Set to false to disable shrink wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth()
        if (width < measuredWidth) {
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

    /** Set to false to disable shrink wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth()
        if (width < measuredWidth) {
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

    /** Set to false to disable shrink wrapping. */
    var shrinkWrap = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // This gives you access to lineCount, layout.getLineMax() and measuredHeight.
        if (!shrinkWrap || isGone) { return }

        val width = actuallyMeasureShrinkWrappedWidth()
        if (width < measuredWidth) {
            setMeasuredDimension(width, measuredHeight)
        }
    }
}

/**
 * If you want to shrink wrap your TextView but you can't use the ShrinkWrapTextView subclass,
 * you can use this function instead. Just override onMeasure() in your own TextView subclass exactly like this:
 *
 * Kotlin:
 *
 *     override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
 *         super.onMeasure(widthMeasureSpec, heightMeasureSpec) // Call super first!
 *         setMeasuredDimension(measureShrinkWrappedWidth(), measuredHeight)
 *     }
 *
 * Java:
 *
 *     @Override
 *     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 *         super.onMeasure(widthMeasureSpec, heightMeasureSpec); // Call super first!
 *         setMeasuredDimension(ShrinkWrapTextViewKt.measureShrinkWrappedWidth(this), getMeasuredHeight());
 *     }
 *
 * */
fun TextView.measureShrinkWrappedWidth(): Int {

    assert(this !is ShrinkWrapTextView, {
        "This function should not be called from an instance of ShrinkWrapTextView (or subclass)."
    })
    assert(layout != null, {
        "No layout, make sure to only call this function inside onMeasure() and super.onMeasure() is called before this."
    })

    return actuallyMeasureShrinkWrappedWidth()
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

private fun TextView.actuallyMeasureShrinkWrappedWidth(): Int {

    val lineCount = layout.lineCount

    // No magic needed if there is only 1 line.
    if (lineCount <= 1 || isGone) {
        return measuredWidth
    }

    // Make sure this view or any of its parents use wrap_content.
    var wrapContentFound = false
    var parentView: View? = this

    while (parentView != null) {
        if (parentView.layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            wrapContentFound = true
            break
        }
        parentView = parentView.parent as? View
        if (parentView == rootView) {
            break
        }
    }

    if (!wrapContentFound) {
        return measuredWidth
    }

    // Replace full text width with shrink wrapped text width.
    val shrinkWrappedWidth = measuredWidth - layout.width + ceil((0 until layout.lineCount).maxOfOrNull { layout.getLineMax(it) } ?: 0.0f).toInt()

    if (shrinkWrappedWidth >= measuredWidth) {
        return measuredWidth
    }

    // Apparently this somehow invalidates/triggers some layout stuff.
    // Without this right aligned text would get clipped, unless the TextView is wrapped inside a RelativeLayout.
    // Alternatively, `text=text` works too
    setPadding(paddingLeft - 1, paddingTop, paddingRight + 1, paddingBottom)
    setPadding(paddingLeft + 1, paddingTop, paddingRight - 1, paddingBottom)

    return shrinkWrappedWidth
}