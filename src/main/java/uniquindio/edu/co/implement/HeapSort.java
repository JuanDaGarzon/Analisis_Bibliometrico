package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class HeapSort {

    private static <T> void heapify(T[] arr, int n, int i, Comparator<? super T> c) {
        int largest = i; // Inicializar largest como la raíz
        int l = 2 * i + 1; // índice del hijo izquierdo
        int r = 2 * i + 2; // índice del hijo derecho

        // Si el hijo izquierdo existe y es MAYOR que el elemento más grande hasta ahora
        // Usamos c.compare(A, B) > 0 para A > B
        if (l < n && c.compare(arr[l], arr[largest]) > 0) {
            largest = l;
        }

        // Si el hijo derecho existe y es MAYOR que el elemento más grande hasta ahora
        if (r < n && c.compare(arr[r], arr[largest]) > 0) {
            largest = r;
        }

        // Si el elemento más grande no es la raíz
        if (largest != i) {

            // Intercambio (swap)
            T swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Llamar recursivamente a heapify en el subárbol afectado
            heapify(arr, n, largest, c);
        }
    }

    private static <T> void heapSort(T[] arr, Comparator<? super T> c) {
        int n = arr.length;

        // Construir el montículo (max-heap).
        // Recorrer desde el último nodo no hoja hacia el inicio.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, c);
        }

        // Extraer elementos uno por uno
        for (int i = n - 1; i > 0; i--) {

            // Mover la raíz actual al final (swap arr[0] con arr[i])
            T temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Llamar a heapify en el montículo reducido (tamaño i)
            heapify(arr, i, 0, c);
        }
    }

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        heapSort(arr, c);
    }

}



