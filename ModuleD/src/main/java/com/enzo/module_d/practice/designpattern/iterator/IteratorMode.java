package com.enzo.module_d.practice.designpattern.iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 迭代器模式
 * 以一种一致的方法对集合内的元素进行遍历，而不用在乎集合内的数据结构
 */
public class IteratorMode {
    public static void main(String[] args) {
        //List集合遍历
        ArrayList<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }

        //Set集合遍历
        HashSet<String> set = new HashSet<>();
        Iterator<String> iterator1 = set.iterator();
        while (iterator.hasNext()) {
            String next = iterator1.next();
            System.out.println(next);
        }
    }
}
