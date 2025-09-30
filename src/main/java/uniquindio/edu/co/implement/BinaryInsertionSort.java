/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class BinaryInsertionSort {

    public static void binaryInsertionSort(int[] arr) {

        // Método principal
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];

            // Encuentra la posición donde insertar usando búsqueda binaria
            int insertPos = binarySearch(arr, key, 0, i - 1);

            // Desplaza los elementos para abrir espacio
            int j = i - 1;
            while (j >= insertPos) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[insertPos] = key;
        }
    }

    // Búsqueda binaria para encontrar índice de inserción
    private static int binarySearch(int[] arr, int key, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (key == arr[mid]) {
                return mid + 1; // insertar después de los iguales
            }
            if (key > arr[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}
