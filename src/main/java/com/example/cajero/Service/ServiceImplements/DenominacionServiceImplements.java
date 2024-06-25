package com.example.cajero.Service.ServiceImplements;

import com.example.cajero.Model.Denominacion;
import com.example.cajero.Repository.IDenominacionRepository;
import com.example.cajero.Service.IDenominacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DenominacionServiceImplements implements IDenominacionService{
    
    @Autowired
    private IDenominacionRepository denominacionRepository; 

    @Override
    public List<Denominacion> listarDenominaciones() {
        List<Denominacion> denominaciones = (List<Denominacion>) denominacionRepository.finAllOrderDesc();
        return denominaciones;
    }

    @Override
    public String insertarDenominacion(Denominacion denominacion) {
        String respuesta = "";
        try{
            denominacionRepository.save(denominacion);
            respuesta = "Operacion exitosa";
        }catch(Exception e){
            respuesta = "Ocurrio un error: " +e;
        }
        return respuesta;
    }
    
    @Override
    public String saveAll(List<Denominacion> denominacion) {
        String respuesta = "";
        denominacionRepository.saveAll(denominacion);
        respuesta = "Operacion exitosa";
        return respuesta;
    }
    
}
