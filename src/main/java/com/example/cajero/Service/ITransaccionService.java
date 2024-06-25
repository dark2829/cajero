package com.example.cajero.Service;

import com.example.cajero.Model.Transaccion;
import java.util.List;

public interface ITransaccionService {
    public Transaccion registrarTransaccion(Transaccion transaccion); 
    public List<Transaccion> listarTransaccionPorId(String nombre);
    public List<Transaccion> listarTransacciones();
}
