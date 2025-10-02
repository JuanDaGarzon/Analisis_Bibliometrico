package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class BinaryInsertionSort {
    public static <T> void sort(T[] array, Comparator<? super T> c) {
        for (int i = 1; i < array.length; i++) {
            T key = array[i];

            int insertedPosition = binarySearch(array, key, c, 0, i - 1);

            if (insertedPosition < 0) {
                insertedPosition = -insertedPosition - 1;
            }

            int j = i;
            while (j > insertedPosition) { // Usamos j > insertedPosition en lugar de j >= insertedPosition
                array[j] = array[j - 1]; // Corregido: array[j] = array[j - 1]
                j--;
            }

            array[insertedPosition] = key;
        }
    }

    private static <T> int binarySearch(T[] array, T key, Comparator<? super T> comparator, int low, int high) {
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = comparator.compare(key, array[mid]);
            if (cmp == 0) return mid;
            else if (cmp < 0) high = mid - 1;
            else low = mid + 1;
        }
        return -(low + 1);
    }
}
