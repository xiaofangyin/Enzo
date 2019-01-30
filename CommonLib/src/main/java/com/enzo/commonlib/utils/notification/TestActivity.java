package com.enzo.commonlib.utils.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import com.enzo.commonlib.R;

/**
 * 文 件 名: TestActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/27
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TestActivity extends Activity {

    /**
     * 清除所有的notification通知
     */
    private void cancelAllNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.clearNotification();
    }

    /**
     * 发送最简单的通知
     */
    private void sendNotification1() {
        //这三个属性是必须要的，否则异常
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(1, "这个是标题", "这个是内容", R.mipmap.ic_launcher);
    }

    /**
     * 3.在Activity中发送通知2，添加action
     */
    private void sendNotification2() {
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 定义Notification的各种属性
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setContentIntent(resultPendingIntent)
                .sendNotificationCompat(2, "这个是标题2", "这个是内容2", R.mipmap.ic_launcher);
    }

    /**
     * 4.在Activity中发送通知3，设置通知栏左右滑动不删除
     */
    private void sendNotification3() {
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 3);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 3, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //发送pendingIntent

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置内容点击处理intent
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("来通知消息啦")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setVibrate(vibrate)
                //必须设置的属性，发送通知
                .sendNotification(3, "这个是标题3", "这个是内容3", R.mipmap.ic_launcher);
    }

    /**
     * 5.在Activity中发送自定义布局通知
     */
    private void sendNotification4() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.setContent(getRemoteViews());
        Notification notification = notificationUtils.getNotification("这个是标题4", "这个是内容4", R.mipmap.ic_launcher);
        notificationUtils.getManager().notify(4, notification);
    }

    private RemoteViews getRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_mobile_play);
        // 设置 点击通知栏的上一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getActivityPendingIntent(11));
        // 设置 点击通知栏的下一首按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getActivityPendingIntent(12));
        // 设置 点击通知栏的播放暂停按钮时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.btn_start, getActivityPendingIntent(13));
        // 设置 点击通知栏的根容器时要执行的意图
        remoteViews.setOnClickPendingIntent(R.id.ll_root, getActivityPendingIntent(14));
        remoteViews.setTextViewText(R.id.tv_title, "标题");     // 设置通知栏上标题
        remoteViews.setTextViewText(R.id.tv_artist, "艺术家");   // 设置通知栏上艺术家
        return remoteViews;
    }

    /**
     * 获取一个Activity类型的PendingIntent对象
     */
    private PendingIntent getActivityPendingIntent(int what) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        intent.putExtra("what", what);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, what, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    /**
     * 8.在Activity中连续发送3条通知
     */
    private void sendNotification8() {
        for (int a = 0; a < 3; a++) {
            //这三个属性是必须要的，否则异常
            NotificationUtils notificationUtils = new NotificationUtils(this);
            notificationUtils.sendNotification(8, "这个是标题8", "这个是内容8", R.mipmap.ic_launcher);

        }
    }

    /**
     * 9.在Activity中发通知，设置通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
     */
    private void sendNotification9() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置状态栏的标题
                .setTicker("有新消息呢9")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_NO_CLEAR)
                //必须设置的属性，发送通知
                .sendNotification(9, "有新消息呢9", "这个是标题9", R.mipmap.ic_launcher);
    }

    /**
     * 10.在Activity中发通知，设置用户单击通知后自动消失
     */
    private void sendNotification10() {

        //处理点击Notification的逻辑
        //创建intent
        Intent resultIntent = new Intent(this, TestActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
        resultIntent.putExtra("what", 10);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 10, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                .setContentIntent(resultPendingIntent)
                //设置状态栏的标题
                .setTicker("有新消息呢10")
                //设置自定义view通知栏布局
                .setContent(getRemoteViews())
                .setDefaults(Notification.DEFAULT_ALL)
                //设置sound
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //自定义震动效果
                .setFlags(Notification.FLAG_AUTO_CANCEL)
                //必须设置的属性，发送通知
                .sendNotification(10, "有新消息呢10", "这个是标题10", R.mipmap.ic_launcher);
    }

    /**
     * 11.设置铃声效果
     */
    private void sendNotification11() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setOngoing(false)
                .setTicker("来通知消息啦")
                .setContent(getRemoteViews())
                //.setSound(Uri.parse("android.resource://com.yc.cn.ycnotification/" + R.raw.hah))
                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "2"))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .sendNotification(11, "我是伴有铃声效果的通知11", "美妙么?安静听~11", R.mipmap.ic_launcher);
    }

    /**
     * 12.设置震动效果
     */
    private void sendNotification12() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        long[] vibrate = new long[]{0, 500, 1000, 1500};
//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("我是伴有震动效果的通知")
//                .setContentText("颤抖吧,逗比哈哈哈哈哈~")
//                //使用系统默认的震动参数,会与自定义的冲突
//                //.setDefaults(Notification.DEFAULT_VIBRATE)
//                //自定义震动效果
//                .setVibrate(vibrate);
//        //另一种设置震动的方法
//        //Notification notify = builder.build();
//        //调用系统默认震动
//        //notify.defaults = Notification.DEFAULT_VIBRATE;
//        //调用自己设置的震动
//        //notify.vibrate = vibrate;
//        //mManager.notify(3,notify);
//        mNotificationManager.notify(12, builder.build());

        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(vibrate)
                .sendNotification(12, "我是伴有震动效果的通知", "颤抖吧,逗比哈哈哈哈哈~", R.mipmap.ic_launcher);

    }

    /**
     * 13.通知只执行一次,与默认的效果一样
     */
    private void sendNotification13() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setDefaults(Notification.DEFAULT_ALL)
                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(13, "仔细看,我就执行一遍", "好了,已经一遍了~", R.mipmap.ic_launcher);

    }

    /**
     * 14.带有进度条的通知栏
     */
    private void sendNotification14() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                .setDefaults(Notification.DEFAULT_ALL)

                .setFlags(Notification.FLAG_ONLY_ALERT_ONCE)
                .sendNotification(14, "显示进度条14", "显示进度条内容，自定定义", R.mipmap.ic_launcher);
    }

    /**
     * 15.Android 8.0 适配问题
     */
    private void sendNotification15() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification(15, "新消息来了", "周末到了，不用上班了", R.mipmap.ic_launcher);
    }
}
