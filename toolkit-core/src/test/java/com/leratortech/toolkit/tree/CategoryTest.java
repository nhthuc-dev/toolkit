package com.leratortech.toolkit.tree;

import com.leratortech.toolkit.tree.TreeNode;
import com.leratortech.toolkit.tree.TreeUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class Category {
    Long id; Long parentId; String name;
    public Category(Long id, Long parentId, String name) { this.id = id; this.parentId = parentId; this.name = name; }
    public Long getId() { return id; }
    public Long getParentId() { return parentId; }
    public String getName() { return name; }
    public String toString() { return name; }
}

public class CategoryTest {
    public static void main(String[] args) {

        List<Category> categories = List.of(
                new Category(1L, null, "Electronics"),
                new Category(2L, 1L, "Laptops"),
                new Category(3L, 1L, "Phones"),
                new Category(4L, 2L, "Gaming Laptops"),
                new Category(5L, null, "Books"),
                new Category(6L, 5L, "Fiction"),
                new Category(7L, 5L, "Non-fiction")
        );

        // Build tree
        List<TreeNode<Category>> tree = TreeUtils.buildTree(
                categories,
                Category::getId,
                Category::getParentId
        );

        System.out.println("===== Original Tree =====");
        TreeUtils.printTree(tree);

        // Sort tree alphabetically
        TreeUtils.traverse(tree, node -> TreeUtils.sortTree(node,
                Comparator.comparing(n -> n.getData().getName())
        ));

        System.out.println("\n===== Sorted Tree =====");
        TreeUtils.printTree(tree);

        // Flatten tree
        List<Category> flat = TreeUtils.flattenData(tree);
        System.out.println("\n===== Flattened Tree =====");
        flat.forEach(c -> System.out.println(c.getName()));

        // Find node and descendants / ancestors
        Optional<TreeNode<Category>> laptopsNode = TreeUtils.findNodeById(tree, Category::getId, 2L);
        laptopsNode.ifPresent(node -> {
            System.out.println("\nFound Node: " + node.getData().getName());
            System.out.println("Descendants: " + TreeUtils.getDescendants(node));
            System.out.println("Ancestors: " + TreeUtils.getAncestors(node));
        });

        // Tree stats
        int height = TreeUtils.getHeight(tree);
        System.out.println("\nTree Height: " + height);

        // Convert tree to map parentId -> children
        Map<Long, List<TreeNode<Category>>> map = TreeUtils.treeToMap(tree, n -> {
            Category data = n.getData();
            return data.getParentId() == null ? 0L : data.getParentId();
        });
        System.out.println("\nParentId -> Children Map:");
        map.forEach((pid, children) -> {
            System.out.print(pid + " -> ");
            children.forEach(c -> System.out.print(c.getData().getName() + ", "));
            System.out.println();
        });
    }
}
