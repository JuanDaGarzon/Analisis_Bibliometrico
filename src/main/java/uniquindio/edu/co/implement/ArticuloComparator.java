package uniquindio.edu.co.implement;

import uniquindio.edu.co.model.Articulo;

import java.util.Comparator;

public class ArticuloComparator implements Comparator<Articulo> {

    public static final ArticuloComparator POR_ANIO_Y_TITULO = new ArticuloComparator();

    // Sobreescribimos el método compare de la interfaz Comparator
    public int compare(Articulo a1, Articulo a2) {

        int anio1;
        int anio2;

        try {
            anio1 = Integer.parseInt(a1.getAnio());
        } catch (NumberFormatException e) {
            anio1 = 0;
        }

        try {
            anio2 = Integer.parseInt(a2.getAnio());
        } catch (NumberFormatException e) {
            anio2 = 0;
        }

        // Primero comparar por año
        int resultado = Integer.compare(anio1, anio2);

        // Si los años son iguales, comparar por título sin importar mayúsculas/minúsculas
        if (resultado == 0) {
            resultado = a1.getTitulo().compareToIgnoreCase(a2.getTitulo());
        }

        return resultado;
    }

}

