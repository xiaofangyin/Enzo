package com.enzo.module_d.practice.datastructure.bitree;

/**
 * 文 件 名: BinaryTree
 * 创 建 人: xiaofangyin
 * 创建日期: 2020/5/12
 * 邮   箱: xiaofangyin@360.cn
 */
public class BinaryTree {
    private BinaryTreeNode root;

    //初始化二叉树
    public BinaryTree() {
    }

    public BinaryTree(BinaryTreeNode root) {
        this.root = root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }


    /**
     * 二叉树的清空：
     * 首先提供一个清空以某个节点为根节点的子树的方法，既递归地删除每个节点；
     * 接着提供一个删除树的方法，直接通过第一种方法删除到根节点即可
     */
    //清除某个子树的所有节点
    public void clear(BinaryTreeNode node) {
        if (node != null) {
            clear(node.getLeftChild());
            clear(node.getRightChild());
            node = null; //删除节点
        }
    }

    //清空树
    public void clear() {
        clear(root);
    }

    //判断二叉树是否为空
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 求二叉树的高度：
     * 首先要一种获取以某个节点为子树的高度的方法，使用递归调用。
     * 如果一个节点为空，那么这个节点肯定是一颗空树，高度为0；
     * 如果不为空，那么我们要遍历地比较它的左子树高度和右子树高度，
     * 高的一个为这个子树的最大高度，然后加上自己本身的高度就是了
     * 获取二叉树的高度，只需要调用第一种方法，即传入根节点
     */

    //获取二叉树的高度
    public int height() {
        return height(root);
    }

    //获取以某节点为子树的高度
    public int height(BinaryTreeNode node) {
        if (node == null) {
            return 0; //递归结束，空子树高度为0
        } else {
            //递归获取左子树高度
            int l = height(node.getLeftChild());
            //递归获取右子树高度
            int r = height(node.getRightChild());
            //高度应该算更高的一边，（+1是因为要算上自身这一层）
            return l > r ? (l + 1) : (r + 1);
        }
    }


    /**
     * 求二叉树的节点数：
     * 求节点数时，我们看看获取某个节点为子树的节点数的实现。
     * 首先节点为空，则个数肯定为0；
     * 如果不为空，那就算上这个节点之后继续递归所有左右子树的子节点数，
     * 全部相加就是以所给节点为根的子树的节点数
     * 如果求二叉树的节点数，则输入根节点即可
     */
    public int size() {
        return size(root);
    }

    public int size(BinaryTreeNode node) {
        if (node == null) {
            return 0;  //如果节点为空，则返回节点数为0
        } else {
            //计算本节点 所以要+1
            //递归获取左子树节点数和右子树节点数，最终相加
            return 1 + size(node.getLeftChild()) + size(node.getRightChild());
        }
    }


    //node节点在subTree子树中的父节点
    public BinaryTreeNode getParent(BinaryTreeNode subTree, BinaryTreeNode node) {
        if (subTree == null) {
            return null;   //如果是空子树，则没有父节点
        }
        if (subTree.getLeftChild() == node || subTree.getRightChild() == node) {
            return subTree;   //如果子树的根节点的左右孩子之一是待查节点，则返回子树的根节点
        }
        BinaryTreeNode parent = null;
        if (getParent(subTree.getLeftChild(), node) != null) {
            parent = getParent(subTree.getLeftChild(), node);
            return parent;
        } else {
            //递归左右子树
            return getParent(subTree.getRightChild(), node);
        }
    }

    //查找node节点在二叉树中的父节点
    public BinaryTreeNode getParent(BinaryTreeNode node) {
        return (root == null || root == node) ? null : getParent(root, node);
    }

    //给某个节点插入左节点
    public void insertLeft(BinaryTreeNode parent, BinaryTreeNode newnode) {
        parent.setLeftChild(newnode);
    }

    //给某个节点插入右节点
    public void insertRitht(BinaryTreeNode parent, BinaryTreeNode newnode) {
        parent.setRightChild(newnode);
    }

    //先根遍历(PreOrder)
    public void PreOrder(BinaryTreeNode node) {
        if (node != null) {
            System.out.println(node.getData()); //先访问根节点
            PreOrder(node.getLeftChild());  //先根遍历左子树
            PreOrder(node.getRightChild());  //先根遍历右子树
        }
    }

    //中根遍历(InOrder)
    public void InOrder(BinaryTreeNode node) {
        if (node != null) {
            InOrder(node.getLeftChild());  //中根遍历左子树
            System.out.println(node);    //访问根节点
            InOrder(node.getRightChild());  //中根遍历右子树
        }
    }

    //后根遍历(PostOrder)
    public void PostOrder(BinaryTreeNode node) {
        if (node != null) {
            PostOrder(node.getLeftChild());  //后根遍历左子树
            PostOrder(node.getRightChild());  //后根遍历右子树
            System.out.println(node);   //访问根节点
        }
    }
}
