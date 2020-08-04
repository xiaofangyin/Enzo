#去除红色警告
#noinspection ShrinkerUnresolvedReference

-optimizationpasses 5#指定代码的压缩级别
-dontusemixedcaseclassnames#包明不混合大小写
-dontskipnonpubliclibraryclasses#不去忽略非公共的库类
-dontpreverify#不进行预校验
-dontoptimize#不优化输入的类文件
#-ignorewarnings#忽略警告
-dontnote
-dontshrink#不压缩输入的类文件
#-verbose#混淆时是否记录日志#
#-dump class_files.txt#列出apk包内所有class的内部结构
#-printseeds seeds.txt#列出未混淆的类和成员
#-printusage unused.txt#列出从apk中删除的代码
#-printmapping mapping.txt#列出混淆前后的映射
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*#混淆时所采用的算法

#公共
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepnames class * extends android.view.View
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}
-keep public class **.R$*{
    public static final int *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

#枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#序列化
-keepnames class * implements java.io.Serializable
-keep class * implements android.os.Parcelable
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#注解
-keepattributes *Annotation*

#反射
-keepattributes EnclosingMethod

#泛型
-keepattributes Signature

#third part module library
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.**
-dontwarn javax.inject.**

# glide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
-dontwarn me.iwf.photopicker.adapter.**

#ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
