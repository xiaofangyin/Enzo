package com.enzo.commonlib.utils.statusbar.ultimate.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import com.enzo.commonlib.utils.statusbar.ultimate.extension.getNavigationBarHeight
import com.enzo.commonlib.utils.statusbar.ultimate.extension.getStatusBarHeight

/**
 * @Author   : Zackratos
 * @Date     : 2020/11/29 0:33
 * @email    : 869649338@qq.com
 * @Describe :
 */
internal class RelativeLayoutCreator(private val relativeLayout: RelativeLayout, tag: Tag) : BaseCreator(tag) {

    override fun getStatusBarView(context: Context, fitWindow: Boolean): View {
        var statusBar: View? = relativeLayout.findViewWithTag(tag.statusBarViewTag())
        if (statusBar == null) {
            statusBar = View(context).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    context.getStatusBarHeight()
                ).apply { addRule(RelativeLayout.ALIGN_PARENT_TOP) }
            }
            statusBar.tag = tag.statusBarViewTag()
            relativeLayout.addView(statusBar)
        }
        statusBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                statusBar.layoutParams = (statusBar.layoutParams as RelativeLayout.LayoutParams)
                    .apply { topMargin = if (fitWindow) -context.getStatusBarHeight() else 0 }
                statusBar.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        return statusBar
    }

    override fun getNavigationBarView(context: Context, fitWindow: Boolean): View {
        var navigationBar: View? = relativeLayout.findViewWithTag(tag.navigationBarViewTag())
        if (navigationBar == null) {
            navigationBar = View(context).apply {
                layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    context.getNavigationBarHeight()
                ).apply { addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) }
            }
            navigationBar.tag = tag.navigationBarViewTag()
            relativeLayout.addView(navigationBar)
        }
        navigationBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                navigationBar.layoutParams = (navigationBar.layoutParams as RelativeLayout.LayoutParams)
                    .apply { bottomMargin = if (fitWindow) -context.getNavigationBarHeight() else 0 }
                navigationBar.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        return navigationBar
    }
}