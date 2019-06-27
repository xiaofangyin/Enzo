package com.enzo.module_d.utils;

/**
 * 自定义队列: 队列先进先出(FIFO) 队列中的数据项不总是从数组的0下标开始,移除数据项后，
 * 队头指针会指向一个较高的下标位置
 * 循环队列(“缓冲环”)
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

    public void insert(long j) {
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

    public Object peekFornt() {
        return queueArray[front];
    }
}
