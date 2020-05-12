package com.enzo.module_d.structure;

/**
 * 线性队列
 * 文 件 名: QueueX
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class QueueX {

    private int maxSize;
    private int front;
    private int rear;
    private Object[] queueArray;

    public QueueX(int n) {
        this.maxSize = n;
        this.front = -1;
        this.rear = -1;
        this.queueArray = new Object[maxSize];
    }

    public void insert(Object j) {
        if (rear < maxSize - 1) {
            queueArray[++rear] = j;
        } else {
            System.out.println("队列已满.....");
        }
    }

    public Object remove() {
        if (front != rear) {
            return queueArray[++front];
        } else {
            System.out.println("队列为空不能删除。。。。");
        }
        return null;
    }
}
