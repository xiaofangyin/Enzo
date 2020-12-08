package com.enzo.commonlib.utils.statusbar.ultimate.operator

import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.enzo.commonlib.utils.statusbar.ultimate.bean.BarConfig
import com.enzo.commonlib.utils.statusbar.ultimate.extension.*
import com.enzo.commonlib.utils.statusbar.ultimate.extension.barLight
import com.enzo.commonlib.utils.statusbar.ultimate.extension.ultimateBarXInitialization
import com.enzo.commonlib.utils.statusbar.ultimate.extension.updateNavigationBar
import com.enzo.commonlib.utils.statusbar.ultimate.extension.updateStatusBar

/**
 * @Author   : Zackratos
 * @Date     : 2020/11/27 0:34
 * @email    : 869649338@qq.com
 * @Describe :
 */
internal class ActivityOperator private constructor(val activity: FragmentActivity, config: BarConfig): BaseOperator(config) {

    companion object {
        internal fun newInstance(activity: FragmentActivity, config: BarConfig = BarConfig.newInstance()) = ActivityOperator(activity, config)
    }

    override fun applyStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        activity.ultimateBarXInitialization()
        val navConfig = manager.getNavigationBarConfig(activity)
        val navLight = manager.getNavigationBarConfig(activity).light
        activity.barLight(config.light, navLight)
        activity.updateStatusBar(config)
        activity.defaultNavigationBar()
        activity.addObserver()
    }

    override fun applyNavigationBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        activity.ultimateBarXInitialization()
        val staLight = manager.getStatusBarConfig(activity).light
        activity.barLight(staLight, config.light)
        activity.updateNavigationBar(config)
        activity.defaultStatusBar()
        activity.addObserver()
    }

}