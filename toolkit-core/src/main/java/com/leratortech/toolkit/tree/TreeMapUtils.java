package com.leratortech.toolkit.tree;

import java.util.*;
import java.util.function.Function;

/**
 * Convert tree to map: parentId -> children
 */
public final class TreeMapUtils {

    private TreeMapUtils() {}

    public static <T, ID> Map<ID, List<TreeNode<T>>> treeToMap(
            List<TreeNode<T>> roots,
            Function<TreeNode<T>, ID> parentIdFunc
    ) {
        Map<ID, List<TreeNode<T>>> map = new HashMap<>();
        TreeTraversal.traverse(roots, node -> {
            ID pid = parentIdFunc.apply(node);
            map.computeIfAbsent(pid, k -> new ArrayList<>()).add(node);
        });
        return map;
    }
}
