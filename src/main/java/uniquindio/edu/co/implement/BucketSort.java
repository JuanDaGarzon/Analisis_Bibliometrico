package uniquindio.edu.co.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniquindio.edu.co.model.Articulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

@Service
@RequiredArgsConstructor
public class BucketSort {

    public static void bucketSort(Articulo[] arr, Comparator<Articulo> c) {
        int n = arr.length;
        if (n<=0)return;

        // Encontrar el Rango de años para definir las cubetas
        int maxAnio = getSafeAnio(arr[0]);
        int minAnio = getSafeAnio(arr[0]);

        for (Articulo a : arr) {
            int anio = getSafeAnio(a);
            if (anio > maxAnio) maxAnio = anio;
            if (anio < minAnio) minAnio = anio;
        }

        // Si todos los años son el mismo (o solo hay un artículo), se usa 1 cubeta
        int bucketCount = maxAnio - minAnio + 1;

        // Inicializar
        List<List<Articulo>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        // Distribuir los artículos en las cubetas según el año
        for(Articulo a : arr) {
            int anio = getSafeAnio(a);
            int bucketIndex = anio -minAnio;
            buckets.get(bucketIndex).add(a);
        }
        // Ordenar las cubetas y fusionarlas de nuevo al arreglo original
        int i = 0;
        for (List<Articulo> bucket : buckets) {
            Collections.sort(bucket,c);
            for (Articulo a : bucket) {
                arr[i++] = a;
            }
        }
    }

    private static int getSafeAnio(Articulo a) {
        try {
            // Asume que Articulo tiene un método getAnio() que devuelve String
            return Integer.parseInt(a.getAnio());
        } catch (NumberFormatException e) {
            return 0; // Usar 0 para artículos con año inválido
        }
    }

}



