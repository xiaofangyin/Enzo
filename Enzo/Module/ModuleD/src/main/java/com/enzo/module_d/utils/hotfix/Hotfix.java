package com.enzo.module_d.utils.hotfix;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Hotfix {

    private static final String TAG = "Hotfix";

    public static void installPatch(Application application, File patch) {
        //1、获得classloader,PathClassLoader
        ClassLoader classLoader = application.getClassLoader();


        List<File> files = new ArrayList<>();
        if (patch.exists()) {
            files.add(patch);
        }
        File dexOptDir = application.getCacheDir();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                NewClassLoaderInjector.inject(application, classLoader, files);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            try {
                //23 6.0及以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    V23.install(classLoader, files, dexOptDir);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    V19.install(classLoader, files, dexOptDir); //4.4以上
                } else {  // >= 14
                    V14.install(classLoader, files, dexOptDir);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final class V23 {

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                    File optimizedDirectory)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
                IOException {
            //找到 pathList
            Field pathListField = ShareReflectUtil.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);

            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            // 从 pathList找到 makePathElements 方法并执行
            // 得到补丁创建的 Element[]
            Object[] patchElements = makePathElements(dexPathList,
                    new ArrayList<>(additionalClassPathEntries), optimizedDirectory,
                    suppressedExceptions);

            //将原本的 dexElements 与 makePathElements生成的数组合并
            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements", patchElements);
            if (suppressedExceptions.size() > 0) {
                for (IOException e : suppressedExceptions) {
                    Log.w(TAG, "Exception in makePathElement", e);
                    throw e;
                }

            }
        }

        /**
         * 把dex转化为Element数组
         */
        private static Object[] makePathElements(
                Object dexPathList, ArrayList<File> files, File optimizedDirectory,
                ArrayList<IOException> suppressedExceptions)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            //通过阅读android6、7、8、9源码，都存在makePathElements方法
            Method makePathElements = ShareReflectUtil.findMethod(dexPathList, "makePathElements",
                    List.class, File.class,
                    List.class);
            return (Object[]) makePathElements.invoke(dexPathList, files, optimizedDirectory,
                    suppressedExceptions);
        }
    }

    private static final class V19 {

        private static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                    File optimizedDirectory)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
                IOException {
            Field pathListField = ShareReflectUtil.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements",
                    makeDexElements(dexPathList,
                            new ArrayList<File>(additionalClassPathEntries), optimizedDirectory,
                            suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                for (IOException e : suppressedExceptions) {
                    Log.w(TAG, "Exception in makeDexElement", e);
                    throw e;
                }
            }
        }

        private static Object[] makeDexElements(
                Object dexPathList, ArrayList<File> files, File optimizedDirectory,
                ArrayList<IOException> suppressedExceptions)
                throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            Method makeDexElements = ShareReflectUtil.findMethod(dexPathList, "makeDexElements",
                    ArrayList.class, File.class,
                    ArrayList.class);


            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory,
                    suppressedExceptions);
        }
    }

    /**
     * 14, 15, 16, 17, 18.
     */
    private static final class V14 {


        private static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                    File optimizedDirectory)
                throws IllegalArgumentException, IllegalAccessException,
                NoSuchFieldException, InvocationTargetException, NoSuchMethodException {

            Field pathListField = ShareReflectUtil.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);

            ShareReflectUtil.expandFieldArray(dexPathList, "dexElements",
                    makeDexElements(dexPathList,
                            new ArrayList<File>(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(
                Object dexPathList, ArrayList<File> files, File optimizedDirectory)
                throws IllegalAccessException, InvocationTargetException,
                NoSuchMethodException {
            Method makeDexElements =
                    ShareReflectUtil.findMethod(dexPathList, "makeDexElements", ArrayList.class,
                            File.class);
            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory);
        }
    }

}
