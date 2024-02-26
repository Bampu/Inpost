package pl.inpost.recruitmenttask.ui.shipment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.R
import kotlin.math.abs

class SwipeToDeleteCallback(
    context: Context,
    private val adapter: ShipmentListAdapter
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background: ColorDrawable = ColorDrawable(Color.RED)
    private val binIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.delete)!!
    private val iconMargin: Int = context.resources.getDimensionPixelSize(R.dimen.dp_20)
    private val swipeThreshold: Float = 0.10f

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.removeItem(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.setBounds(
            itemView.left + dX.toInt(),
            itemView.top,
            itemView.left,
            itemView.bottom
        )
        background.draw(c)

        if (abs(dX) > itemView.width * swipeThreshold) {
            val binIconTop = itemView.top + (itemHeight - binIcon.intrinsicHeight) / 2
            val binIconLeft = itemView.right - iconMargin - binIcon.intrinsicWidth
            val binIconRight = itemView.right - iconMargin
            val binIconBottom = binIconTop + binIcon.intrinsicHeight

            binIcon.setBounds(binIconLeft, binIconTop, binIconRight, binIconBottom)
            binIcon.draw(c)
        }

        viewHolder.itemView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Handle the touch up event
                    if (abs(dX) > itemView.width * swipeThreshold) {
                        // Perform item removal if swiped beyond the threshold
                        removeItem(viewHolder.adapterPosition, recyclerView)
                    } else {
                        // Animate back to the original position
                        val animator = ObjectAnimator.ofFloat(
                            itemView,
                            View.TRANSLATION_X,
                            dX,
                            0f
                        )
                        animator.duration = 300
                        animator.start()
                    }
                }
            }
            false
        }
    }

    private fun removeItem(position: Int, recyclerView: RecyclerView) {
        val animator = ObjectAnimator.ofFloat(
            recyclerView.getChildAt(position),
            View.TRANSLATION_X,
            0f,
            -recyclerView.width.toFloat()
        )

        animator.apply {
            duration = 300
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    adapter.removeItem(position)
                    notifyDataSetChanged()
                }
            })
            start()
        }
    }

    private fun canItemBeSwiped(position: Int): Boolean {
        return position in 0 until adapter.itemCount
                && adapter.getItemAtPosition(position)?.isArchived == true
    }

    private fun notifyDataSetChanged() {
        // Notify the adapter that the data set has changed after item removal
        adapter.notifyDataSetChanged()
    }
}