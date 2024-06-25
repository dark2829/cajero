package com.example.cajero.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "denominaciones")
public class Denominacion {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddenominaciones")
    private Integer idDenominaciones; 
    private String tipo; 
    private float denominacion; 
    private Integer cantidad; 
    
}
