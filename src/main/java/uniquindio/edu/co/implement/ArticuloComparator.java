package uniquindio.edu.co.implement;

import uniquindio.edu.co.model.Articulo;

import java.util.Comparator;

public class ArticuloComparator  {
    public static final Comparator<Articulo> POR_ANIO_Y_TITULO =
            Comparator.comparingInt((Articulo a) -> {
                try {
                    return Integer.parseInt(a.getAnio()); // convertir String a int
                } catch (NumberFormatException e) {
                    return 0; // si no se puede convertir, usar 0
                }
            }).thenComparing(Articulo::getTitulo, String.CASE_INSENSITIVE_ORDER);
}
