package com.enzo.commonlib.utils.statusbar.ultimate.rom

import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

/**
 * @Author   : zhangwenchao
 * @Date     : 2020/11/24  8:09 PM
 * @email    : zhangwenchao@soulapp.cn
 * @Describe : 小米 rom
 */
internal class MiuiRom: Rom {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun navigationBarExist(activity: FragmentActivity): Boolean {
        return Settings.Global.getInt(activity.contentResolver, "force_fsg_nav_bar", 0) == 0
    }
}