package com.example.cajero.Controller;

import com.example.cajero.Model.Denominacion;
import com.example.cajero.Service.IDenominacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/denominacion")
public class DenominacionController {

    @Autowired
    private IDenominacionService denominacionServie;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Denominacion>> listarDenominaciones(){
        List<Denominacion> denominaciones = denominacionServie.listarDenominaciones();  
        return new ResponseEntity<>(denominaciones, HttpStatus.OK);
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<String> insertar(@RequestBody Denominacion denominacion){
        denominacionServie.insertarDenominacion(denominacion);
        return new ResponseEntity<>("Operacion exitosa", HttpStatus.OK);
    }
}
