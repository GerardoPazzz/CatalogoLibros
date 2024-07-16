package com.grpa.CatalogoLibros.principal;

import com.grpa.CatalogoLibros.DTO.AutorDTO;
import com.grpa.CatalogoLibros.DTO.LibroDTO;
import com.grpa.CatalogoLibros.model.*;
import com.grpa.CatalogoLibros.repository.AutorRepository;
import com.grpa.CatalogoLibros.repository.LibroRepository;
import com.grpa.CatalogoLibros.service.ConsumoAPI;
import com.grpa.CatalogoLibros.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convertirDatos = new ConvierteDatos();
    private Scanner sc = new Scanner(System.in);
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un anio determinado
                    5 - Listar libros por idioma          
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
            }
        }
    }

    private void listarAutoresRegistrados() {
        List<AutorDTO> listaAutorDTO = autorRepository.findAllBy();

        if (listaAutorDTO.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("Listado de libros registrados:");
            for (AutorDTO AutorDTO : listaAutorDTO) {
                System.out.println("Nombre: " + AutorDTO.getNombre());
                System.out.println("Anio nacimiento: " + AutorDTO.getAnioNacimiento());
                System.out.println("Anio muerte: " + AutorDTO.getAnioMuerte());
                System.out.println("----------------------");
            }
        }
    }

    public void listarLibrosRegistrados() {
        List<LibroDTO> listaLibrosDTO = libroRepository.findAllBy();

        if (listaLibrosDTO.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("Listado de libros registrados:");
            for (LibroDTO libroDTO : listaLibrosDTO) {
                System.out.println("Título: " + libroDTO.getTitulo());
                System.out.println("Idiomas: " + libroDTO.getIdiomas());
                System.out.println("Total de Descargas: " + libroDTO.getTotalDescargas());
                System.out.println("----------------------");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = convertirDatos.obtenerDatos(json, Datos.class);
        System.out.println("Ingrese el libro que desee buscar: ");
        var libroBuscado = sc.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search="+libroBuscado.replace(" ", "+"));
        var datosBusqueda = convertirDatos.obtenerDatos(json,Datos.class);
        Optional<DatosLibros> libroRecolectado = datosBusqueda.listaLibros().stream()
                .filter(e -> e.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                .findFirst();
        if(libroRecolectado.isPresent()){
            DatosLibros datosLibrosEncontrado = libroRecolectado.get();
            System.out.println(datosLibrosEncontrado);
            Libro libroEncontrado = new Libro(datosLibrosEncontrado);
            List<DatosAutor> autores = libroRecolectado.get().datosAutor();
            DatosAutor datosAutorEncontrado = autores.get(0);
            System.out.println(datosAutorEncontrado);
            Autor autorEncontrado = new Autor(datosAutorEncontrado);

            libroEncontrado.setAutor(autorEncontrado);

            autorRepository.save(autorEncontrado);
            libroRepository.save(libroEncontrado);


            System.out.println(libroRecolectado.get());
        }else {
            System.out.println("No se encontro ningun libro!");
        }
    }
}