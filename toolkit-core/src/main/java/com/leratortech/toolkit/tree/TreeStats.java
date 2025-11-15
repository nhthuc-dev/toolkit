package com.leratortech.toolkit.tree;

import java.util.List;

/**
 * Tree statistics
 */
public final class TreeStats {

    private TreeStats() {}

    public static <T> int getDepth(TreeNode<T> node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;
        return 1 + node.getChildren().stream().mapToInt(TreeStats::getDepth).max().orElse(0);
    }

    public static <T> int getHeight(List<TreeNode<T>> roots) {
        if (roots == null || roots.isEmpty()) return 0;
        return roots.stream().mapToInt(TreeStats::getDepth).max().orElse(0);
    }
}
