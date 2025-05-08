package uniquindio.edu.co;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        guardarBibtex(duplicados, "target/salida/duplicados.bib", true);

        System.out.println("✅ Consolidación completada. Únicos: " + unicos.size() + ", Duplicados: " + duplicados.size());
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
        String titulo = extraerCampo(entrada, "title");
        String autor = extraerCampo(entrada, "author");
        String anio = extraerCampo(entrada, "year");
        String publicacion = extraerCampo(entrada, "journal");
        String paginas = extraerCampo(entrada, "pages");
        String serie = extraerCampo(entrada, "series");
        String url = extraerCampo(entrada, "url");
        String topico = extraerCampo(entrada, "keywords");

        return new Articulo(titulo, autor, anio, publicacion, paginas, serie, url, topico);
    }

    private static String extraerCampo(String entrada, String campo) {
        int i = entrada.indexOf(campo + " =");
        if (i == -1) return "";
        int inicio = entrada.indexOf("{", i);
        int fin = entrada.indexOf("}", inicio);
        if (inicio != -1 && fin != -1) {
            return entrada.substring(inicio + 1, fin).trim();
        }
        return "";
    }

    private static void guardarBibtex(List<Articulo> articulos, String outputPath, boolean duplicado) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (int i = 0; i < articulos.size(); i++) {
                writer.write(articulos.get(i).aBibtex(i + 1, duplicado));
                writer.newLine();
                writer.newLine();
            }
        }
    }
}
