/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comiccollectorsystem.manager;

import com.mycompany.comiccollectorsystem.models.usuario.Usuario;
import java.util.HashMap;

/**
 * Administrador central del sistema de cómics.
 * <p>
 * Implementa el patrón Singleton para garantizar una única instancia,
 * administrando usuarios mediante un mapa global.
 * </p>
 *
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public class ComicSystemManager {
    /** Mapa que asocia RUT a instancia de {@link Usuario}. */
    private HashMap<String, Usuario> usuarios;
     /** Instancia única de la clase (Singleton). */
    private static ComicSystemManager instancia;

    /**
     * Constructor privado para prevenir instanciación externa
     * y asegurar que solo exista una instancia (patrón Singleton).
     */
    private ComicSystemManager() {
        this.usuarios = new HashMap<>();
    }
    
    /**
     * Devuelve la instancia única de {@code ComicSystemManager}.
     * Si no existe, se crea (inicialización perezosa).
     * Esta técnica es un enfoque común del patrón Singleton :contentReference[oaicite:2]{index=2}.
     *
     * @return instancia única de {@code ComicSystemManager}.
     */
    public static ComicSystemManager getInstancia() {
        if (instancia == null) {
            instancia = new ComicSystemManager();
        }
        return instancia;
    }

    /**
     * Busca un usuario por su RUT.
     *
     * @param rut Identificador único del usuario.
     * @return la instancia {@link Usuario} si está registrada, o {@code null} si no se encuentra.
     */
    public Usuario buscarUsuario(String rut) {
        return usuarios.get(rut);
    }

    /**
     * Agrega un nuevo usuario al sistema.
     * Si ya existe un usuario con el mismo RUT, lo sobrescribe.
     *
     * @param usuario Instancia de {@link Usuario} a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getRut(), usuario);
    }

    /**
     * Imprime en consola la información del usuario identificado por el RUT.
     * Usa {@link Usuario#verInformacionUsuario()} para obtener el formato de salida.
     *
     * @param rut RUT del usuario cuya información se desea mostrar.
     */
    public void verInformacionUsuario(String rut) {
        Usuario usuarioActivo = usuarios.get(rut);
        System.out.println(usuarioActivo.verInformacionUsuario());
    }

}
