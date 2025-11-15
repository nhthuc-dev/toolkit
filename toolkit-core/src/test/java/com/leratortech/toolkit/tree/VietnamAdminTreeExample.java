package com.leratortech.toolkit.tree;

import com.leratortech.toolkit.tree.TreeNode;
import com.leratortech.toolkit.tree.TreeUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class AdministrativeUnit {
    Long id;
    Long parentId;
    String name;
    String type; // Tỉnh/TP, Huyện/Quận, Xã/Phường

    public AdministrativeUnit(Long id, Long parentId, String name, String type) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
    }

    public Long getId() { return id; }
    public Long getParentId() { return parentId; }
    public String getName() { return name; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}

public class VietnamAdminTreeExample {
    public static void main(String[] args) {
        List<AdministrativeUnit> units = List.of(
                new AdministrativeUnit(1L, null, "Hà Nội", "Thành phố trực thuộc Trung ương"),
                new AdministrativeUnit(2L, 1L, "Quận Ba Đình", "Quận"),
                new AdministrativeUnit(3L, 1L, "Quận Hoàn Kiếm", "Quận"),
                new AdministrativeUnit(4L, 2L, "Phường Phúc Xá", "Phường"),
                new AdministrativeUnit(5L, 2L, "Phường Trúc Bạch", "Phường"),
                new AdministrativeUnit(6L, 3L, "Phường Hàng Bạc", "Phường"),
                new AdministrativeUnit(7L, null, "TP Hồ Chí Minh", "Thành phố trực thuộc Trung ương"),
                new AdministrativeUnit(8L, 7L, "Quận 1", "Quận"),
                new AdministrativeUnit(9L, 8L, "Phường Bến Nghé", "Phường")
        );

        // Build tree
        List<TreeNode<AdministrativeUnit>> tree = TreeUtils.buildTree(
                units,
                AdministrativeUnit::getId,
                AdministrativeUnit::getParentId
        );

        System.out.println("===== Tree Hành Chính Việt Nam =====");
        TreeUtils.printTree(tree);

        // Flatten tree
        List<AdministrativeUnit> flat = TreeUtils.flattenData(tree);
        System.out.println("\n===== Flattened List =====");
        flat.forEach(u -> System.out.println(u));

        // Find node & descendants/ancestors
        Optional<TreeNode<AdministrativeUnit>> baDinh = TreeUtils.findNodeById(tree, AdministrativeUnit::getId, 2L);
        baDinh.ifPresent(node -> {
            System.out.println("\nNode: " + node.getData());
            System.out.println("Descendants: " + TreeUtils.getDescendants(node));
            System.out.println("Ancestors: " + TreeUtils.getAncestors(node));
        });

        // Tree stats
        int height = TreeUtils.getHeight(tree);
        System.out.println("\nTree height: " + height);

        // Map parentId -> children
        Map<Long, List<TreeNode<AdministrativeUnit>>> map = TreeUtils.treeToMap(tree, n -> {
            AdministrativeUnit data = n.getData();
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
