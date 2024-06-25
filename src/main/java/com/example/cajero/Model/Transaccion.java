package com.example.cajero.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "transacciones")
public class Transaccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransacciones")
    private Integer idTransacciones; 
    private Date date; 
    private String usuario; 
    private String identificador; 
    private String monto; 
    
}
