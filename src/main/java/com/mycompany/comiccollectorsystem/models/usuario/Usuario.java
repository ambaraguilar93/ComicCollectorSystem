/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comiccollectorsystem.models.usuario;

import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaCompradoException;
import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaReservadoException;
import com.mycompany.comiccollectorsystem.models.comic.Comic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Clase abstracta que representa un usuario en el sistema.
 * Contiene información básica como RUT, nombre, apellido y tipo de usuario.
 * Define métodos abstractos que deben ser implementados por las subclases.
 *
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public abstract class Usuario {
    private String rut;
    private String nombre;
    private String apellido;
    private String tipoUsuario;
    private int opcion;

    /**
     * Constructor que inicializa todos los atributos del usuario.
     *
     * @param rut         RUT del usuario.
     * @param nombre      Nombre del usuario.
     * @param apellido    Apellido del usuario.
     * @param tipoUsuario Tipo de usuario (por ejemplo, "Administrador" o "Cliente").
     * @param opcion      Opción numérica asociada al tipo de usuario.
     */
    public Usuario(String rut, String nombre, String apellido, String tipoUsuario, int opcion) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = tipoUsuario;
        this.opcion = opcion;
    }

    /**
     * Constructor que inicializa los atributos básicos del usuario y determina el tipo de usuario según la opción.
     *
     * @param rut      RUT del usuario.
     * @param nombre   Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param opcion   Opción numérica para determinar el tipo de usuario.
     */
    public Usuario(String rut, String nombre, String apellido, int opcion) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = crearTipoUsuario(opcion);
    }

    /**
     * Obtiene el RUT del usuario.
     *
     * @return RUT del usuario.
     */
    public String getRut() {
        return rut;
    }

    /**
     * Establece el RUT del usuario.
     *
     * @param rut Nuevo RUT del usuario.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre Nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return Apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param apellido Nuevo apellido del usuario.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el tipo de usuario.
     *
     * @return Tipo de usuario.
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Establece el tipo de usuario.
     *
     * @param tipoUsuario Nuevo tipo de usuario.
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Obtiene la opción numérica asociada al tipo de usuario.
     *
     * @return Opción numérica del usuario.
     */
    public int getOpcion() {
        return opcion;
    }

    /**
     * Establece la opción numérica asociada al tipo de usuario.
     *
     * @param opcion Nueva opción numérica del usuario.
     */
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /**
     * Determina el tipo de usuario basado en la opción proporcionada.
     *
     * @param opcion Opción numérica para determinar el tipo de usuario.
     * @return Tipo de usuario correspondiente a la opción.
     */
    public String crearTipoUsuario(int opcion) {
        if (opcion == 1) {
            return "Administrador";
        }
        if (opcion == 2) {
            return "Cliente";
        }
        return "No válido.";
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param rut      RUT del usuario.
     * @param usuario  Objeto Usuario a registrar.
     * @param usuarios Mapa de usuarios existentes.
     * @return true si el registro fue exitoso; false en caso contrario.
     */
    public abstract boolean registrarUsuario(String rut, Usuario usuario, HashMap<String, Usuario> usuarios);

    /**
     * Muestra la información del usuario.
     *
     * @return Cadena con la información del usuario.
     */
    public abstract String verInformacionUsuario();

    /**
     * Agrega un nuevo cómic a la lista.
     *
     * @param comics    Lista de cómics existentes.
     * @param titulo    Título del cómic.
     * @param autor     Autor del cómic.
     * @param editorial Editorial del cómic.
     * @param precio    Precio del cómic.
     * @param tipoComic Tipo de cómic.
     * @throws InputMismatchException Si hay un error en los datos ingresados.
     */
    public abstract void agregarComic(ArrayList<Comic> comics, String titulo, String autor, String editorial,
                                      int precio, String tipoComic) throws InputMismatchException;

    /**
     * Elimina un cómic de la lista según su código.
     *
     * @param comics Lista de cómics existentes.
     * @param codigo Código del cómic a eliminar.
     * @throws InputMismatchException Si hay un error en los datos ingresados.
     */
    public abstract void eliminarComic(ArrayList<Comic> comics, String codigo) throws InputMismatchException;

    /**
     * Reserva un cómic de la lista.
     *
     * @param comics   Lista de cómics disponibles.
     * @param reservas Lista de cómics reservados.
     * @param codigo   Código del cómic a reservar.
     * @throws ComicYaReservadoException Si el cómic ya está reservado.
     */
    public abstract void reservarComic(List<Comic> comics, List<Comic> reservas, String codigo) throws ComicYaReservadoException;

    /**
     * Compra un cómic de la lista de reservas.
     *
     * @param reservas Lista de cómics reservados.
     * @param ventas   Lista de cómics vendidos.
     * @throws ComicYaCompradoException Si el cómic ya ha sido comprado.
     */
    public abstract void comprarComic(List<Comic> reservas, List<Comic> ventas) throws ComicYaCompradoException;
}
