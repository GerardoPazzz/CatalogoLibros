package com.grpa.CatalogoLibros.DTO;

import java.util.List;

public interface LibroDTO {
    String getTitulo();
    List<String> getIdiomas();
    Double getTotalDescargas();
}
