package com.enzo.module_d.practice.designpattern.strategy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 策略模式
 * 就拿之前学的Arrays类和Collections类中的sort()方法来说，
 * 这个方法有个重载需要两个参数，一个是数组或者集合一个是Comparator接口，
 * 这个接口中可以写一种策略，这个策略保证我们以什么方式排列元素（降序，升序）。
 * 但是底层算法实现就固定的，因为java设计人员选择的算法都是比较优秀的。这就是策略模式的应用。
 */
public class StrategyMode {

    /**
     * 1、升序：
     * if(o1<o2)  return -1; //(交换顺序）
     * if(o1==o2)  return 0; //或者返回1效果是一样的；-1相同元素会发生位置调整（虽然两个相同的数字交换顺序没影响，但如果是map元素则有影响）
     * if(o1>o2)  return 1; //不交换顺序
     * <p>
     * 2、降序：
     * if(o1>o2)  return -1; //(交换顺序）
     * if(o1==o2)  return 0; //或者返回1效果是一样的；-1相同元素会发生位置调整（虽然两个相同的数字交换顺序没影响，但如果是map元素则有影响）
     * if(o1<o2)  return 1; //不交换顺序
     */
    public static void main(String[] args) {
        Integer[] arr = {5, 4, 3, 54, 48, 41, 56};
        Arrays.sort(arr, new Comparator<Integer>() {
            //返回负数表示o1 小于o2，返回0 表示o1和o2相等，返回正数表示o1大于o2。
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println("o1: " + o1 + "...o2: " + o2 + "...(o1 - o2) = " + (o1 - o2));
                return o1 - o2;
            }
        });
        System.out.println(Arrays.toString(arr));
    }
}
