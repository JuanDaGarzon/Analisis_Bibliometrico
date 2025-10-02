package uniquindio.edu.co.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniquindio.edu.co.model.Articulo;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RadixSort {

    public static void radixSort(Articulo[] arr) {
        int n = arr.length;
        if (n <= 0) return;

        // 1. Encontrar el año máximo para saber el número de pasadas
        int maxAnio = getSafeAnio(arr[0]);
        for (int i = 1; i < n; i++) {
            int anio = getSafeAnio(arr[i]);
            if (anio > maxAnio) maxAnio = anio;
        }

        // 2. Realizar Counting Sort para cada dígito (pasadas)
        // exp es 1, 10, 100, 1000, etc.
        for (int exp = 1; maxAnio / exp > 0; exp *= 10) {
            countingSort(arr, n, exp);
        }

        // 3. Post-ordenamiento (Opcional pero recomendado para Articulos)
        // Dado que RadixSort solo ordenó por AÑO, los artículos con el MISMO año
        // podrían estar en el orden incorrecto según el TÍTULO.
        // Se requiere un algoritmo de ordenamiento estable (como Insertion Sort)
        // solo para los sub-arreglos que comparten el mismo año, si el Comparator
        // incluye el título. Sin un Comparator, esta implementación está completa.
    }

    private static void countingSort(Articulo[] arr, int n, int exp) {
        // El arreglo de salida debe ser del mismo tipo
        Articulo[] output = new Articulo[n];
        int[] count = new int[10]; // 10 dígitos (0 a 9)
        Arrays.fill(count, 0);

        // 1. Contar ocurrencias (basado en el dígito actual)
        for (int i = 0; i < n; i++) {
            int anio = getSafeAnio(arr[i]);
            // El dígito actual es: (anio / exp) % 10
            count[(anio / exp) % 10]++;
        }

        // 2. Modificar el conteo para guardar la posición real en el output
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 3. Construir el arreglo de salida (ITERANDO HACIA ATRÁS para estabilidad)
        for (int i = n - 1; i >= 0; i--) {
            int anio = getSafeAnio(arr[i]);
            int digit = (anio / exp) % 10;

            // Usar el contador para la posición en el output
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // 4. Copiar los elementos de output de vuelta a arr
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }


    private static int getSafeAnio(Articulo a) {
        try {
            return Integer.parseInt(a.getAnio());
        } catch (NumberFormatException e) {
            return 0; // Usar 0 para años inválidos
        }
    }
}
