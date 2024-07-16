package com.grpa.CatalogoLibros.model;


import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer num;
    @Column(unique = true)
    private String titulo;
    private List<String> idiomas;
    private Double totalDescargas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor ;

    public Libro() {
    }
    public Libro(DatosLibros datosLibros){
        if (datosLibros != null) {
            this.titulo = datosLibros.titulo();
            this.num = datosLibros.id();
            this.idiomas = datosLibros.idiomas();
            this.totalDescargas = datosLibros.totalDescargas();
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", num=" + num +
                ", titulo='" + titulo + '\'' +
                ", idiomas=" + idiomas +
                ", totalDescargas=" + totalDescargas +
                ", autor=" + autor +
                '}';
    }
}
