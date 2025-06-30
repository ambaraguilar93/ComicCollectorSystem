/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comiccollectorsystem.models.comic;

import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicNoEncontradoException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un cómic con atributos como código, título, autor, editorial, precio y tipo.
 * Implementa {@link Comparable} para permitir orden alfabético por título.
 * <p>
 * Proporciona también métodos estáticos para buscar por código y leer/escribir desde/hacia CSV.
 * </p>
 *
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public class Comic implements Comparable<Comic>{
    /**
     * Código único del cómic.
     */
    private String codigo;
    private static final SecureRandom random = new SecureRandom();
    private static final Set<String> usados = new HashSet<>();
    private String titulo;
    private String autor;
    private String editorial;
    private int precio;
    private String tipoComic;

    /**
     * Constructor que admite código.
     *
     * @param codigo     Código del cómic.
     * @param titulo     Título del cómic.
     * @param autor      Autor del cómic.
     * @param editorial  Editorial que publica el cómic.
     * @param precio     Precio en pesos.
     * @param tipoComic  Categoría del cómic (manga, comic, etc.).
     */
    public Comic(String codigo, String titulo, String autor, String editorial, int precio, String tipoComic) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.precio = precio;
        this.tipoComic = tipoComic;
    }

    /**
     * Constructor que genera un código único automáticamente.
     *
     * @param titulo     Título del cómic.
     * @param autor      Autor del cómic.
     * @param editorial  Editorial que publica el cómic.
     * @param precio     Precio en pesos.
     * @param tipoComic  Categoría del cómic.
     */
    public Comic(String titulo, String autor, String editorial, int precio, String tipoComic) {
        this.codigo = generarCodigoUnico();
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.precio = precio;
        this.tipoComic = tipoComic;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getTipoComic() {
        return tipoComic;
    }

    public void setTipoComic(String tipoComic) {
        this.tipoComic = tipoComic;
    }

    /**
     * Determina el tipo de cómic según una opción numérica.
     *
     * @param opcion Número que representa el tipo.
     * @return String con la categoría del cómic o "No valido.".
     */   
    public String crearTipoComic(int opcion) {

        if (opcion == 1) {
            return "comic";
        }

        if (opcion == 2) {
            return "novela ligera";
        }

        if (opcion == 3) {
            return "manga";
        }

        if (opcion == 4) {
            return "ciencia ficcion";
        }

        return "No valido.";
    }

    /**
     * Genera un código único para el cómic, evitando repeticiones.
     *
     * @return Código nuevo válido.
     */
    private String generarCodigoUnico() {
        String codigo;
        do {
            int aleatorio = random.nextInt(9000) + 1000;
            codigo = "IDCOM" + aleatorio;
        } while (!usados.add(codigo));
        return codigo;
    }

    /**
     * Busca un cómic en la lista por su código.
     *
     * @param comics Lista donde buscar.
     * @param codigo Código del cómic a encontrar.
     * @return Instancia encontrada.
     * @throws ComicNoEncontradoException Si no se encuentra o lista/código inválidos.
     */
    public static Comic buscarComicPorCodigo(ArrayList<Comic> comics, String codigo) throws ComicNoEncontradoException {

        if (comics == null || comics.isEmpty()) {
            throw new ComicNoEncontradoException("No hay comics en la lista");
        }

        if (codigo == null || codigo.isEmpty()) {
            throw new ComicNoEncontradoException("El nombre del comic a buscar no puede estar vacío.");
        }

        for (Comic comic : comics) {
            if (comic.getCodigo().equalsIgnoreCase(codigo)) {
                return comic;
            }
        }

        throw new ComicNoEncontradoException("Comic llamado " + codigo + " no encontrado.");
    }

    /**
     * Devuelve una cadena con los detalles del cómic.
     *
     * @return Información formateada del cómic.
     */
    public String mostrarInformacionComic() {
        return codigo + " | " + titulo + " | " + autor + " | " + " | " + editorial + " | " + " | " + precio + " | "
                + " | " + tipoComic;
    }

    /**
     * Carga cómics desde un archivo CSV con encabezados específicos.
     *
     * @return Lista de cómics leída desde el CSV.
     * @throws CsvValidationException Si el CSV no es válido.
     */
    public static ArrayList<Comic> cargarComicsDesdeCSV() throws CsvValidationException {
        ArrayList<Comic> comics = new ArrayList<>();

        // System.out.println(new java.io.File(".").getAbsolutePath());

        try (CSVReader reader = new CSVReader(new FileReader("comic.csv"))) {

            String[] proximaLinea;
            reader.readNext();

            while ((proximaLinea = reader.readNext()) != null) {

                if (proximaLinea.length >= 6) {

                    String codigo = proximaLinea[0];
                    String titulo = proximaLinea[1];
                    String autor = proximaLinea[2];
                    String editorial = proximaLinea[3];
                    int precio = 0;
                    try {
                        precio = Integer.parseInt(proximaLinea[4]);
                    } catch (NumberFormatException e) {
                        // ignorar
                    }
                    String tipoComic = proximaLinea[5];

                    Comic comic = new Comic(codigo, titulo, autor, editorial, precio, tipoComic);
                    comics.add(comic);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return comics;
    }

    /**
     * Guarda la lista de cómics en un archivo CSV, sobrescribiéndolo.
     *
     * @param comics Lista de cómics a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    public static void guardarComicsenCSV(ArrayList<Comic> comics) throws IOException {
        try (
                FileWriter writer = new FileWriter("comic.csv");
                CSVWriter csvWriter = new CSVWriter(writer);) {

            String[] header = { "codigo", "titulo", "autor", "editorial", "precio", "tipoComic" };
            csvWriter.writeNext(header);

            for (Comic comic : comics) {
                String[] data = {
                        comic.getCodigo(),
                        comic.getTitulo(),
                        comic.getAutor(),
                        comic.getEditorial(),
                        String.valueOf(comic.getPrecio()),
                        comic.getTipoComic()

                };
                csvWriter.writeNext(data);
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo.");
        }
    }

    /**
     * Compara dos cómics por título.
     *
     * @param o Otro cómic a comparar.
     * @return Resultado de la comparación lexicográfica.
     */
    @Override
    public int compareTo(Comic o) {
        return this.titulo.compareTo(o.titulo);
    }

}
