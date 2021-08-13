package com.enzo.commonlib.utils.statusbar.ultimate.extension

import android.text.TextUtils
import com.enzo.commonlib.utils.statusbar.ultimate.rom.EmuiRom
import com.enzo.commonlib.utils.statusbar.ultimate.rom.FuntouchRom
import com.enzo.commonlib.utils.statusbar.ultimate.rom.MiuiRom
import com.enzo.commonlib.utils.statusbar.ultimate.rom.OtherRom
import com.enzo.commonlib.utils.statusbar.ultimate.rom.Rom
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @Author   : zhangwenchao
 * @Date     : 2020/11/24  8:00 PM
 * @email    : zhangwenchao@soulapp.cn
 * @Describe :
 */

internal fun getRom(): Rom {
    if (!TextUtils.isEmpty(getProp(Rom.KEY_VERSION_MIUI)))
        return MiuiRom()
    if (!TextUtils.isEmpty(getProp(Rom.KEY_VERSION_EMUI)))
        return EmuiRom()
    if (!TextUtils.isEmpty(getProp(Rom.KEY_VERSION_VIVO)))
        return FuntouchRom()
    return OtherRom()
}

private fun getProp(name: String): String? {
    val line: String?
    var input: BufferedReader? = null
    try {
        val p = Runtime.getRuntime().exec("getprop $name")
        input = BufferedReader(InputStreamReader(p.inputStream), 1024)
        line = input.readLine()
        input.close()
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    } finally {
        if (input != null) {
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return line
}