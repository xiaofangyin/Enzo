package com.enzo.module_d.practice.algorithm;

import java.util.Scanner;

/**
 * 文 件 名: BinarySearch
 * 创 建 人: xiaofangyin
 * 创建日期: 2020/5/12
 * 邮   箱: xiaofangyin@360.cn
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {16, 19, 20, 23, 45, 56, 78, 90, 96, 98, 100};
        int item, location = -1;
        System.out.println("Enter the item which you want to search");
        Scanner sc = new Scanner(System.in);
        item = sc.nextInt();
        location = binarySearch(arr, 0, 10, item);
        if (location != -1)
            System.out.println("the location of the item is " + location);
        else
            System.out.println("Item not found");
    }

    private static int binarySearch(int[] a, int beg, int end, int item) {
        if (end >= beg) {
            int mid = (beg + end) / 2;
            System.out.println("position: " + mid + "...value: " + a[mid]);
            if (a[mid] == item) {
                return mid;
            } else if (a[mid] < item) {
                return binarySearch(a, mid + 1, end, item);
            } else {
                return binarySearch(a, beg, mid - 1, item);
            }
        }
        return -1;
    }
}
