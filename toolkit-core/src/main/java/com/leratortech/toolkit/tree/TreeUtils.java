package com.leratortech.toolkit.tree;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TreeUtils Full Wrapper
 */
public final class TreeUtils {

    private TreeUtils() {}

    // Build
    public static <T, ID> List<TreeNode<T>> buildTree(List<T> items, Function<T, ID> idFunc, Function<T, ID> parentIdFunc) {
        return TreeBuilder.buildTree(items, idFunc, parentIdFunc);
    }

    // Traverse / Print
    public static <T> void traverse(List<TreeNode<T>> roots, java.util.function.Consumer<TreeNode<T>> consumer) {
        TreeTraversal.traverse(roots, consumer);
    }
    public static <T> void printTree(List<TreeNode<T>> roots) {
        TreeTraversal.printTree(roots);
    }

    // Flatten
    public static <T> List<TreeNode<T>> flatten(List<TreeNode<T>> roots) {
        return TreeTraversal.flatten(roots);
    }
    public static <T> List<T> flattenData(List<TreeNode<T>> roots) {
        return TreeTraversal.flattenData(roots);
    }

    // Find
    public static <T> Optional<TreeNode<T>> findNode(List<TreeNode<T>> roots, Predicate<TreeNode<T>> predicate) {
        return TreeSearch.findNode(roots, predicate);
    }
    public static <T, ID> Optional<TreeNode<T>> findNodeById(List<TreeNode<T>> roots, Function<T, ID> idFunc, ID id) {
        return TreeSearch.findNodeById(roots, idFunc, id);
    }

    // Descendants / Ancestors
    public static <T> List<TreeNode<T>> getDescendants(TreeNode<T> node) {
        return TreeSearch.getDescendants(node);
    }
    public static <T> List<TreeNode<T>> getAncestors(TreeNode<T> node) {
        return TreeSearch.getAncestors(node);
    }

    // Stats
    public static <T> int getDepth(TreeNode<T> node) {
        return TreeStats.getDepth(node);
    }
    public static <T> int getHeight(List<TreeNode<T>> roots) {
        return TreeStats.getHeight(roots);
    }

    // Sorting
    public static <T> void sortTree(TreeNode<T> node, java.util.Comparator<TreeNode<T>> comparator) {
        TreeSort.sortTree(node, comparator);
    }

    // Map conversion
    public static <T, ID> java.util.Map<ID, List<TreeNode<T>>> treeToMap(List<TreeNode<T>> roots, Function<TreeNode<T>, ID> parentIdFunc) {
        return TreeMapUtils.treeToMap(roots, parentIdFunc);
    }
}
