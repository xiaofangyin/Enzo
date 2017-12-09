package com.ifenglian.module_d.utils;

/**
 * 自定义队列: 队列先进先出(FIFO) 队列中的数据项不总是从数组的0下标开始,移除数据项后，
 * 队头指针会指向一个较高的下标位置
 * 循环队列(“缓冲环”)
 * 文 件 名: QueueX
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofy@ifenglian.com
 */
public class QueueX {

    private int maxSize;
    private Long[] queueArray;
    private int front;
    private int rear;
    private int nItems;

    public QueueX(int n) {
        this.maxSize = n;
        this.queueArray = new Long[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }

    public void insert(long j) {
        if (!isFull()) {
            if (rear == maxSize - 1)
                rear = -1;
            queueArray[++rear] = j;
            nItems++;
        } else {
            System.out.println("队列已满.....");
        }
    }

    public Long remove() {
        if (!isEmpty()) {
            Long temp = queueArray[front++];
            if (front == maxSize)
                front = 0;
            nItems--;
            return temp;
        } else {
            System.out.println("队列为空不能删除。。。。");
        }
        return null;
    }

    public Long peekFornt() {
        return queueArray[front];
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull() {
        return (nItems == maxSize);
    }

    public int queueSize() {
        return nItems;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Long[] getQueueArray() {
        return queueArray;
    }

    public void setQueueArray(Long[] queueArray) {
        this.queueArray = queueArray;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }

    public int getnItems() {
        return nItems;
    }

    public void setnItems(int nItems) {
        this.nItems = nItems;
    }

}
