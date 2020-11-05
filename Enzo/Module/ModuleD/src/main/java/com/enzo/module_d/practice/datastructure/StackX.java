package com.enzo.module_d.practice.datastructure;

/**
 * 文 件 名: StackX
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class StackX {
    private final int maxSize;
    private final long[] stackArray;
    private int topIndex;

    public StackX(int max) {
        stackArray = new long[max];
        maxSize = max;
        topIndex = -1;//空栈
    }

    public void push(long j) {
        if (!isFull()) {
            stackArray[++topIndex] = j;
        } else {
            System.out.println("数组已经满了。。。");
        }
    }

    public long pop() {
        if (!isEmpty()) {
            return stackArray[topIndex--];
        } else {
            return -1;
        }
    }

    public long peek() {
        return stackArray[topIndex];
    }

    public boolean isEmpty() {
        return topIndex == -1;
    }

    public boolean isFull() {
        return topIndex == (maxSize - 1);
    }

    public static void main(String[] args) {
        //创建栈
        StackX stack = new StackX(10);
        //入栈
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);

        //查看栈
        System.out.println("查看栈：" + stack.peek());

        //出栈
        while (!stack.isEmpty()) {
            System.out.println("出栈" + stack.pop());
        }
    }
}
