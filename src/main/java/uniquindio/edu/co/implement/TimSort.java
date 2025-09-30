package uniquindio.edu.co.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class TimSort {

    // Constante RUN: tamaño de los bloques a ordenar con Insertion Sort
    private static final int RUN = 32;

    // Método principal para envolver la lógica y usar la firma simple
    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        timSort(arr, arr.length, c);
    }
    // ----------------------------------------------------------------------
    // 1. Insertion Sort (para los pequeños bloques RUN)
    // ----------------------------------------------------------------------
    private static <T> void insertionSort(T[] arr, int left, int right, Comparator<? super T> c) {
        for (int i = left + 1; i <= right; i++) {
            T temp = arr[i];
            int j = i - 1;

            // Mover elementos mayores que 'temp' una posición a la derecha
            // c.compare(arr[j], temp) > 0 significa que arr[j] es mayor que temp
            while (j >= left && c.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    private static <T> void timSort(T[] arr, int n, Comparator<? super T> c) {

        // Ordenar los bloques RUN usando Insertion Sort
        for (int i = 0; i < n; i += RUN) {
            insertionSort(arr, i, Math.min(i + RUN - 1, n - 1), c);
        }

        // Fusión (Merge) de los bloques ordenados
        for (int size = RUN; size < n; size *= 2) {
            for (int left = 0; left < n; left += 2 * size) {

                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);

                // Solo fusionar si el bloque "mid" es válido
                if (mid < right) {
                    merge(arr, left, mid, right, c);
                }
            }
        }
    }

    // ----------------------------------------------------------------------
    // 2. Merge (Fusión de dos subarrays ordenados)
    // ----------------------------------------------------------------------
    private static <T> void merge(T[] arr, int l, int m, int r, Comparator<? super T> c) {
        int len1 = m - l + 1;
        int len2 = r - m;

        // Crear arrays temporales
        T[] left = (T[]) new Object[len1];
        T[] right = (T[]) new Object[len2];

        // Copiar datos a los arrays temporales
        for (int i = 0; i < len1; i++) {
            left[i] = arr[l + i];
        }
        for (int i = 0; i < len2; i++) {
            right[i] = arr[m + 1 + i];
        }

        // Fusión: Usar el comparador para decidir qué elemento es menor
        int i = 0;
        int j = 0;
        int k = l;

        while (i < len1 && j < len2) {

            // c.compare(left[i], right[j]) <= 0 significa que left[i] es menor o igual
            if (c.compare(left[i], right[j]) <= 0) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copiar los elementos restantes (si los hay)
        while (i < len1) {
            arr[k] = left[i];
            k++;
            i++;
        }

        while (j < len2) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }
}
