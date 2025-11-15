package com.leratortech.toolkit.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Search / find / descendants / ancestors
 */
public final class TreeSearch {

    private TreeSearch() {}

    public static <T> Optional<TreeNode<T>> findNode(List<TreeNode<T>> roots, Predicate<TreeNode<T>> predicate) {
        if (roots == null || predicate == null) return Optional.empty();
        for (TreeNode<T> node : roots) {
            if (predicate.test(node)) return Optional.of(node);
            Optional<TreeNode<T>> found = findNode(node.getChildren(), predicate);
            if (found.isPresent()) return found;
        }
        return Optional.empty();
    }

    public static <T, ID> Optional<TreeNode<T>> findNodeById(List<TreeNode<T>> roots, Function<T, ID> idFunc, ID id) {
        return findNode(roots, node -> Objects.equals(idFunc.apply(node.getData()), id));
    }

    public static <T> List<TreeNode<T>> getDescendants(TreeNode<T> node) {
        List<TreeNode<T>> result = new ArrayList<>();
        if (node == null) return result;
        for (TreeNode<T> child : node.getChildren()) {
            result.add(child);
            result.addAll(getDescendants(child));
        }
        return result;
    }

    public static <T> List<TreeNode<T>> getAncestors(TreeNode<T> node) {
        List<TreeNode<T>> result = new ArrayList<>();
        while (node != null && node.getParent() != null) {
            result.add(node.getParent());
            node = node.getParent();
        }
        Collections.reverse(result);
        return result;
    }
}
