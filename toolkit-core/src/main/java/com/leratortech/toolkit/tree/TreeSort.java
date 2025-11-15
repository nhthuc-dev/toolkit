package com.leratortech.toolkit.tree;

import java.util.Comparator;

/**
 * Tree sorting
 */
public final class TreeSort {

    private TreeSort() {}

    public static <T> void sortTree(TreeNode<T> node, Comparator<TreeNode<T>> comparator) {
        if (node == null || comparator == null) return;
        node.getChildren().sort(comparator);
        node.getChildren().forEach(child -> sortTree(child, comparator));
    }
}
