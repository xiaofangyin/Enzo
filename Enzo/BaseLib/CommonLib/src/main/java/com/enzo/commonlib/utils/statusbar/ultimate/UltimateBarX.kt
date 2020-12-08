package com.enzo.commonlib.utils.statusbar.ultimate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.enzo.commonlib.utils.statusbar.ultimate.annotation.Type
import com.enzo.commonlib.utils.statusbar.ultimate.bean.BarConfig
import com.enzo.commonlib.utils.statusbar.ultimate.operator.Operator
import com.enzo.commonlib.utils.statusbar.ultimate.operator.OperatorProvider

class UltimateBarX {
    companion object {

        /**
         * use [com.zackratos.ultimatebarx.library.operator.Operator.applyStatusBar]
         */
        @Deprecated("")
        const val STATUS_BAR = 0

        /**
         * use [com.zackratos.ultimatebarx.library.operator.Operator.applyNavigationBar]
         */
        @Deprecated("")
        const val NAVIGATION_BAR = 1

        /**
         * use [with]
         * and [com.zackratos.ultimatebarx.library.operator.Operator.applyStatusBar]
         * and [com.zackratos.ultimatebarx.library.operator.Operator.applyNavigationBar]
         */
        @Deprecated("")
        @JvmStatic
        fun create(@Type type: Int): BarConfig.Builder = BarConfig.Builder.newDefaultBuilder(type)

        @JvmStatic
        fun with(activity: FragmentActivity): Operator = OperatorProvider.create(activity)

        @JvmStatic
        fun with(fragment: Fragment): Operator = OperatorProvider.create(fragment)

        @JvmStatic
        fun get(activity: FragmentActivity): Operator = OperatorProvider.get(activity)

        @JvmStatic
        fun get(fragment: Fragment): Operator = OperatorProvider.get(fragment)

        @JvmStatic
        fun getStatusBarConfig(activity: FragmentActivity): BarConfig = UltimateBarXManager.getInstance().getStatusBarConfig(activity)

        @JvmStatic
        fun getNavigationBarConfig(activity: FragmentActivity): BarConfig = UltimateBarXManager.getInstance().getNavigationBarConfig(activity)

        @JvmStatic
        fun getStatusBarConfig(fragment: Fragment): BarConfig = UltimateBarXManager.getInstance().getStatusBarConfig(fragment)

        @JvmStatic
        fun getNavigationBarConfig(fragment: Fragment): BarConfig = UltimateBarXManager.getInstance().getNavigationBarConfig(fragment)
    }

}