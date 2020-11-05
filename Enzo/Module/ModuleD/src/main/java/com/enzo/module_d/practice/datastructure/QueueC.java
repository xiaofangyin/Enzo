package com.enzo.module_d.practice.datastructure;

/**
 * 循环队列
 * 文 件 名: QueueC
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class QueueC {

    private final int maxSize;
    private final Object[] queueArray;
    private int startIndex;
    private int endIndex;
    private int nItems;

    public QueueC(int n) {
        this.maxSize = n;
        this.queueArray = new Object[maxSize];
        this.startIndex = 0;
        this.endIndex = -1;
        this.nItems = 0;
    }

    public void insert(Object j) {
        if (!isFull()) {
            if (endIndex == maxSize - 1) {
                endIndex = -1;
            }
            queueArray[++endIndex] = j;
            nItems++;
        } else {
            System.out.println("队列已满.....");
        }
    }

    public Object remove() {
        if (!isEmpty()) {
            Object temp = queueArray[startIndex++];
            if (startIndex == maxSize) {
                startIndex = 0;
            }
            nItems--;
            return temp;
        } else {
            System.out.println("队列为空不能删除。。。。");
        }
        return null;
    }

    private boolean isEmpty() {
        return (nItems == 0);
    }

    private boolean isFull() {
        return (nItems == maxSize);
    }
}
