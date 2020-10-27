package com.enzo.module_d.utils.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.enzo.module_d.ui.activity.MDMatisseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookHelper {
    private static final String TAG = "xfy";

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookIActivityManager() {
        try {
            Field gIActivityManagerSingleton;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> activityManager = Class.forName("android.app.ActivityManager");
                gIActivityManagerSingleton = activityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManager = Class.forName("android.app.ActivityManagerNative");
                //拿到 Singleton<IActivityManager> gDefault
                gIActivityManagerSingleton = activityManager.getDeclaredField("gDefault");
            }

            gIActivityManagerSingleton.setAccessible(true);
            //Singlon<IActivityManager>
            Object gIActivityManager = gIActivityManagerSingleton.get(null);

            //拿到Singleton的Class对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //获取IActivityManager对象
            final Object rawIActivityManager = mInstanceField.get(gIActivityManager);

            //进行动态代理
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            //生产IActivityManager的代理对象
            Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{iActivityManagerInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.i(TAG, "invoke: method " + method.getName());
                    if ("startActivity".equals(method.getName())) {
                        Log.i(TAG, "准备启动activity");
                        for (Object obj : args) {
                            Log.i(TAG, "invoke: obj= " + obj);
                        }

                        //偷梁换柱 把Target 换成我们的Stub,欺骗AMS的权限验证
                        //拿到原始的Intent,然后保存
                        Intent raw = null;
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                raw = (Intent) args[i];
                                index = i;
                                break;
                            }
                        }
                        Log.i(TAG, "invoke: raw= " + raw);

                        //替换成Stub
                        Intent newIntent = new Intent();
                        String stubPackage = "com.enzo.module_d";
                        newIntent.setComponent(new ComponentName(stubPackage, MDMatisseActivity.class.getName()));
                        //把这个newIntent放回到args,达到了一个欺骗AMS的目的
                        newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
                        args[index] = newIntent;

                    }
                    return method.invoke(rawIActivityManager, args);
                }
            });

            //把我们的代理对象融入到framework
            mInstanceField.set(gIActivityManager, proxy);
        } catch (Exception e) {
            Log.e(TAG, "hookIActivityManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void hookHandler() {
        //TODO:
        try {
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = atClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            //ActivityThread 一个app进程 只有一个，获取它的mH
            Field mHField = atClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            //获取mCallback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    Log.i(TAG, "handleMessage: " + msg.what);
                    switch (msg.what) {
                        case 100: {
                            // final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
//                            static final class ActivityClientRecord {
//                                IBinder token;
//                                int ident;
//                                Intent intent;//hook 恢复
                            //恢复真身
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent intent = (Intent) intentField.get(msg.obj);
                                Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
//                                intent.setComponent(targetIntent.getComponent());
                                intentField.set(msg.obj,targetIntent);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                        case 159: {
                            Object obj = msg.obj;
                            Log.i(TAG, "handleMessage: obj=" + obj);
                            try {
                                Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksField.get(obj);
                                Log.i(TAG, "handleMessage: mActivityCallbacks= " + mActivityCallbacks);
                                if (mActivityCallbacks.size() > 0) {
                                    Log.i(TAG, "handleMessage: size= " + mActivityCallbacks.size());
                                    String className = "android.app.servertransaction.LaunchActivityItem";
                                    if (mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)) {
                                        Object object = mActivityCallbacks.get(0);
                                        Field intentField = object.getClass().getDeclaredField("mIntent");
                                        intentField.setAccessible(true);
                                        Intent intent = (Intent) intentField.get(object);
                                        Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
//                                        intent.setComponent(targetIntent.getComponent());
                                        intentField.set(object,targetIntent);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    }
                    mH.handleMessage(msg);
                    return true;
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "hookHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
