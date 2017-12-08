package com.ifenglian.commonlib.utils.updateversion;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast工具箱  可防止用户多次点击之后 显示消息的时长太长  利用handler消息机制
 */
public class ToastUtils {
	
	private static String oldMsg;  
    protected static Toast toast   = null;  
    private static long oneTime=0;  
    private static long twoTime=0;  
    private static Handler handler = new Handler();
    private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			toast.cancel();
			toast = null;
		}
	};
    /**
     * 吐出一个显示时间较短的提示
     * @param context 上下文
     * @param s  文本内容
     */
    public static void showToast(Context context, String s){      
        if(toast==null){   
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);  
            toast.show();  
            oneTime=System.currentTimeMillis();  
        }else{
            twoTime=System.currentTimeMillis();  
            if(s.equals(oldMsg)){  
                if(twoTime-oneTime>Toast.LENGTH_SHORT){  
                    toast.show();  
                }
            }else{  
                oldMsg = s;  
                toast.setText(s);  
                toast.show();  
            }
        }
        oneTime=twoTime;  
    }
    
    
    
    


	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param resId 显示内容资源ID
	 */
	public static final void showToast(Context context, int resId){
		showToast(context, context.getString(resId));
	}

	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public static final void toastL(Context context, int formatResId, Object... args){
		Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_LONG).show();
	}

	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public static final void toastS(Context context, int formatResId, Object... args){
		Toast.makeText(context, String.format(context.getString(formatResId), args), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 吐出一个显示时间较长的提示
	 * @param context 上下文对象
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public static final void toastL(Context context, String format, Object... args){
		Toast.makeText(context, String.format(format, args), Toast.LENGTH_LONG).show();
	}

	/**
	 * 吐出一个显示时间较短的提示
	 * @param context 上下文对象
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public static final void toastS(Context context, String format, Object... args){
		Toast.makeText(context, String.format(format, args), Toast.LENGTH_SHORT).show();
	}

}