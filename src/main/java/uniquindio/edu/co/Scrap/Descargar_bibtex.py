import requests
import time

# Ruta donde se guardará el archivo BibTeX dentro de tu proyecto Java
ruta_bibtex = r"C:\Proyectos\Analisis\Analisis_bibliometrico\src\main\resources\articulos.bib"

# Palabra clave de búsqueda
query = "generative artificial intelligence"

# Límite total de artículos
limite_total = 5000

# Límite máximo por petición permitido por CrossRef
limite_por_pagina = 100

# URL base de CrossRef
url_base = "https://api.crossref.org/works"

def descargar_bibtex():
    with open(ruta_bibtex, "w", encoding="utf-8") as f:
        contador = 0
        offset = 0

        while contador < limite_total:
            # Calcular cuántos traer en esta página
            cantidad = min(limite_por_pagina, limite_total - contador)

            # Construir la URL con paginación
            url = f"{url_base}?query={query}&rows={cantidad}&offset={offset}"
            print(f"Descargando: {url}")

            resp = requests.get(url, headers={"User-Agent": "Mozilla/5.0"})
            if resp.status_code != 200:
                print(f"Error {resp.status_code} al acceder a {url}")
                break

            data = resp.json()
            items = data["message"]["items"]

            # Procesar cada artículo
            for i, item in enumerate(items):
                title = item.get("title", [""]).pop(0)
                author_list = item.get("author", [])
                authors = " and ".join(
                    [f"{a.get('given','')} {a.get('family','')}" for a in author_list]
                )
                year = item.get("issued", {}).get("date-parts", [[None]])[0][0]
                journal = item.get("container-title", [""]).pop(0)
                pages = item.get("page", "")
                series = item.get("event", {}).get("name", "")
                url_art = item.get("URL", "")
                keywords = ", ".join(item.get("subject", []))

                # Escribir en formato BibTeX
                f.write(f"@article{{ref{contador+i},\n")
                f.write(f"  title = {{{title}}},\n")
                f.write(f"  author = {{{authors}}},\n")
                f.write(f"  year = {{{year}}},\n")
                f.write(f"  journal = {{{journal}}},\n")
                f.write(f"  pages = {{{pages}}},\n")
                f.write(f"  series = {{{series}}},\n")
                f.write(f"  url = {{{url_art}}},\n")
                f.write(f"  keywords = {{{keywords}}}\n")
                f.write("}\n\n")

            # Avanzar paginación
            contador += cantidad
            offset += cantidad
            time.sleep(1)  # Pausa para no saturar CrossRef

    print(f"\n✅ Archivo BibTeX guardado en {ruta_bibtex} con {contador} artículos.")


if __name__ == "__main__":
    descargar_bibtex()
