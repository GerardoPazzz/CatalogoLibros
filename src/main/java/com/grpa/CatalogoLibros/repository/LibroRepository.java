package com.grpa.CatalogoLibros.repository;

import com.grpa.CatalogoLibros.DTO.LibroDTO;
import com.grpa.CatalogoLibros.model.Autor;
import com.grpa.CatalogoLibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<LibroDTO> findAllBy();
    //Encontrar libro por titulo
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    //Listar libros por nombre de autor
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombreAutor")
    List<Libro> findLibrosByNombreAutor(String nombreAutor);

    //Listar libros por idioma
 //   @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
 //   List<Libro> findLibrosByIdioma(String idioma);


}
