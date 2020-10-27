package com.enzo.module_d.utils.hotfix;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ShareReflectUtil {


    /**
     * 从 instance 到其父类 找 name 属性
     *
     * @param instance
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                //查找当前类的 属性(不包括父类)
                Field field = clazz.getDeclaredField(name);

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /**
     * 从 instance 到其父类 找  name 方法
     *
     * @param instance
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);

                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }
        throw new NoSuchMethodException("Method "
                + name
                + " with parameters "
                + Arrays.asList(parameterTypes)
                + " not found in " + instance.getClass());
    }


    /**
     * @param instance
     * @param fieldName
     * @param patchElements 补丁的Element数组
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void expandFieldArray(Object instance, String fieldName, Object[] patchElements)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        //拿到 classloader中的dexelements 数组
        Field dexElementsField = findField(instance, fieldName);
        //old Element[]
        Object[] dexElements = (Object[]) dexElementsField.get(instance);


        //合并后的数组
        Object[] newElements = (Object[]) Array.newInstance(dexElements.getClass().getComponentType(),
                dexElements.length + patchElements.length);

        // 先拷贝新数组
        System.arraycopy(patchElements, 0, newElements, 0, patchElements.length);
        System.arraycopy(dexElements, 0, newElements, patchElements.length, dexElements.length);

        //修改 classLoader中 pathList的 dexelements
        dexElementsField.set(instance, newElements);
    }


}
