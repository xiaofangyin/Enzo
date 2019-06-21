package com.enzo.commonlib.widget.timeclock;

import android.graphics.Color;

/**
 * 文 件 名: SHTimePickerUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/5/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SHTimePickerUtils {

    //绿色
    private static final int COLOR_GREEN_START_AM_R = 111;
    private static final int COLOR_GREEN_END_AM_R = 9;
    private static final int COLOR_GREEN_START_AM_G = 251;
    private static final int COLOR_GREEN_END_AM_G = 154;
    private static final int COLOR_GREEN_START_AM_B = 158;
    private static final int COLOR_GREEN_END_AM_B = 105;

    private static final int COLOR_GREEN_START_PM_R = 80;
    private static final int COLOR_GREEN_END_PM_R = 10;
    private static final int COLOR_GREEN_START_PM_G = 215;
    private static final int COLOR_GREEN_END_PM_G = 112;
    private static final int COLOR_GREEN_START_PM_B = 125;
    private static final int COLOR_GREEN_END_PM_B = 77;

    //蓝色
    private static final int COLOR_BLUE_START_AM_R = 102;
    private static final int COLOR_BLUE_END_AM_R = 14;
    private static final int COLOR_BLUE_START_AM_G = 215;
    private static final int COLOR_BLUE_END_AM_G = 135;
    private static final int COLOR_BLUE_START_AM_B = 255;
    private static final int COLOR_BLUE_END_AM_B = 187;

    private static final int COLOR_BLUE_START_PM_R = 41;
    private static final int COLOR_BLUE_END_PM_R = 42;
    private static final int COLOR_BLUE_START_PM_G = 150;
    private static final int COLOR_BLUE_END_PM_G = 85;
    private static final int COLOR_BLUE_START_PM_B = 194;
    private static final int COLOR_BLUE_END_PM_B = 103;

    //黄色
    private static final int COLOR_YELLOW_START_AM_R = 250;
    private static final int COLOR_YELLOW_END_AM_R = 242;
    private static final int COLOR_YELLOW_START_AM_G = 217;
    private static final int COLOR_YELLOW_END_AM_G = 167;
    private static final int COLOR_YELLOW_START_AM_B = 97;
    private static final int COLOR_YELLOW_END_AM_B = 42;

    private static final int COLOR_YELLOW_START_PM_R = 165;
    private static final int COLOR_YELLOW_END_PM_R = 175;
    private static final int COLOR_YELLOW_START_PM_G = 129;
    private static final int COLOR_YELLOW_END_PM_G = 91;
    private static final int COLOR_YELLOW_START_PM_B = 20;
    private static final int COLOR_YELLOW_END_PM_B = 8;

    //紫色
    private static final int COLOR_PURPLE_START_AM_R = 135;
    private static final int COLOR_PURPLE_END_AM_R = 103;
    private static final int COLOR_PURPLE_START_AM_G = 136;
    private static final int COLOR_PURPLE_END_AM_G = 59;
    private static final int COLOR_PURPLE_START_AM_B = 238;
    private static final int COLOR_PURPLE_END_AM_B = 225;

    private static final int COLOR_PURPLE_START_PM_R = 96;
    private static final int COLOR_PURPLE_END_PM_R = 54;
    private static final int COLOR_PURPLE_START_PM_G = 97;
    private static final int COLOR_PURPLE_END_PM_G = 28;
    private static final int COLOR_PURPLE_START_PM_B = 231;
    private static final int COLOR_PURPLE_END_PM_B = 128;

    //粉色
    private static final int COLOR_PINK_START_AM_R = 243;
    private static final int COLOR_PINK_END_AM_R = 218;
    private static final int COLOR_PINK_START_AM_G = 109;
    private static final int COLOR_PINK_END_AM_G = 29;
    private static final int COLOR_PINK_START_AM_B = 192;
    private static final int COLOR_PINK_END_AM_B = 183;

    private static final int COLOR_PINK_START_PM_R = 184;
    private static final int COLOR_PINK_END_PM_R = 115;
    private static final int COLOR_PINK_START_PM_G = 38;
    private static final int COLOR_PINK_END_PM_G = 8;
    private static final int COLOR_PINK_START_PM_B = 167;
    private static final int COLOR_PINK_END_PM_B = 102;

    //白色
    private static final int COLOR_WHITE_START_AM_R = 241;
    private static final int COLOR_WHITE_END_AM_R = 183;
    private static final int COLOR_WHITE_START_AM_G = 241;
    private static final int COLOR_WHITE_END_AM_G = 183;
    private static final int COLOR_WHITE_START_AM_B = 241;
    private static final int COLOR_WHITE_END_AM_B = 183;

    static int getPickerColor(int position, float total, boolean am, String colorType) {
        int colorR;
        int colorG;
        int colorB;
        switch (colorType) {
            case SHScheduleBean.SCHEDULE_HOME_MODE: {//居家守护
                if (am) {
                    colorR = (int) ((COLOR_GREEN_END_AM_R - COLOR_GREEN_START_AM_R) / total * position + COLOR_GREEN_START_AM_R);
                    colorG = (int) ((COLOR_GREEN_END_AM_G - COLOR_GREEN_START_AM_G) / total * position + COLOR_GREEN_START_AM_G);
                    colorB = (int) ((COLOR_GREEN_END_AM_B - COLOR_GREEN_START_AM_B) / total * position + COLOR_GREEN_START_AM_B);
                } else {
                    colorR = (int) ((COLOR_GREEN_END_PM_R - COLOR_GREEN_START_PM_R) / total * position + COLOR_GREEN_START_PM_R);
                    colorG = (int) ((COLOR_GREEN_END_PM_G - COLOR_GREEN_START_PM_G) / total * position + COLOR_GREEN_START_PM_G);
                    colorB = (int) ((COLOR_GREEN_END_PM_B - COLOR_GREEN_START_PM_B) / total * position + COLOR_GREEN_START_PM_B);
                }
                return Color.rgb(colorR, colorG, colorB);
            }
            case SHScheduleBean.SCHEDULE_INFRARED_NIGHTLIGHT: {//人体感应灯
                if (am) {
                    colorR = (int) ((COLOR_BLUE_END_AM_R - COLOR_BLUE_START_AM_R) / total * position + COLOR_BLUE_START_AM_R);
                    colorG = (int) ((COLOR_BLUE_END_AM_G - COLOR_BLUE_START_AM_G) / total * position + COLOR_BLUE_START_AM_G);
                    colorB = (int) ((COLOR_BLUE_END_AM_B - COLOR_BLUE_START_AM_B) / total * position + COLOR_BLUE_START_AM_B);
                } else {
                    colorR = (int) ((COLOR_BLUE_END_PM_R - COLOR_BLUE_START_PM_R) / total * position + COLOR_BLUE_START_PM_R);
                    colorG = (int) ((COLOR_BLUE_END_PM_G - COLOR_BLUE_START_PM_G) / total * position + COLOR_BLUE_START_PM_G);
                    colorB = (int) ((COLOR_BLUE_END_PM_B - COLOR_BLUE_START_PM_B) / total * position + COLOR_BLUE_START_PM_B);
                }
                return Color.rgb(colorR, colorG, colorB);
            }
            case SHScheduleBean.SCHEDULE_OUTSIDE_MODE: {//外出警戒
                if (am) {
                    colorR = (int) ((COLOR_YELLOW_END_AM_R - COLOR_YELLOW_START_AM_R) / total * position + COLOR_YELLOW_START_AM_R);
                    colorG = (int) ((COLOR_YELLOW_END_AM_G - COLOR_YELLOW_START_AM_G) / total * position + COLOR_YELLOW_START_AM_G);
                    colorB = (int) ((COLOR_YELLOW_END_AM_B - COLOR_YELLOW_START_AM_B) / total * position + COLOR_YELLOW_START_AM_B);
                } else {
                    colorR = (int) ((COLOR_YELLOW_END_PM_R - COLOR_YELLOW_START_PM_R) / total * position + COLOR_YELLOW_START_PM_R);
                    colorG = (int) ((COLOR_YELLOW_END_PM_G - COLOR_YELLOW_START_PM_G) / total * position + COLOR_YELLOW_START_PM_G);
                    colorB = (int) ((COLOR_YELLOW_END_PM_B - COLOR_YELLOW_START_PM_B) / total * position + COLOR_YELLOW_START_PM_B);
                }
                return Color.rgb(colorR, colorG, colorB);
            }
            case SHScheduleBean.SCHEDULE_LIGHT_SENSOR_NIGHTLIGHT: {//智能小夜灯
                if (am) {
                    colorR = (int) ((COLOR_PURPLE_END_AM_R - COLOR_PURPLE_START_AM_R) / total * position + COLOR_PURPLE_START_AM_R);
                    colorG = (int) ((COLOR_PURPLE_END_AM_G - COLOR_PURPLE_START_AM_G) / total * position + COLOR_PURPLE_START_AM_G);
                    colorB = (int) ((COLOR_PURPLE_END_AM_B - COLOR_PURPLE_START_AM_B) / total * position + COLOR_PURPLE_START_AM_B);
                } else {
                    colorR = (int) ((COLOR_PURPLE_END_PM_R - COLOR_PURPLE_START_PM_R) / total * position + COLOR_PURPLE_START_PM_R);
                    colorG = (int) ((COLOR_PURPLE_END_PM_G - COLOR_PURPLE_START_PM_G) / total * position + COLOR_PURPLE_START_PM_G);
                    colorB = (int) ((COLOR_PURPLE_END_PM_B - COLOR_PURPLE_START_PM_B) / total * position + COLOR_PURPLE_START_PM_B);
                }
                return Color.rgb(colorR, colorG, colorB);
            }
            case SHScheduleBean.SCHEDULE_NORMAL_NIGHTLIGHT: {//遥控灯
                if (am) {
                    colorR = (int) ((COLOR_PINK_END_AM_R - COLOR_PINK_START_AM_R) / total * position + COLOR_PINK_START_AM_R);
                    colorG = (int) ((COLOR_PINK_END_AM_G - COLOR_PINK_START_AM_G) / total * position + COLOR_PINK_START_AM_G);
                    colorB = (int) ((COLOR_PINK_END_AM_B - COLOR_PINK_START_AM_B) / total * position + COLOR_PINK_START_AM_B);
                } else {
                    colorR = (int) ((COLOR_PINK_END_PM_R - COLOR_PINK_START_PM_R) / total * position + COLOR_PINK_START_PM_R);
                    colorG = (int) ((COLOR_PINK_END_PM_G - COLOR_PINK_START_PM_G) / total * position + COLOR_PINK_START_PM_G);
                    colorB = (int) ((COLOR_PINK_END_PM_B - COLOR_PINK_START_PM_B) / total * position + COLOR_PINK_START_PM_B);
                }
                return Color.rgb(colorR, colorG, colorB);
            }
            default: {//默认值
                colorR = (int) ((COLOR_WHITE_END_AM_R - COLOR_WHITE_START_AM_R) / total * position + COLOR_WHITE_START_AM_R);
                colorG = (int) ((COLOR_WHITE_END_AM_G - COLOR_WHITE_START_AM_G) / total * position + COLOR_WHITE_START_AM_G);
                colorB = (int) ((COLOR_WHITE_END_AM_B - COLOR_WHITE_START_AM_B) / total * position + COLOR_WHITE_START_AM_B);
                return Color.rgb(colorR, colorG, colorB);
            }
        }
    }
}
