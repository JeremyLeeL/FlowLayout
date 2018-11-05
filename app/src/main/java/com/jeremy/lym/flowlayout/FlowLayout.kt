package com.jeremy.lym.flowlayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * Created by lym on 2018/11/5.
 * GitHub：https://github.com/JeremyLeeL
 */
class FlowLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : ViewGroup(context, attributeSet, defStyleAttr) {
    private val rectList = ArrayList<Rect>()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var widthUsed = paddingLeft
        var heightUsed = paddingTop
        var maxLineWidth = 0
        var maxLineHeight = 0
        for (i in 0 until childCount){
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (widthMode != MeasureSpec.UNSPECIFIED && widthUsed + child.measuredWidth > widthSize){ //换行
                heightUsed += maxLineHeight
                maxLineWidth = Math.max(widthUsed, maxLineWidth)
                widthUsed = paddingLeft
                maxLineHeight = 0
            }

            var rect: Rect
            if (rectList.size <= i){
                rect = Rect()
                rectList.add(rect)
            }else{
                rect = rectList[i]
            }
            rect.set(widthUsed, heightUsed, widthUsed + child.measuredWidth, heightUsed + child.measuredHeight)

            widthUsed += child.measuredWidth
            maxLineHeight = Math.max(child.measuredHeight, maxLineHeight)
        }
        val width = if (widthMode == MeasureSpec.EXACTLY) widthSize else maxLineWidth
        val height = if (heightMode == MeasureSpec.EXACTLY) heightSize else heightUsed + maxLineHeight
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount){
            val rect = rectList[i]
            getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}