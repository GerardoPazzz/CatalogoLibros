package com.grpa.CatalogoLibros.repository;
import com.grpa.CatalogoLibros.DTO.AutorDTO;
import com.grpa.CatalogoLibros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<AutorDTO> findAllBy();
    @Query("SELECT a FROM Autor a WHERE (a.anioMuerte IS NULL OR a.anioMuerte > :anioActual) AND a.anioNacimiento <= :anioActual")
    List<Autor> listarAutoresVivos(@Param("anioActual") Integer anioActual);
}
