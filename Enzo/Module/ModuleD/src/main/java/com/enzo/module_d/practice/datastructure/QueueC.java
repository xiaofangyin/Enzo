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
    private int frontIndex;
    private int behindIndex;
    private int nItems;

    public QueueC(int n) {
        this.maxSize = n;
        this.queueArray = new Object[maxSize];
        this.frontIndex = 0;
        this.behindIndex = -1;
        this.nItems = 0;
    }

    public void insert(Object j) {
        if (!isFull()) {
            if (behindIndex == maxSize - 1) {
                behindIndex = -1;
            }
            queueArray[++behindIndex] = j;
            nItems++;
        } else {
            System.out.println("队列已满.....");
        }
    }

    public Object remove() {
        if (!isEmpty()) {
            Object temp = queueArray[frontIndex++];
            if (frontIndex == maxSize) {
                frontIndex = 0;
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
