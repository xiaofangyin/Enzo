package com.enzo.module_d.practice.algorithm;

import java.util.ArrayList;

public class GetSubSet {


    /**
     * Java没有自带的求一个集合的所有子集的方法，我们可以通过集合的子集规律来求。
     * 一个集合的所有子集等于2^该集合的长度。比如{c,b,a}的长度为3，这个集合的子集就有8个。
     * 这句话看起来很简单，但同时也隐含着高深的哲理。其实一个集合的所有集合，和2^该集合的长度这个数字有关。
     * 比如上面的例子，{c,b,a}的长度为3，则可以用0-7表示其所有子集。如下所示，改数字所对应的位置为1，
     * 则说明我需要这个数字形成子集。从0-7的二进制表示，刚好代表完，一个长度为3，子集个数为8的所有子集。
     * 0(000)：{}
     * 1(001)：{a}
     * 2(010)：{b}
     * 3(011)：{ab}
     * 4(100)：{c}
     * 5(101)：{a,c}
     * 6(110)：{b,c}
     * 7(111)：{a,b,c}
     * 于是，根据上面的规律，代码可以这样写，先取集合长度，求出2^该集合的长度是多少，
     * 比如上面的8，然后从0遍历到8-1。遍历的时候，对0、1、2……每一个数据进行位运算，
     * 逐一判断其对应的位数，也就是二进制表示方式，那一位是1。用汇编那种，将每一位移到最末尾，
     * 与1的位与实现.
     */
    private static ArrayList<ArrayList<Integer>> getSubset(ArrayList<Integer> L) {
        if (L.size() > 0) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < Math.pow(2, L.size()); i++) {// 集合子集个数=2的该集合长度的乘方
                ArrayList<Integer> subSet = new ArrayList<Integer>();
                int index = i;// 索引从0一直到2的集合长度的乘方-1
                for (int j = 0; j < L.size(); j++) {
                    // 通过逐一位移，判断索引那一位是1，如果是，再添加此项
                    if ((index & 1) == 1) {// 位与运算，判断最后一位是否为1
                        subSet.add(L.get(j));
                    }
                    index >>= 1;// 索引右移一位
                }
                result.add(subSet); // 把子集存储起来
            }
            return result;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> L = new ArrayList<Integer>();
        L.add(1);
        L.add(2);
        L.add(3);
        System.out.println(getSubset(L));
    }
}
