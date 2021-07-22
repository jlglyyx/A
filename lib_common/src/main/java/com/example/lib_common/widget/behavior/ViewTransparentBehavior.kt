package com.example.lib_common.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView


/**
 * @Author Administrator
 * @ClassName ViewTransparentBehavior
 * @Description
 * @Date 2021/7/22 17:16
 */
class ViewTransparentBehavior : CoordinatorLayout.Behavior<ImageView> {

    private var deltaY = 0f

    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {
        return dependency is NestedScrollView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {

        if (deltaY === 0f) {
            deltaY = dependency.y - child.height
        }

        var dy: Float = dependency.y - child.height
        dy = if (dy < 0f) 0f else dy
        val alpha: Float = 1 - dy / deltaY
        child.alpha = alpha
        return true
    }
}