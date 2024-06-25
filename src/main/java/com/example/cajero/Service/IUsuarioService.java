package com.example.cajero.Service;

import com.example.cajero.Model.Transaccion;
import com.example.cajero.Model.Usuario;
import java.util.List;

public interface IUsuarioService {
    public Usuario SaveOrUpdateUsuario(Usuario usuario);
    public String consultarSaldo(String nombre);
    public Usuario eliminarUsuario(String nombre);
    public String retirar(float cantidad, String user, String pass);
    public List<Transaccion> consultarHistorial(String nombre);
    public List<Usuario> listarUsuarios();
}
