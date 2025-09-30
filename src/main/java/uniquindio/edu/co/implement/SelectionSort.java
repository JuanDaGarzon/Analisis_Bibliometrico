package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SelectionSort {

    // ----------------------------------------------------------------------
    // selectionSort adaptado para T[] y Comparator
    // ----------------------------------------------------------------------
    private static <T> void selectionSort(T[] arr, Comparator<? super T> c) {
        int n = arr.length;

        // El bucle externo recorre todo el arreglo
        for (int i = 0; i < n - 1; i++) {

            // Suponemos que el elemento en 'i' es el mínimo
            int minIndex = i;

            // El bucle interno busca el verdadero mínimo en la parte no ordenada
            for (int j = i + 1; j < n; j++) {

                // CRUCIAL: Reemplazar la comparación directa (<) con el Comparator
                // c.compare(A, B) < 0 significa que A es menor que B
                if (c.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Intercambiar (swap) el elemento mínimo encontrado con arr[i]
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        selectionSort(arr, c);
    }

}
