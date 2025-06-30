/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comiccollectorsystem.models.usuario.tipos;

import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaCompradoException;
import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaReservadoException;
import com.mycompany.comiccollectorsystem.models.comic.Comic;
import com.mycompany.comiccollectorsystem.models.usuario.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Clase que representa a un administrador en el sistema.
 * Hereda de {@link Usuario} y añade validaciones específicas para rut y cómics.
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public class Administrador extends Usuario {

    /**
     * Constructor que inicializa al administrador con tipo de usuario explícito.
     *
     * @param rut         RUT del administrador.
     * @param nombre      Nombre del administrador.
     * @param apellido    Apellido del administrador.
     * @param tipoUsuario Tipo de usuario (ej. "Administrador").
     * @param opcion      Código numérico para el tipo de usuario.
     */
    public Administrador(String rut, String nombre, String apellido, String tipoUsuario, int opcion) {
        super(rut, nombre, apellido, tipoUsuario, opcion);
    }

    /**
     * Constructor que determina el tipo de usuario según la opción.
     *
     * @param rut      RUT del administrador.
     * @param nombre   Nombre del administrador.
     * @param apellido Apellido del administrador.
     * @param opcion   Código numérico para el tipo de usuario.
     */
    public Administrador(String rut, String nombre, String apellido, int opcion) {
        super(rut, nombre, apellido, opcion);
    }

    /**
     * Valida el formato del RUT.
     *
     * @param rut RUT a validar.
     * @return {@code true} si el RUT sigue el formato correcto; {@code false} en otro caso.
     */
    private boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }
        if (rut.length() != 11 && rut.length() != 12) {
            return false;
        }
        if (!rut.matches("\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}")) {
            return false;
        }
        return true;
    }

    /**
     * Registra un nuevo usuario en el sistema si todos los datos son válidos.
     *
     * @param rut      RUT del nuevo usuario.
     * @param usuario  Instancia de {@link Usuario} a registrar.
     * @param usuarios Mapa de usuarios existentes (clave: rut).
     * @return {@code true} si el registro fue exitoso; {@code false} si hubo algún problema.
     */
    @Override
    public boolean registrarUsuario(String rut, Usuario usuario, HashMap<String, Usuario> usuarios) {
        if (rut == null || rut.isEmpty()) {
            System.out.println("El RUT no puede estar vacío.");
            return false;
        }
        if (usuario == null) {
            System.out.println("El usuario no puede ser null.");
            return false;
        }
        if (usuarios == null) {
            return false;
        }
        if (!validarRut(rut)) {
            System.out.println("El RUT '" + rut + "' no es válido.");
            return false;
        }
        if (usuarios.containsKey(rut)) {
            System.out.println("El RUT '" + rut + "' ya está registrado.");
            return false;
        }
        usuarios.put(rut, usuario);
        System.out.println("Cliente registrado correctamente: "
                + usuario.getNombre() + " " + usuario.getApellido());
        return true;
    }

    /**
     * Devuelve una representación en texto del administrador.
     *
     * @return Cadena con datos: RUT, nombre, apellido y tipo de usuario.
     */
    @Override
    public String verInformacionUsuario() {
        return this.getRut() + " - " + this.getNombre() + " " + this.getApellido() + " " + this.getTipoUsuario();
    }

    /**
     * Valida los datos de un cómic antes de añadirlo.
     *
     * @param titulo     Título del cómic.
     * @param autor      Autor del cómic.
     * @param editorial  Editorial del cómic.
     * @param precio     Precio del cómic.
     * @param tipoComic  Tipo o categoría del cómic.
     * @return {@code true} si todos los datos son válidos; {@code false} en caso contrario.
     */
    public boolean validarComic(String titulo, String autor, String editorial, int precio, String tipoComic) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("El campo titulo no puede estar vacio.");
            return false;
        }
        if (autor == null || autor.isEmpty()) {
            System.out.println("El campo autor no puede estar vacio.");
            return false;
        }
        if (editorial == null || editorial.isEmpty()) {
            System.out.println("El campo editorial no puede estar vacio.");
            return false;
        }
        if (precio <= 0) {
            System.out.println("El campo precio debe ser mayor que cero.");
            return false;
        }
        if (tipoComic == null || tipoComic.isEmpty()) {
            System.out.println("El campo tipo Comic no puede estar vacio.");
            return false;
        }
        return true;
    }

    /**
     * Agrega un cómic a la lista si los datos son válidos.
     *
     * @param comics     Lista de cómics existente.
     * @param titulo     Título del cómic.
     * @param autor      Autor del cómic.
     * @param editorial  Editorial del cómic.
     * @param precio     Precio del cómic.
     * @param tipoComic  Tipo o categoría del cómic.
     * @throws InputMismatchException Si ocurre un error de formato en los datos.
     */
    @Override
    public void agregarComic(ArrayList<Comic> comics, String titulo, String autor, String editorial, int precio,
                             String tipoComic) throws InputMismatchException {
        if (validarComic(titulo, autor, editorial, precio, tipoComic)) {
            Comic nuevoComic = new Comic(titulo, autor, editorial, precio, tipoComic);
            comics.add(nuevoComic);
            System.out.println("Comic agregado correctamente.");
            return;
        }
        System.out.println("Error: Comic no fue agregado.");
    }

    /**
     * Elimina un cómic de la lista según su código.
     *
     * @param comics Lista de cómics existente.
     * @param codigo Código del cómic a eliminar.
     * @throws InputMismatchException Si ocurre un error de formato.
     */
    @Override
    public void eliminarComic(ArrayList<Comic> comics, String codigo) throws InputMismatchException {
        if (comics == null || comics.isEmpty()) {
            System.out.println("No hay comics.");
            return;
        }
        if (codigo == null || codigo.isEmpty()) {
            System.out.println("El campo codigo no puede estar vacio.");
            return;
        }
        for (int i = 0; i < comics.size(); i++) {
            Comic libro = comics.get(i);
            if (libro.getCodigo().equalsIgnoreCase(codigo)) {
                comics.remove(i);
                System.out.println("El comic con el codigo " + codigo + " fue eliminado.");
                return;
            }
        }
        System.out.println("No se encontro ningun comic con el codigo " + codigo);
    }

    /**
     * No permite que un administrador compre cómics.
     *
     * @param reservas Lista de cómics reservados.
     * @param ventas   Lista de cómics vendidos.
     * @throws ComicYaCompradoException Siempre, ya que un administrador no puede comprar.
     */
    @Override
    public void comprarComic(List<Comic> reservas, List<Comic> ventas) throws ComicYaCompradoException {
        throw new ComicYaCompradoException("Este usuario no tiene permisos para comprar comics.");
    }

    /**
     * No permite que un administrador reserve cómics.
     *
     * @param comics   Lista de cómics disponibles.
     * @param reservas Lista de cómics reservados.
     * @param codigo   Código del cómic a reservar.
     * @throws ComicYaReservadoException Siempre, ya que un administrador no puede reservar.
     */
    @Override
    public void reservarComic(List<Comic> comics, List<Comic> reservas, String codigo) throws ComicYaReservadoException {
        throw new ComicYaReservadoException("Este usuario no tiene permisos para reservar comics.");
    }
}