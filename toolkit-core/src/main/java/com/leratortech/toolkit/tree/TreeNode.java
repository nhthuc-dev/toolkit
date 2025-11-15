package com.leratortech.toolkit.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generic Tree Node
 */
public class TreeNode<T> {
    private final T data;
    private final List<TreeNode<T>> children = new ArrayList<>();
    private TreeNode<T> parent = null;

    public TreeNode(T data) { this.data = data; }

    public T getData() { return data; }
    public List<TreeNode<T>> getChildren() { return children; }
    public TreeNode<T> getParent() { return parent; }

    void setParent(TreeNode<T> parent) { this.parent = parent; }

    public void addChild(TreeNode<T> child) {
        if (child != null) {
            child.setParent(this);
            children.add(child);
        }
    }

    @Override
    public String toString() {
        return Objects.toString(data);
    }
}
