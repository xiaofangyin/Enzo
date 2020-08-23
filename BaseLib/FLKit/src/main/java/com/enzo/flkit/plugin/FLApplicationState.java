package com.enzo.flkit.plugin;

/**
 * app状态（前台，中间态，后台，主进程被杀死）
 */

public enum FLApplicationState {
    Foreground, Intermediate, Background, Dead
}
