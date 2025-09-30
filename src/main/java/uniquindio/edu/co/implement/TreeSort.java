/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

class Node {
    int key;
    Node left, right;

    public Node(int item) {
        key = item;
        left = right = null;
    }
}

public class TreeSort {
    static Node root;

    public static void treeSort(int[] arr) {
        root = null;
        for (int num : arr) {
            root = insert(root, num);
        }
        int[] index = {0};
        storeSorted(root, arr, index);
    }

    static Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insert(node.left, key);
        else node.right = insert(node.right, key);
        return node;
    }

    static void storeSorted(Node node, int[] arr, int[] index) {
        if (node != null) {
            storeSorted(node.left, arr, index);
            arr[index[0]++] = node.key;
            storeSorted(node.right, arr, index);
        }
    }
}

