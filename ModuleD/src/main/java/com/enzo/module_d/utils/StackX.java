package com.enzo.module_d.utils;

/**
 * 文 件 名: StackX
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/9
 * 邮   箱: xiaofangyinwork@163.com
 */
public class StackX {
    private int maxSize;
    private long[] stackArray;
    private int top;

    public StackX(int max) {
        stackArray = new long[max];
        maxSize = max;
        top = -1;//空栈
    }

    public void push(long j) {
        if (isFull()) {
            stackArray[++top] = j;
        } else {
            System.out.println("数组已经满了。。。");
        }
    }

    public long pop() {
        if (isEmpty()) {
            return stackArray[top--];
        } else {
            return -1;
        }
    }

    public long peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == (maxSize - 1);
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
