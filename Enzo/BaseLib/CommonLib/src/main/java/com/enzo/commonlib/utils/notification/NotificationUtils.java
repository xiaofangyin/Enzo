package com.enzo.commonlib.utils.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * blog  : https://www.jianshu.com/p/514eb6193a06
 */
public class NotificationUtils extends ContextWrapper {

    private String CHANNEL_ID = "";
    private String CHANNEL_NAME = "";
    private NotificationManager mManager;
    private Options options;

    public NotificationUtils(Context base, String channel_id, String channel_name) {
        super(base);
        this.CHANNEL_ID = channel_id;
        this.CHANNEL_NAME = channel_name;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            createNotificationChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //第一个参数：channel_id
        //第二个参数：channel_name
        //第三个参数：设置通知重要性级别
        //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
        //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过请勿打扰模式
        channel.enableLights(true);//闪光灯
        channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
        channel.setLightColor(Color.RED);//闪关灯的灯光颜色
        channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(false);//是否允许震动
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
        //channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
        channel.shouldShowLights();//是否会有灯光
        getManager().createNotificationChannel(channel);
    }

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return NotificationManager对象
     */
    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 清空所有的通知
     */
    public void clearNotification() {
        getManager().cancelAll();
    }

    /**
     * 清空指定通知
     */
    public void cancelNotify(int id) {
        getManager().cancel(id);
    }

    /**
     * 设置配置参数
     */
    public void setOptions(Options o) {
        this.options = o;
    }

    /**
     * 调用该方法可以发送通知
     */
    public void sendNotification(int notifyId, String title, String content, int icon) {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            //通知用到NotificationCompat()这个V4库中的方法。但是在实际使用时发现书上的代码已经过时并且Android8.0已经不支持这种写法
            Notification.Builder builder = getChannelNotification(title, content, icon);
            notification = builder.build();
        } else {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
            notification = builder.build();
        }
        if (options != null) {
            if (options.flags != null && options.flags.length > 0) {
                for (int a = 0; a < options.flags.length; a++) {
                    notification.flags |= options.flags[a];
                }
            }
        }
        getManager().notify(notifyId, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String title, String content, int icon) {
        Notification.Builder notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID);
        notification.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setAutoCancel(true);
        if (options != null) {
            //让通知左右滑的时候是否可以取消通知
            notification.setOngoing(options.ongoing);
            //设置优先级
            notification.setPriority(options.priority);
            //是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
            notification.setOnlyAlertOnce(options.onlyAlertOnce);
            if (options.progress > 0 && options.progress <= 100) {
                //一种是有进度刻度的（false）,一种是循环流动的（true）
                //设置为false，表示刻度，设置为true，表示流动
                notification.setProgress(100, options.progress, false);
            } else {
                //0,0,false,可以将进度条隐藏
                notification.setProgress(0, 0, false);
            }
            if (options.remoteViews != null) {
                //设置自定义view通知栏
                notification.setContent(options.remoteViews);
            }
            if (options.intent != null) {
                notification.setContentIntent(options.intent);
            }
            if (!TextUtils.isEmpty(options.ticker)) {
                //设置状态栏的标题
                notification.setTicker(options.ticker);
            }
            if (options.when != 0) {
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
                notification.setWhen(options.when);
            }
            if (options.sound != null) {
                //设置sound
                notification.setSound(options.sound);
            }
            if (options.defaults != 0) {
                //设置默认的提示音
                notification.setDefaults(options.defaults);
            }
            if (options.pattern != null) {
                //自定义震动效果
                notification.setVibrate(options.pattern);
            }
        }
        return notification;
    }

    private NotificationCompat.Builder getNotificationCompat(String title, String content, int icon) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        notification.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setAutoCancel(true);
        if (options != null) {
            //让通知左右滑的时候是否可以取消通知
            notification.setOngoing(options.ongoing);
            //设置优先级
            notification.setPriority(options.priority);
            //是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
            notification.setOnlyAlertOnce(options.onlyAlertOnce);
            if (options.progress > 0 && options.progress <= 100) {
                //一种是有进度刻度的（false）,一种是循环流动的（true）
                //设置为false，表示刻度，设置为true，表示流动
                notification.setProgress(100, options.progress, false);
            } else {
                //0,0,false,可以将进度条隐藏
                notification.setProgress(0, 0, false);
            }
            if (options.remoteViews != null) {
                //设置自定义view通知栏
                notification.setContent(options.remoteViews);
            }
            if (options.intent != null) {
                notification.setContentIntent(options.intent);
            }
            if (!TextUtils.isEmpty(options.ticker)) {
                //设置状态栏的标题
                notification.setTicker(options.ticker);
            }
            if (options.when != 0) {
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
                notification.setWhen(options.when);
            }
            if (options.sound != null) {
                //设置sound
                notification.setSound(options.sound);
            }
            if (options.defaults != 0) {
                //设置默认的提示音
                notification.setDefaults(options.defaults);
            }
            if (options.pattern != null) {
                //自定义震动效果
                notification.setVibrate(options.pattern);
            }
        }
        return notification;
    }

    public static class Options {

        private boolean ongoing = false;
        private RemoteViews remoteViews = null;
        private PendingIntent intent = null;
        private int progress = -1;
        private String ticker = "";
        private int priority = Notification.PRIORITY_DEFAULT;
        private boolean onlyAlertOnce = false;
        private long when = 0;
        private Uri sound = null;
        private int defaults = 0;
        private long[] pattern = null;
        private int[] flags;

        /**
         * 让通知左右滑的时候是否可以取消通知
         *
         * @param ongoing 是否可以取消通知
         */
        public Options setOngoing(boolean ongoing) {
            this.ongoing = ongoing;
            return this;
        }

        /**
         * 设置自定义view通知栏布局
         */
        public Options setContent(RemoteViews remoteViews) {
            this.remoteViews = remoteViews;
            return this;
        }

        /**
         * 设置内容点击
         */
        public Options setContentIntent(PendingIntent intent) {
            this.intent = intent;
            return this;
        }

        /**
         * 通知进度
         */
        public Options setProgress(int progress) {
            this.progress = progress;
            return this;
        }

        /**
         * 设置状态栏的标题
         */
        public Options setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        /**
         * 设置优先级
         * 注意：
         * Android 8.0以及上，在 NotificationChannel 的构造函数中指定，总共要五个级别；
         * Android 7.1（API 25）及以下的设备，还得调用NotificationCompat 的 setPriority方法来设置
         *
         * @param priority 优先级，默认是Notification.PRIORITY_DEFAULT
         */
        public Options setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
         *
         * @param onlyAlertOnce 是否只提示一次，默认是false
         */
        public Options setOnlyAlertOnce(boolean onlyAlertOnce) {
            this.onlyAlertOnce = onlyAlertOnce;
            return this;
        }

        /**
         * 设置通知时间，默认为系统发出通知的时间，通常不用设置
         *
         * @param when when
         */
        public Options setWhen(long when) {
            this.when = when;
            return this;
        }

        /**
         * 设置sound
         *
         * @param sound sound
         */
        public Options setSound(Uri sound) {
            this.sound = sound;
            return this;
        }

        /**
         * 设置默认的提示音
         *
         * @param defaults defaults
         */
        public Options setDefaults(int defaults) {
            this.defaults = defaults;
            return this;
        }

        /**
         * 自定义震动效果
         *
         * @param pattern pattern
         */
        public Options setVibrate(long[] pattern) {
            this.pattern = pattern;
            return this;
        }

        /**
         * 设置flag标签
         *
         * @param flags flags
         */
        public Options setFlags(int... flags) {
            this.flags = flags;
            return this;
        }
    }

}
