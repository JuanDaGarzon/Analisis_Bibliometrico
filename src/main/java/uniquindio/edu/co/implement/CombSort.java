package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class CombSort {



    private static <T> void combSort(T[] arr, Comparator<? super T> c) {
        int n = arr.length;
        int gap = n;
        boolean swapped = true;

        while (gap != 1 || swapped) {
            gap = getNextGap(gap);
            swapped = false;

            for (int i = 0; i < n - gap; i++) {
                // CRUCIAL: Reemplazar la comparación directa (>)
                // con el uso del Comparator.
                // c.compare(A, B) > 0 significa que A es mayor que B.
                if (c.compare(arr[i], arr[i + gap]) > 0) {

                    // Intercambio: la variable temporal debe ser de tipo T
                    T temp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = temp;
                    swapped = true;
                }
            }
        }
    }

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        combSort(arr, c);
    }

    private static int getNextGap(int gap) {
        // Implementación típica: gap = (gap * 10) / 13;
        // if (gap < 1) return 1;
        // return gap;
        // Usar el cuerpo de tu getNextGap aquí
        int newGap = (gap * 10) / 13;
        return (newGap < 1) ? 1 : newGap;
    }


}
