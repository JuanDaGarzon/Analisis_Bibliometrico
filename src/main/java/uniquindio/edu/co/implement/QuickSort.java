package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class QuickSort {

    private static <T> void quickSort(T[] arr, int low, int high, Comparator<? super T> c) {
        if (low < high) {

            // pi es el índice de partición
            int pi = partition(arr, low, high, c);

            // Llamadas recursivas, pasando el comparador 'c'
            quickSort(arr, low, pi - 1, c);
            quickSort(arr, pi + 1, high, c);
        }
    }

    private static <T> int partition(T[] arr, int low, int high, Comparator<? super T> c) {

        // Pivot es el elemento en la posición más alta
        T pivot = arr[high];
        int i = (low - 1); // Índice del elemento más pequeño

        for (int j = low; j < high; j++) {

            // CRUCIAL: Reemplazar la comparación directa (<=) con el Comparator
            // c.compare(A, B) <= 0 significa que A es menor o igual que B
            if (c.compare(arr[j], pivot) <= 0) {
                i++;

                // Intercambio (swap) arr[i] y arr[j]
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Intercambio (swap) arr[i + 1] y arr[high] (el pivot)
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        quickSort(arr, 0, arr.length - 1, c);
    }

}



