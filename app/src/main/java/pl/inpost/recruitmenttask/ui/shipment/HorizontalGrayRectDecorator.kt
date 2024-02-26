package pl.inpost.recruitmenttask.ui.shipment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HorizontalGrayRectDecorator(context: Context, private val spacing: Int) : RecyclerView.ItemDecoration() {

    private val grayPaint: Paint = Paint()

    init {
        grayPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_dark)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val nextChild = parent.getChildAt(i + 1)

            val left = child.right
            val right = nextChild.left
            val top = child.top + spacing
            val bottom = child.bottom - spacing

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), grayPaint)
        }
    }
}