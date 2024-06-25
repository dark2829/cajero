package com.example.cajero.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios")
    private Integer idUsuarios; 
    private String usuario; 
    @NotNull
    private String correo;
    @NotNull
    private String pass; 
    @NotNull
    private String monto; 
    @NotNull
    private String nombre; 
    private Boolean activo;
}
