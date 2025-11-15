package com.leratortech.toolkit.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Traverse / flatten / print tree
 */
public final class TreeTraversal {

    private TreeTraversal() {}

    public static <T> void traverse(List<TreeNode<T>> roots, Consumer<TreeNode<T>> consumer) {
        if (roots == null || consumer == null) return;
        for (TreeNode<T> node : roots) {
            consumer.accept(node);
            traverse(node.getChildren(), consumer);
        }
    }

    public static <T> List<TreeNode<T>> flatten(List<TreeNode<T>> roots) {
        List<TreeNode<T>> result = new ArrayList<>();
        if (roots == null) return result;
        for (TreeNode<T> node : roots) {
            result.add(node);
            result.addAll(flatten(node.getChildren()));
        }
        return result;
    }

    public static <T> List<T> flattenData(List<TreeNode<T>> roots) {
        return flatten(roots).stream().map(TreeNode::getData).toList();
    }

    public static <T> void printTree(List<TreeNode<T>> roots) {
        printTree(roots, 0);
    }

    private static <T> void printTree(List<TreeNode<T>> nodes, int level) {
        if (nodes == null) return;
        String indent = "  ".repeat(level);
        for (TreeNode<T> node : nodes) {
            System.out.println(indent + "- " + node.getData());
            printTree(node.getChildren(), level + 1);
        }
    }
}
