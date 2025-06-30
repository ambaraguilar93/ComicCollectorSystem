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
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Representa a un cliente en el sistema, con capacidades para reservar y comprar cómics.
 * Hereda de {@link Usuario} y define permisos restringidos para administración de cómics.
 * <p>
 * El RUT se valida localmente, y las operaciones están limitadas según permisos de cliente.
 * </p>
 * 
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public class Cliente extends Usuario {

    /**
     * Constructor que inicializa un cliente con tipo de usuario.
     *
     * @param rut         RUT del cliente.
     * @param nombre      Nombre del cliente.
     * @param apellido    Apellido del cliente.
     * @param tipoUsuario Tipo de usuario (por ejemplo, "Cliente").
     * @param opcion      Código numérico para el tipo de usuario.
     */
    public Cliente(String rut, String nombre, String apellido, String tipoUsuario, int opcion) {
        super(rut, nombre, apellido, tipoUsuario, opcion);
    }

    /**
     * Constructor que determina el tipo de usuario según la opción.
     *
     * @param rut      RUT del cliente.
     * @param nombre   Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param opcion   Código numérico para el tipo de usuario.
     */
    public Cliente(String rut, String nombre, String apellido, int opcion) {
        super(rut, nombre, apellido, opcion);
    }

    /**
     * Valida el formato del RUT.
     *
     * @param rut RUT a validar.
     * @return {@code true} si el RUT es no nulo, no vacío y coincide con formato esperado; {@code false} en otro caso.
     */
    private boolean validarRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }
        if (rut.length() != 11 && rut.length() != 12) {
            return false;
        }
        return rut.matches("\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}");
    }

    /**
     * Registra un nuevo usuario cliente en el sistema.
     *
     * @param rut      RUT del usuario a registrar.
     * @param usuario  Instancia de {@link Usuario} a registrar.
     * @param usuarios Mapa de usuarios existentes.
     * @return {@code true} si el registro fue exitoso; {@code false} en caso de error o duplicados.
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
     * Retorna información del cliente.
     *
     * @return Cadena con RUT, nombre, apellido y tipo de usuario.
     */
    @Override
    public String verInformacionUsuario() {
        return this.getRut() + " - " + this.getNombre()
                + " " + this.getApellido() + " " + this.getTipoUsuario();
    }

    /**
     * No permite agregar cómics en esta clase.
     *
     * @throws InputMismatchException Siempre, porque el cliente no tiene permiso.
     */
    @Override
    public void agregarComic(ArrayList<Comic> comics, String titulo, String autor,
                             String editorial, int precio, String tipoComic)
            throws InputMismatchException {
        throw new InputMismatchException("Este usuario no puede agregar comics.");
    }

    /**
     * No permite eliminar cómics en esta clase.
     *
     * @throws InputMismatchException Siempre, porque el cliente no tiene permiso.
     */
    @Override
    public void eliminarComic(ArrayList<Comic> comics, String codigo)
            throws InputMismatchException {
        throw new InputMismatchException("Este usuario no puede eliminar comics.");
    }

    /**
     * Compra todos los cómics reservados, moviéndolos a ventas y calculando el total.
     *
     * @param reservas Lista de cómics reservados.
     * @param ventas   Lista de cómics vendidos.
     * @throws ComicYaCompradoException Si no hay reservas disponibles.
     */
    @Override
    public void comprarComic(List<Comic> reservas, List<Comic> ventas)
            throws ComicYaCompradoException {
        if (reservas == null || reservas.isEmpty()) {
            throw new ComicYaCompradoException("No hay reservas para mostrar.");
        }
        int sumaPrecios = 0;
        HashSet<Comic> copiaReservas = new HashSet<>(reservas);
        for (Comic comic : copiaReservas) {
            sumaPrecios += comic.getPrecio();
            ventas.add(comic);
            reservas.remove(comic);
        }
        System.out.println("Total a pagar: " + sumaPrecios);
    }

    /**
     * Reserva un cómic moviéndolo de la lista disponible a reservas.
     *
     * @param comics   Lista actual de cómics disponibles.
     * @param reservas Lista donde se almacenan las reservas.
     * @param codigo   Código del cómic a reservar.
     * @throws ComicYaReservadoException Si falta información o no hay cómics disponibles.
     */
    @Override
    public void reservarComic(List<Comic> comics, List<Comic> reservas, String codigo)
            throws ComicYaReservadoException {
        if (codigo == null || codigo.isEmpty()) {
            throw new ComicYaReservadoException("El codigo no puede estar vacio.");
        }
        if (comics == null || comics.isEmpty()) {
            throw new ComicYaReservadoException("No hay comics para mostrar");
        }
        if (reservas == null) {
            throw new ComicYaReservadoException("No hay reservas para mostrar.");
        }
        Integer index = null;
        for (int i = 0; i < comics.size(); i++) {
            Comic comic = comics.get(i);
            if (comic.getCodigo().equalsIgnoreCase(codigo)) {
                index = i;
                break;
            }
        }
        if (index == null) {
            return;
        }
        Comic comicIndexado = comics.get(index);
        reservas.add(comicIndexado);
        comics.remove(comicIndexado);
    }
}