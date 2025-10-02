package uniquindio.edu.co.implement;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniquindio.edu.co.model.Articulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PigeonholeSort {


    public static void sort(Articulo[] arr, Comparator<Articulo> c) {

        int n = arr.length;
        if (n <= 0) return;

        // 1. Encontrar el rango de años
        int min = getSafeAnio(arr[0]);
        int max = getSafeAnio(arr[0]);

        for (Articulo a : arr) {
            int num = getSafeAnio(a);
            if (num < min) min = num;
            if (num > max) max = num;
        }

        int range = max - min + 1;

        // 2. Crear los "palomares" (holes) como Listas de Articulo
        // Un palomar por cada año en el rango
        List<List<Articulo>> holes = new ArrayList<>(range);
        for (int i = 0; i < range; i++) {
            holes.add(new ArrayList<>());
        }

        // 3. Distribuir los artículos en los palomares según su año
        for (Articulo a : arr) {
            int anio = getSafeAnio(a);
            // El índice es la posición del año relativo al año mínimo
            int holeIndex = anio - min;
            holes.get(holeIndex).add(a);
        }

        // 4. Ordenar internamente y fusionar de vuelta al arreglo original
        int index = 0;
        for (List<Articulo> hole : holes) {

            // CRUCIAL: Usar el ArticuloComparator para ordenar los elementos
            // dentro del palomar (por título, si los años son iguales).
            Collections.sort(hole, c);

            // Copiar los elementos ordenados de vuelta al arreglo
            for (Articulo articulo : hole) {
                arr[index++] = articulo;
            }
        }
    }

    private static int getSafeAnio(Articulo a) {
        try {
            return Integer.parseInt(a.getAnio());
        } catch (NumberFormatException e) {
            return 0; // Año inválido
        }
    }


}



