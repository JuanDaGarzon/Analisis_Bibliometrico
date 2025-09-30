package uniquindio.edu.co;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uniquindio.edu.co.implement.*;
import uniquindio.edu.co.implement.TimSort;
import uniquindio.edu.co.model.Articulo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(App.class, args);

        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("articulos.bib");

        if (inputStream == null) {
            throw new FileNotFoundException("No se encontró el archivo articulos.bib en resources.");
        }

        String contenido = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(contenido);

        List<Articulo> articulos = leerBibtex(contenido);
        List<Articulo> unicos = new ArrayList<>();
        List<Articulo> duplicados = new ArrayList<>();
        Set<String> claves = new HashSet<>();

        for (Articulo art : articulos) {
            String clave = art.generarClave();
            if (claves.add(clave)) {
                unicos.add(art);
            } else {
                duplicados.add(art);
            }
        }

        guardarBibtex(unicos, "target/salida/consolidado_unico.bib", false);
        guardarBibtex(duplicados, "target/salida/duplicados.bib",  true);

        System.out.println("\nConsolidación completada. Únicos: " + unicos.size() + ". Duplicados: " + duplicados.size()
                + ". Total: " + (unicos.size() + duplicados.size()));

        //BinaryInsertionSort
        // Convertir lista en array
        Articulo[] articulosArray = articulos.toArray(new Articulo[0]);

        //BinaryInsertionSort
        long inicio = System.nanoTime();
        BinaryInsertionSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        long fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución BinaryInsertionSort: " + (fin - inicio) + " ns");

        //BitonicSort
        inicio = System.nanoTime();
        BitonicSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución BitonicSort: " + (fin - inicio) + " ns");

        //BucketSort
        inicio = System.nanoTime();
        BucketSort.bucketSort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución BucketSort: " + (fin - inicio) + " ns");

        //CombSort
        inicio = System.nanoTime();
        CombSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución CombSort: " + (fin - inicio) + " ns");

        //GnomeSort
        inicio = System.nanoTime();
        GnomeSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución GnomeSort: " + (fin - inicio) + " ns");

        //PingeonholeSort
        inicio = System.nanoTime();
        PigeonholeSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución PingeonholeSort: " + (fin - inicio) + " ns");

        //QuickSort
        inicio = System.nanoTime();
        QuickSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución QuickSort: " + (fin - inicio) + " ns");

        //RadixSort
        inicio = System.nanoTime();
        // Llamada al nuevo RadixSort (no necesita el Comparator en la firma)
        RadixSort.radixSort(articulosArray);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución RadixSort: " + (fin - inicio) + " ns");

        //SelectionSort
        inicio = System.nanoTime();
        SelectionSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución SelectionSort: " + (fin - inicio) + " ns");

        //TimSort
        inicio = System.nanoTime();
        TimSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución TimSort: " + (fin - inicio) + " ns");

        //TreeSort
        inicio = System.nanoTime();
        TreeSort.sort(articulosArray, ArticuloComparator.POR_ANIO_Y_TITULO);
        fin = System.nanoTime();
        System.out.println("\ntiempo de ejecución TreeSort: " + (fin - inicio) + " ns");

        // Contar la frecuencia
        Map<String, Integer> mapaFrecuencias = contarAutores(articulos);
        //Convertir el Map a una lista de Entries
        List<Map.Entry<String, Integer>> listaAutores = new ArrayList<>(mapaFrecuencias.entrySet());
        //Ordenar
        Collections.sort(listaAutores, new AutorFrecuenciaComparator());
        //Seleccionar los 15 autores con MÁS apariciones
        int totalAutores = listaAutores.size();
        int i = Math.max(0, totalAutores - 15); // Asegura que no sea un índice negativo
        List<Map.Entry<String, Integer>> top15MasFrecuentes = listaAutores.subList(i, totalAutores);
        //Ordenar los top 15 de MENOR a MAYOR frecuencia
        System.out.println("\nLos 15 autores con más apariciones (ordenados por frecuencia ascendente):");
        for (Map.Entry<String, Integer> entry : top15MasFrecuentes) {
            System.out.println("Autor: " + entry.getKey() + " | Apariciones: " + entry.getValue());
        }

    }
    public static List<Articulo> leerBibtex(String contenido) {
        List<Articulo> articulos = new ArrayList<>();
        String[] entradas = contenido.split("@");

        for (String entrada : entradas) {
            if (entrada.trim().isEmpty()) continue;
            entrada = "@" + entrada;
            Articulo articulo = parseEntrada(entrada);
            if (articulo != null) {
                articulos.add(articulo);
            }
        }

        return articulos;
    }

    private static Articulo parseEntrada(String entrada) {
        String titulo = extraerCampo(entrada,  "title");
        String autor = extraerCampo(entrada,  "author");
        String anio = extraerCampo(entrada,  "year");
        String publicacion = extraerCampo(entrada,  "journal");
        String paginas = extraerCampo(entrada,  "pages");
        String serie = extraerCampo(entrada,  "series");
        String url = extraerCampo(entrada,  "url");
        String topico = extraerCampo(entrada,  "keywords");

        return new Articulo(titulo, autor, anio, publicacion, paginas, serie, url, topico);
    }

    private static String extraerCampo(String entrada, String campo) {
        // Busca el campo en el formato 'campo = {' o 'campo = "'
        int i = entrada.indexOf(campo + " =");
        if (i == -1) return "";

        int inicioValor = -1;
        char delimitador = 0;

        // Buscar el inicio del valor (puede ser '{' o '"')
        for (int j = i + campo.length() + 2; j < entrada.length(); j++) {
            char c = entrada.charAt(j);
            if (c == '{' || c == '"') {
                inicioValor = j;
                delimitador = c;
                break;
            }
            if (!Character.isWhitespace(c)) break; // Si encuentra algo que no es espacio, el formato es incorrecto
        }

        if (inicioValor == -1) return "";

        // Determinar el delimitador de cierre
        char delimitadorFin = (delimitador == '{') ? '}' : '"';

        // Buscar el fin del valor
        int finValor = entrada.indexOf(delimitadorFin, inicioValor + 1);

        // Si no encuentra el fin, o el inicio no se encontró bien
        if (finValor == -1 || inicioValor == -1) return "";

        // Retorna el valor entre los delimitadores, quitando el delimitador de inicio y fin.
        return entrada.substring(inicioValor + 1, finValor).trim();
    }

    private static void guardarBibtex(List<Articulo> articulos, String outputPath, boolean duplicado) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (int i = 0; i < articulos.size(); i++) {
                writer.write(articulos.get(i).aBibtex( i + 1, duplicado));
                writer.newLine();
                writer.newLine();
            }
        }
    }


    public static Map<String, Integer> contarAutores(List<Articulo> articulos) {
        Map<String, Integer> frecuenciaAutores = new HashMap<>();

        for (Articulo articulo : articulos) {
            String autoresCadena = articulo.getAutor();

            // Comprobar que no esté vacío. Si es vacío, se incrementará la cuenta para ""
            if (autoresCadena.isEmpty()) {
                frecuenciaAutores.put("", frecuenciaAutores.getOrDefault("", 0) + 1);
                continue; // Si ya lo contamos, pasamos al siguiente artículo
            }

            // Dividir por ' and ' (estándar de BibTeX) y limpiar espacios/comas
            String[] autores = autoresCadena.split(" and ");

            for (String autor : autores) {
                String autorLimpio = autor.trim();
                if (!autorLimpio.isEmpty()) {
                    frecuenciaAutores.put(autorLimpio, frecuenciaAutores.getOrDefault(autorLimpio, 0) + 1);
                }
            }
        }
        return frecuenciaAutores;
    }
}