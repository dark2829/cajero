package com.example.cajero.Service;

import com.example.cajero.Model.Denominacion;
import java.util.List;

public interface IDenominacionService {
    public List<Denominacion> listarDenominaciones(); 
    public String insertarDenominacion(Denominacion denominacion); 
    public String saveAll(List<Denominacion> denominaciones); 
}
