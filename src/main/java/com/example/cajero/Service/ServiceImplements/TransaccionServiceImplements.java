package com.example.cajero.Service.ServiceImplements;

import com.example.cajero.Model.Transaccion;
import com.example.cajero.Repository.ITransaccionRepository;
import com.example.cajero.Service.ITransaccionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionServiceImplements implements ITransaccionService{
    
    @Autowired
    private ITransaccionRepository transaccionRepository; 
    
    @Override
    public Transaccion registrarTransaccion(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> listarTransaccionPorId(String nombre) {
        return (List<Transaccion>) transaccionRepository.findByUser(nombre);
    }

    @Override
    public List<Transaccion> listarTransacciones() {
        return (List<Transaccion>) transaccionRepository.findAll();
    }
    
}
