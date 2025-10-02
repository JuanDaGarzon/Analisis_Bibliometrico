package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GnomeSort {


    private static <T> void gnomeSort(T[] arr, Comparator<? super T> c) {
        int n = arr.length;
        int index = 0;

        while (index < n) {

            // Caso 1: Estar al inicio del arreglo
            if (index == 0) {
                index++;
                continue; // Avanza sin comparar
            }

            // Caso 2: El elemento actual (arr[index]) es mayor o igual que el anterior (arr[index - 1])
            // Usamos c.compare(A, B) >= 0 para A >= B
            if (c.compare(arr[index], arr[index - 1]) >= 0) {
                index++; // Avanza a la derecha (todo está en orden hasta aquí)
            }

            // Caso 3: El elemento actual es menor que el anterior (DESORDEN)
            else {
                // swap: El tipo T debe usarse para la variable temporal
                T temp = arr[index];
                arr[index] = arr[index - 1];
                arr[index - 1] = temp;

                index--; // Retrocede un paso (al anterior) para revisar la nueva posición
            }
        }
    }

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        gnomeSort(arr, c);
    }

}
