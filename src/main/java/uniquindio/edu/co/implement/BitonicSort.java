package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class BitonicSort {
    // Constantes para dirección
    static final int ASCENDING = 1;
    static final int DESCENDING = 0;
    // Método principal que el main llama
    public static <T> void bitonicSort(T[] arr, int low, int cnt, int dir, Comparator<? super T> c) {
        if (cnt > 1) {
            int k = cnt / 2;

            bitonicSort(arr, low, k, ASCENDING, c);
            bitonicSort(arr, low + k, k, DESCENDING, c);
            bitonicMerge(arr, low, cnt, dir, c); // Asegúrate que esta llamada también tenga los 5

        }
    }
    // Fusiona la secuencia bitónica en orden definido
    private static <T> void bitonicMerge(T[] arr, int low, int cnt, int dir, Comparator<? super T> c) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++) {
                // Si el orden es ASCENDENTE y arr[i] > arr[i+k] (comparación > 0)
                if (dir == ASCENDING && c.compare(arr[i], arr[i + k]) > 0) {
                    swap(arr, i, i + k);
                }
                // Si el orden es DESCENDENTE y arr[i] < arr[i+k] (comparación < 0)
                else if (dir == DESCENDING && c.compare(arr[i], arr[i + k]) < 0) {
                    swap(arr, i, i + k);
                }
            }
            // Llamadas recursivas, pasando el comparador 'c'
            bitonicMerge(arr, low, k, dir, c); // Aquí parece que "dir" es el argumento 4 y "c" el 5
            bitonicMerge(arr, low + k, k, dir, c); // **Parece que el 3er argumento 'cnt' debería ser 'k'**
        }
    }
    // Intercambia valores en el arreglo
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static <T> void sort(T[] array, Comparator<? super T> c) {
        // Llama al método principal para ordenar todo el array en orden ascendente
        bitonicSort(array, 0, array.length, ASCENDING, c);
    }
}
