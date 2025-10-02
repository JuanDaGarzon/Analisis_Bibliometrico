package uniquindio.edu.co.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Atributos
    String titulo, autor, anio, publicacion, paginas, serie, url, topico;

    //Constructor Para Parsear la entrada en App
    public Articulo(String titulo, String autor, String anio, String publicacion, String paginas,
                    String serie, String url, String topico) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.publicacion = publicacion;
        this.paginas = paginas;
        this.serie = serie;
        this.url = url;
        this.topico = topico;
    }

    // Clave para facilitar la lectura de la clase
    public String generarClave() {
        return (titulo + autor + anio).toLowerCase().replaceAll("\\s+", "");
    }

    // Formato Bibtex
    public String aBibtex(int index, boolean duplicado) {
        return String.format("@article{ref%s,\n" +
                        "  title={%s},\n" +
                        "  author={%s},\n" +
                        "  year={%s},\n" +
                        "  journal={%s},\n" +
                        "  pages={%s},\n" +
                        "  series={%s},\n" +
                        "  url={%s},\n" +
                        "  keywords={%s}\n}",
                duplicado ? "dup" + index : index,
                safe(titulo), safe(autor), safe(anio), safe(publicacion),
                safe(paginas), safe(serie), safe(url), safe(topico));
    }

    // Validacion no null para los valores de la clase
    private String safe(String val) {
        return val == null ? "" : val;
    }

}
