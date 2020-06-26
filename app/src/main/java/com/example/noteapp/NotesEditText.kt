package com.example.noteapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class NotesEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val mRect = Rect()
    private val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1F
        color = -0Xc9c9c9
    }

    override fun onDraw(canvas: Canvas?) {

        //height of the view
        // Java -> int height = ((view)this.parent).getHeight()
        val height = (this.parent as View).height

        val lineHeight = getLineHeight()
        val numLines = height / lineHeight

        val rects = mRect
        val paints = mPaint
        var baseline = getLineBounds(0, rects)

        //draws lines
        for (i in 0 .. numLines) {
            canvas?.drawLine(rects.left.toFloat(), baseline + 1.toFloat(), rects.right.toFloat(),baseline + 1.toFloat(), paints)
            baseline += lineHeight
            //baseline -> relate antara text dengan line paint
        }

        super.onDraw(canvas)
    }


}
