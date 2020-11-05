package com.enzo.module_d.practice.datastructure;

/**
 * 线性队列
 * 文 件 名: QueueX
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class QueueX {

    private final int maxSize;
    private final Object[] queueArray;
    private int frontIndex;
    private int behindIndex;

    public QueueX(int n) {
        this.maxSize = n;
        this.frontIndex = -1;
        this.behindIndex = -1;
        this.queueArray = new Object[maxSize];
    }

    public void insert(Object j) {
        if (behindIndex < maxSize - 1) {
            queueArray[++behindIndex] = j;
        } else {
            System.out.println("队列已满.....");
        }
    }

    public Object remove() {
        if (frontIndex != behindIndex) {
            return queueArray[++frontIndex];
        } else {
            System.out.println("队列为空不能删除。。。。");
        }
        return null;
    }
}
