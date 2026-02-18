package com.example.shrinkwrap

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import shrinkwrap.xml.measureShrinkWrappedWidth

/**
 * This is an example of a `TextView` subclass (Kotlin) that does not subclass from `ShrinkWrapTextView`.
 * Shrink wrapping is still possible by overriding `onMeasure()` like shown here.
 * This also works for Button subclasses (see below).
 * */
class MyKotlinTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // Call super first!
        setMeasuredDimension(measureShrinkWrappedWidth(), measuredHeight)
    }

}

/**
 * This is an example of a `Button` subclass (Kotlin) that does not subclass from `ShrinkWrapButton`.
 * Shrink wrapping is still possible by overriding `onMeasure()` like shown here.
 * */
class MyKotlinButton(context: Context, attrs: AttributeSet?) : AppCompatButton(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) // Call super first!
        setMeasuredDimension(measureShrinkWrappedWidth(), measuredHeight)
    }

}