package com.enzo.module_d.practice.algorithm;

/**
 * 文 件 名: QuickSort
 * 创 建 人: xiaofangyin
 * 创建日期: 2020/5/12
 * 邮   箱: xiaofangyin@360.cn
 */
public class QuickSort {

    public static void main(String[] args) {
        int a[] = {2, 3, 6, 4, 0, 1, 7, 8, 5, 9};
        bubbleSort(a);

        selectSort(a);
    }

    /**
     * 冒泡排序
     */
    private static void bubbleSort(int[] a) {
        int i, j;
        for (i = 0; i < a.length - 1; i++) {
            for (j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        System.out.println("冒泡排序从小到大排序后的结果是:");
        for (int value : a) {
            System.out.print(value + " ");
        }
        System.out.println(" ");
    }

    /**
     * 选择排序
     */
    private static void selectSort(int[] a) {
        int length = a.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i; j < length - 1; j++) {
                if (a[i] > a[j + 1]) {
                    int temp = a[i];
                    a[i] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        System.out.println("选择排序从小到大排序后的结果是:");
        for (int value : a) {
            System.out.print(value + " ");
        }
        System.out.println(" ");
    }
}
