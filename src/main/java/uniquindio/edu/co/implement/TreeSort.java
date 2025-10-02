package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
class Node <T> {
    T key; // La clave es ahora el objeto Articulo (T)
    Node<T> left, right; // Los hijos también deben ser genéricos

    public Node(T item) {
        key = item;
        left = right = null;
    }
}
@Service
@RequiredArgsConstructor
public class TreeSort {

    static Node root;

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        // La raíz debe ser inicializada como genérica
        root = null;
        treeSort(arr, c);
    }

    // ----------------------------------------------------------------------
    // treeSort adaptado para T[] y Comparator
    // ----------------------------------------------------------------------
    private static <T> void treeSort(T[] arr, Comparator<? super T> c) {

        // 1. Construir el BST insertando cada elemento
        for (T item : arr) {
            root = insert(root, item, c);
        }

        // 2. Recorrer el árbol en orden (in-order) para rellenar el array
        // Usamos un array de 1 posición para pasar el índice por referencia
        int[] index = {0};
        storeSorted(root, arr, index);
    }

    // ----------------------------------------------------------------------
    // Inserción en el BST: Usa el Comparator
    // ----------------------------------------------------------------------
    private static <T> Node<T> insert(Node<T> node, T key, Comparator<? super T> c) {

        if (node == null) {
            return new Node<>(key);
        }

        // CRUCIAL: Usar el Comparator para decidir la dirección
        // c.compare(key, node.key) < 0 significa que 'key' es menor
        if (c.compare(key, node.key) < 0) {
            node.left = insert(node.left, key, c);
        } else {
            // Si son iguales (== 0) o mayores (> 0), va a la derecha (BST estándar)
            node.right = insert(node.right, key, c);
        }

        return node;
    }

    // ----------------------------------------------------------------------
    // Recorrido en orden: Almacena las claves ordenadas en el array
    // ----------------------------------------------------------------------
    private static <T> void storeSorted(Node<T> node, T[] arr, int[] index) {
        if (node != null) {

            // Recorre subárbol izquierdo (menores)
            storeSorted(node.left, arr, index);

            // Almacena la clave actual (Articulo)
            arr[index[0]++] = node.key;

            // Recorre subárbol derecho (mayores/iguales)
            storeSorted(node.right, arr, index);
        }
    }
}

