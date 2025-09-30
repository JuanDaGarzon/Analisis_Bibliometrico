/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


class BitonicSort {

    
    // Constantes para dirección
    static final int ASCENDING = 1;
    static final int DESCENDING = 0;

    // Método principal que el main llama
    public static void bitonicSort(int[] arr, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;

            // Ordena en orden ascendente la primera mitad
            bitonicSort(arr, low, k, ASCENDING);

            // Ordena en orden descendente la segunda mitad
            bitonicSort(arr, low + k, k, DESCENDING);

            // Combina para hacer secuencia bitónica
            bitonicMerge(arr, low, cnt, dir);
        }
    }

    // Fusiona la secuencia bitónica en orden definido
    private static void bitonicMerge(int[] arr, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++) {
                if (dir == ASCENDING && arr[i] > arr[i + k]) {
                    swap(arr, i, i + k);
                }
                if (dir == DESCENDING && arr[i] < arr[i + k]) {
                    swap(arr, i, i + k);
                }
            }
            bitonicMerge(arr, low, k, dir);
            bitonicMerge(arr, low + k, k, dir);
        }
    }

    // Intercambia valores en el arreglo
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
