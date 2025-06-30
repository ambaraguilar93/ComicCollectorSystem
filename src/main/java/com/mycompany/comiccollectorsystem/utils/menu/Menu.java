/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comiccollectorsystem.utils.menu;

import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicNoEncontradoException;
import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaCompradoException;
import com.mycompany.comiccollectorsystem.exceptions.comicexceptions.ComicYaReservadoException;
import com.mycompany.comiccollectorsystem.manager.ComicSystemManager;
import com.mycompany.comiccollectorsystem.models.comic.Comic;
import com.mycompany.comiccollectorsystem.models.usuario.Usuario;
import com.mycompany.comiccollectorsystem.models.usuario.tipos.Administrador;
import com.mycompany.comiccollectorsystem.models.usuario.tipos.Cliente;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Clase principal que representa el menú del sistema Comic Collector.
 * <p>
 * Permite registrar usuarios, gestionar cómics (agregar, eliminar, buscar), realizar reservas y compras.
 * Usa una consola de texto como interfaz.
 * </p>
 *
 * @author ambar
 * @version 1.0
 * @since 2025-06-30
 */
public class Menu {

    private Scanner scanner;
    private Usuario usuarioActivo;
    private ComicSystemManager comicSystemManager;
    private ArrayList<Comic> comics = new ArrayList<>();;
    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private ArrayList<Comic> reservas = new ArrayList<>();;
    private ArrayList<Comic> ventas = new ArrayList<>();;

    /**
     * Constructor del menú principal. Inicializa dependencias del sistema.
     */
    public Menu() {
        scanner = new Scanner(System.in);
        comicSystemManager = comicSystemManager.getInstancia();
        usuarioActivo = null;
    }

    /**
     * Muestra el menú principal y gestiona la navegación de opciones del sistema.
     */
    public void mostrarMenu() {

        try {
            this.comics = Comic.cargarComicsDesdeCSV();
        } catch (CsvValidationException ex) {
            System.out.println("Error al cargar los comics desde CSV: " + ex.getMessage());
        }
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n===== COMIC COLLECTOR SYSTEM =====");

            System.out.println("\n===== SELECCIONE UNA OPCION =====");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Buscar comic");
            System.out.println("3. Ver informacion usuario");
            System.out.println("4. Ver comics");
            System.out.println("\n===== OPCIONES CLIENTE =====");
            System.out.println("5. Reservar comic");
            System.out.println("6. Comprar comic");
            System.out.println("\n===== OPCIONES ADMIN =====");
            System.out.println("7. Agregar comic");
            System.out.println("8. Eliminar comic");
            System.out.println("9. Generar reporte TXT");
            System.out.println("\n===== SIGN OUT =====");
            System.out.println("10. Salir");
            System.out.println("\n=======================================");
            System.out.println("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    buscarComic();
                    break;
                case 3:
                    verInformacionUsuario();
                    break;
                case 4:
                    verComics();
                    break;
                case 5:
                    reservarComic();
                    break;
                case 6:
                    comprarComic();
                    break;
                case 7:
                    agregarComic();
                    break;
                case 8:
                    eliminarComic();
                    break;
                case 9:
                    exportarReporteTxtUsuariosYVentas();
                    break;
                case 10:
                    System.out.println("Gracias por utilizar la app de biblioteca DUOC UC.");
                    break;

                default:
                    System.out.println("La opcion seleccionada no es valida.");
                    break;
            }

        } while (opcion != 10);
    }

    /**
     * Permite registrar un nuevo usuario (Administrador o Cliente).
     * Realiza validaciones de campos y formato de RUT.
     */
    private void registrarUsuario() {
        System.out.println("\n ===== REGISTRO DE USUARIO =====");

        String rut;
        while (true) {
            System.out.print("Ingrese RUT (12.345.678-9, o deje vacío para cancelar: ");
            rut = scanner.nextLine();

            if (rut.isEmpty()) {
                System.out.println("Registro de cliente cancelado");
                return;
            }

            // Validar RUT y su formato
            if (!rut.matches("\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}")) {
                System.out.println("Error: Formato de RUT no válido, debe ser 11.111.111-1");
                continue;
            }

            // Verificar si el cliente ya existe
            if (comicSystemManager.buscarUsuario(rut) != null) {
                System.out.println("Error: Ya existe un cliente con ese RUT");
                continue;
            } else {
                break;
            }
        }

        String nombre;
        do{
            System.out.println("Ingrese su nombre: ");
            nombre = scanner.nextLine();
            
            if (nombre.isEmpty()) {
                System.out.println("Este campo no puede estar vacio.");
            }
        }while(nombre.isEmpty());
        

        String apellido;
        do{
            System.out.println("Ingrese su apellido: ");
            apellido = scanner.nextLine();
            
            if (apellido.isEmpty()) {
                System.out.println("Este campo no puede estar vacio.");
            }
        }while(apellido.isEmpty());
        

        int opcion;

        while (true) {
            System.out.println("Seleccione perfil");
            System.out.println("1. Administrador | 2. Cliente");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion == 1 || opcion == 2) {
                    break;
                }
                System.out.println("La opcion no es valida.");
                continue;

            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }

        }

        try {
            if (opcion == 1) {
                Usuario nuevoUsuario = new Administrador(rut, nombre, apellido, opcion);
                comicSystemManager.agregarUsuario(nuevoUsuario);
                usuarioActivo = nuevoUsuario;
                usuarioActivo.registrarUsuario(rut, nuevoUsuario, usuarios);
                System.out.println("Registro exitoso.");
            }

            if (opcion == 2) {
                Usuario nuevoUsuario = new Cliente(rut, nombre, apellido, opcion);
                comicSystemManager.agregarUsuario(nuevoUsuario);
                usuarioActivo = nuevoUsuario;
                usuarioActivo.registrarUsuario(rut, nuevoUsuario, usuarios);
                System.out.println("Registro exitoso.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Permite buscar un cómic por código. Muestra su información si se encuentra.
     */
    private void buscarComic() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }

        System.out.println("Ingrese el codigo o titulo del comic: ");
        String busqueda = scanner.nextLine().trim();

        if (busqueda.isEmpty()) {
            System.out.println("Busqueda cancelada.");
            return;
        }

        // buscar por codigo
        try {
            Comic comic = Comic.buscarComicPorCodigo(comics, busqueda);
            System.out.println("Comic encontrado.");
            System.out.println(comic.mostrarInformacionComic());
        } catch (ComicNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Muestra la información del usuario actualmente autenticado.
     */
    private void verInformacionUsuario() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }

        System.out.println("\n===== INFORMACION DE USUARIO =====");
        comicSystemManager.verInformacionUsuario(usuarioActivo.getRut());
    }

    /**
     * Muestra todos los cómics disponibles, los reservados y los vendidos.
     */
    private void verComics() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }

        System.out.println("\n===== LISTA DE COMICS =====");

        if (comics.isEmpty()) {
            System.out.println("No hay comics disponibles.");
        }

        if (!comics.isEmpty()) {
            for (Comic comic : comics) {
                System.out.println(comic.getCodigo() + " - " + comic.getTitulo() + " - " + comic.getAutor() + " - "
                        + comic.getEditorial() + " - " + comic.getTipoComic());
            }
        }
        
        listarComicsReservados();
        listarComicsVendidos();
    }

    /**
     * Permite a un administrador agregar un nuevo cómic al sistema.
     */
    private void agregarComic() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }
        
        if (usuarioActivo.getTipoUsuario() == "Cliente") {
            System.out.println("Este usuario no tiene permisos para agregar comics.");
            return;
        }

        String titulo, autor, editorial, tipoComic;
        int precio;
        
        do {
            System.out.println("Ingrese titulo del Comic");
            titulo = scanner.nextLine().trim();
            
            if (titulo.isEmpty()) {
                System.out.println("El campo no puede quedar vacio.");
            }
        } while (titulo.isEmpty());
        
        
        do{
            System.out.println("Ingrese autor del comic");
            autor = scanner.nextLine().trim();
            
            if (autor.isEmpty()) {
                System.out.println("El campo no puede quedar vacio.");
            }
        } while(autor.isEmpty());
        
        do {
            System.out.println("Ingrese editorial del comic");
            editorial = scanner.nextLine().trim();
            
            if (editorial.isEmpty()) {
                System.out.println("El campo no puede quedar vacio.");
            }
        }while(editorial.isEmpty());
        

        while (true) {
            System.out.println("Ingrese precio del comic");
            String precioStr = scanner.nextLine().trim();
                 
            
            try {
                precio = Integer.parseInt(precioStr);
                if (precio <= 0) {
                    System.out.println("El precio no puede ser cero o menor.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese numeros enteros.");
            }
        }

        int opcion;

        while (true) {
            System.out.println("Eliga el tipo de comic que agregara");
            System.out.println("1. Comic | 2. Novela grafica | 3. Superheroes | 4. Ciencia Ficcion");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion == 1) {
                    tipoComic = "comic";
                    break;
                }

                if (opcion == 2) {
                    tipoComic = "novela grafica";
                    break;
                }

                if (opcion == 3) {
                    tipoComic = "superheroes";
                    break;
                }

                if (opcion == 4) {
                    tipoComic = "ciencia ficcion";
                    break;
                }
                System.out.println("La opcion no es valida.");
                continue;

            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero.");
            }
        }
        try{
            usuarioActivo.agregarComic(comics, titulo, autor, editorial, precio, tipoComic);
            guardarCambios();
        } catch(InputMismatchException e ){
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    /**
     * Permite a un administrador eliminar un cómic por su código.
     */
    private void eliminarComic() {

        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }
        
        if (usuarioActivo.getTipoUsuario() == "Cliente") {
            System.out.println("Este usuario no tiene permisos para agregar comics.");
            return;
        }

        String codigo;
  
        do{
            System.out.println("Ingrese el codigo del comic que quiere eliminar");
            codigo = scanner.nextLine().trim();
            
            if (codigo.isEmpty()) {
                System.out.println("El campo no puede quedar vacio.");
            }
        } while(codigo.isEmpty());

        try {
            usuarioActivo.eliminarComic(comics, codigo);
            guardarCambios();

        } catch (InputMismatchException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Permite a un cliente reservar un cómic disponible usando su código.
     */
    private void reservarComic() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }
        
        if (usuarioActivo.getTipoUsuario() == "Administrador") {
            System.out.println("Este usuario no tiene permisos para reservar comics.");
            return;
        }
        
        String codigo;

            System.out.println("Ingrese el codigo del libro que quiere reservar: ");
            codigo = scanner.nextLine();           
      
        try{
            usuarioActivo.reservarComic(comics, reservas, codigo);
            System.out.println("Reserva exitosa.");
        
            for (Comic comic: reservas){
                System.out.println(comic.getCodigo() + " - " + comic.getTitulo() + " - " + comic.getAutor() + " - "
                        + comic.getEditorial() + " - " + comic.getTipoComic());
            }
        } catch(ComicYaReservadoException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Permite a un cliente comprar todos los cómics actualmente reservados.
     */
    private void comprarComic() {
    
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }
        
        if (usuarioActivo.getTipoUsuario() == "Administrador") {
            System.out.println("Este usuario no tiene permisos para comprar comics.");
            return;
        }        
        
        try{
            usuarioActivo.comprarComic(reservas, ventas);
            System.out.println("Usted ha comprado los siguientes comics: .");
        
            for (Comic comic: ventas){
                System.out.println(comic.getCodigo() + " - " + comic.getTitulo() + " - " + comic.getAutor() + " - "
                        + comic.getEditorial() + " - " + comic.getTipoComic());
            }
        } catch (ComicYaCompradoException e){
             System.out.println("Error: " + e.getMessage());
        }
        
    }
    /**
     * Muestra todos los cómics actualmente reservados por el cliente.
     */
    
    private void listarComicsReservados(){
        System.out.println("\n===== LISTA DE COMICS RESERVADOS =====");
        TreeSet<Comic> comicsOrdenados = new TreeSet<>(reservas);
        
        if (reservas == null || reservas.isEmpty()) {
            System.out.println("No hay comics reservados.");
        }
        
        for(Comic comic: comicsOrdenados){
            System.out.println( comic.mostrarInformacionComic() );
        }  
    }
    
    /**
     * Muestra todos los cómics que han sido vendidos durante la sesión.
     */
    private void listarComicsVendidos() {
        System.out.println("\n===== LISTA DE COMICS VENDIDOS =====");  
        
        if (ventas.isEmpty()) {
            System.out.println("No hay comics vendidos.");
        }
        
        for(Comic comic: ventas){
            System.out.println( comic.mostrarInformacionComic() );
        }
    }

    /**
     * Guarda la lista actual de cómics en el archivo CSV.
     */
    private void guardarCambios() {

        try {
            Comic.guardarComicsenCSV(comics);
            System.out.println("Cambios guardados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar los cambios: " + e.getMessage());
        }
    }
    
    /**
    * Exporta un reporte en formato TXT que incluye la lista de usuarios y ventas del sistema.
    * <p>
    * El archivo generado tiene el nombre: <code>reporte_usuarios_ventas_AAAA‑MM‑DD.txt</code>,
    * donde la fecha corresponde al día que se genero el reporte.
    * </p>
    * <p>
    * Solo un usuario autenticado con rol de administrador puede ejecutar este método.
    * Si no hay usuario autenticado o el usuario es de tipo Cliente, se muestra un mensaje de error
    * y no se genera el archivo.
    * </p>
    *
    * @throws IOException si ocurre un error durante la escritura del archivo.
    */
    public void exportarReporteTxtUsuariosYVentas() {
        if (usuarioActivo == null) {
            System.out.println("Error: registrese o identifiquese.");
            return;
        }
        
        if (usuarioActivo.getTipoUsuario() == "Cliente") {
            System.out.println("Este usuario no tiene permisos para generar reportes.");
            return;
        }
        
        String fecha = java.time.LocalDate.now().toString();
        String rutaArchivo = "reporte_usuarios_ventas_" + fecha + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(rutaArchivo))) {
            // Sección de Usuarios
            writer.write("===== Reporte de Usuarios =====");
            writer.newLine();

            if (usuarios.isEmpty()) {
                writer.write("No hay usuarios registrados.");
                writer.newLine();
            } else {
                for (Usuario usuario : usuarios.values()) {
                    writer.write(usuario.getRut() + " - " + usuario.getNombre() + " - " 
                            + usuario.getApellido() + " - " + usuario.getTipoUsuario());
                    writer.newLine();
                }
                writer.write("Total de usuarios registrados: " + usuarios.size());
                writer.newLine();
            }

            writer.newLine();

            // Sección de Ventas
            writer.write("===== Reporte de Ventas =====");
            writer.newLine();

            if (ventas.isEmpty()) {
                writer.write("No hay ventas registradas.");
                writer.newLine();
            } else {
         
                for (Comic comic : ventas) {
                    writer.write(comic.getCodigo()+ " - " +comic.getTitulo()+ " - " +comic.getAutor()
                            + " - " +comic.getEditorial()+ " - " +comic.getPrecio()+ " - " +comic.getTipoComic());
                    writer.newLine();
                
                }
                writer.write("Total de ventas realizadas: " + ventas.size());
                writer.newLine();          
            }

            writer.newLine();
            writer.write("Fecha de generación del reporte: " + fecha);

            System.out.println("Reporte exportado exitosamente a: " + rutaArchivo);
        } catch (Exception e) {
            System.err.println("Error al exportar el reporte: " + e.getMessage());
        }
    
    }  
   
}
   
