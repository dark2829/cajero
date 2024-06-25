package com.example.cajero.Controller;

import com.example.cajero.Model.Transaccion;
import com.example.cajero.Service.ITransaccionService;
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
@RequestMapping("/transaction")
public class TransaccionController {
    
    @Autowired
    private ITransaccionService transaccionService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Transaccion>> listarTransacciones(){
        List<Transaccion> transacciones = transaccionService.listarTransacciones();
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
    
    @GetMapping("/list/{nombre}")
    public ResponseEntity<Transaccion> listarTransaccionesPorNombre(String nombre){
        Transaccion transaccion = (Transaccion) transaccionService.listarTransaccionPorId(nombre);
        return new ResponseEntity<>(transaccion, HttpStatus.OK);
    }
    
    @PostMapping("/insertar")
    public ResponseEntity<Transaccion> insertar(@RequestBody Transaccion transaccion){
        return new ResponseEntity<>(transaccionService.registrarTransaccion(transaccion), HttpStatus.OK);
    }
}
