package com.leratortech.toolkit.tree;

import java.util.*;
import java.util.function.Function;

/**
 * Build tree from flat list
 */
public final class TreeBuilder {

    private TreeBuilder() {}

    public static <T, ID> List<TreeNode<T>> buildTree(
            List<T> items,
            Function<T, ID> idFunc,
            Function<T, ID> parentIdFunc
    ) {
        if (items == null || items.isEmpty()) return Collections.emptyList();

        Map<ID, TreeNode<T>> nodeMap = new HashMap<>();
        List<TreeNode<T>> roots = new ArrayList<>();

        for (T item : items) nodeMap.put(idFunc.apply(item), new TreeNode<>(item));

        for (T item : items) {
            TreeNode<T> node = nodeMap.get(idFunc.apply(item));
            ID parentId = parentIdFunc.apply(item);
            if (parentId == null || !nodeMap.containsKey(parentId)) {
                roots.add(node);
            } else {
                TreeNode<T> parentNode = nodeMap.get(parentId);
                parentNode.addChild(node);
            }
        }
        return roots;
    }
}
