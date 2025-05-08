package uniquindio.edu.co.model;

public class Articulo {
    String titulo, autor, anio, publicacion, paginas, serie, url, topico;

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

    public String generarClave() {
        return (titulo + autor + anio).toLowerCase().replaceAll("\\s+", "");
    }

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

    private String safe(String val) {
        return val == null ? "" : val;
    }
}
