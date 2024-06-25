package com.example.cajero.Controller;

import com.example.cajero.Model.Transaccion;
import com.example.cajero.Model.Usuario;
import com.example.cajero.Service.IUsuarioService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService usuarioService; 
    
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<String> saveOrUpdate(@RequestBody Usuario usuario) throws IOException{
        Usuario user = usuarioService.SaveOrUpdateUsuario(usuario);
        String respuesta = "Operacion realizada con exito, recuerda tu clave: " + user.getUsuario();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping("/consultaSaldo/{usuario}")
    public ResponseEntity<String> consulta(@PathVariable String usuario){
        return new ResponseEntity<>(usuarioService.consultarSaldo(usuario), HttpStatus.OK);
    }
    
    @GetMapping("/consultarHistorial/{usuario}")
    public ResponseEntity<List<Transaccion>> consultaHistorial(@PathVariable String usuario){
        List<Transaccion> transacciones = usuarioService.consultarHistorial(usuario);
        return new ResponseEntity<>(transacciones,HttpStatus.OK);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @PutMapping("/updateActive/{usuario}")
    public ResponseEntity<Usuario> eliminar(@PathVariable String usuario){
        Usuario user = usuarioService.eliminarUsuario(usuario);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping("/retirar")
    public ResponseEntity<String> retirar(@RequestBody Map<String, String> retiro){
        String usuario = retiro.get("usuario");
        float cantidad = Float.parseFloat(retiro.get("cantidad"));
        String password = retiro.get("pass");
        
        String respuesta = usuarioService.retirar(cantidad, usuario, password);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
