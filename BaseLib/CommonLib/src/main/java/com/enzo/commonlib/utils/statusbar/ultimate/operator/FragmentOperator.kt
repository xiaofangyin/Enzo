package com.enzo.commonlib.utils.statusbar.ultimate.operator

import android.os.Build
import androidx.fragment.app.Fragment
import com.enzo.commonlib.utils.statusbar.ultimate.bean.BarConfig
import com.enzo.commonlib.utils.statusbar.ultimate.extension.*
import com.enzo.commonlib.utils.statusbar.ultimate.extension.barLight
import com.enzo.commonlib.utils.statusbar.ultimate.extension.ultimateBarXInitialization
import com.enzo.commonlib.utils.statusbar.ultimate.extension.updateNavigationBar
import com.enzo.commonlib.utils.statusbar.ultimate.extension.updateStatusBar

/**
 * @Author   : Zackratos
 * @Date     : 2020/11/28 18:31
 * @email    : 869649338@qq.com
 * @Describe :
 */
internal class FragmentOperator private constructor(val fragment: Fragment, config: BarConfig): BaseOperator(config) {

    companion object {
        internal fun newInstance(fragment: Fragment, config: BarConfig = BarConfig.newInstance()) = FragmentOperator(fragment, config)
    }

    override fun applyStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        fragment.requireActivity().ultimateBarXInitialization()
        fragment.ultimateBarXInitialization()
        val navLight = manager.getNavigationBarConfig(fragment).light
        fragment.requireActivity().barLight(config.light, navLight)
        fragment.updateStatusBar(config)
        fragment.requireActivity().defaultNavigationBar()
        fragment.addObserver()
        fragment.requireActivity().addObserver()
    }

    override fun applyNavigationBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        fragment.requireActivity().ultimateBarXInitialization()
        fragment.ultimateBarXInitialization()
        val staLight = manager.getStatusBarConfig(fragment).light
        fragment.requireActivity().barLight(staLight, config.light)
        fragment.updateNavigationBar(config)
        fragment.requireActivity().defaultStatusBar()
        fragment.addObserver()
        fragment.requireActivity().addObserver()
    }

}