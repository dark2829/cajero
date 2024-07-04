package com.example.cajero.Service.ServiceImplements;

import com.example.cajero.Model.Denominacion;
import com.example.cajero.Model.Transaccion;
import com.example.cajero.Model.Usuario;
import com.example.cajero.Repository.IDenominacionRepository;
import com.example.cajero.Repository.ITransaccionRepository;
import com.example.cajero.Repository.IUsuariosRepository;
import com.example.cajero.Service.IDenominacionService;
import com.example.cajero.Service.ITransaccionService;
import com.example.cajero.Service.IUsuarioService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImplements implements IUsuarioService {

    @Autowired
    private IUsuariosRepository usuarioRepository;

    @Autowired
    private ITransaccionRepository transaccoinesRepository;

    @Autowired
    private IDenominacionService denominacionService;

    @Autowired
    private ITransaccionService transaccionService;

    @Override
    public Usuario SaveOrUpdateUsuario(Usuario usuario) {
        Random random = new Random();
        List<Usuario> usuarioRecorrido = (List<Usuario>) usuarioRepository.findAll();
        List<Integer> codigosUsuario = new ArrayList<>();
        for (Usuario user : usuarioRecorrido) {
            codigosUsuario.add(Integer.parseInt(user.getUsuario()));
        }
        int codigoUsuario = random.nextInt(9000) + 1000;
        while (codigosUsuario.contains(codigoUsuario)) {
            codigoUsuario = random.nextInt(9000) + 1000;
        }
        String codigo = String.valueOf(codigoUsuario);
        usuario.setUsuario(codigo);
        usuario.setActivo(Boolean.TRUE);
        return usuarioRepository.save(usuario);
    }

    @Override
    public String consultarSaldo(String usuarioParametro) {
        Usuario usuario = usuarioRepository.findByUsuario(usuarioParametro);
        if (usuario == null) {
            return "Usuario invalido";
        }
        String respuesta = "Hola " + usuario.getNombre() + " cuentas con: $" + usuario.getMonto();
        return respuesta;
    }

    @Override
    public Usuario eliminarUsuario(String nombre) {
        
        Usuario usuario = new Usuario(); 
        try {
            usuario = usuarioRepository.findByUsuario(nombre);
            if (usuario.getActivo()) {
                usuario.setActivo(Boolean.FALSE);
            } else {
                usuario.setActivo(Boolean.TRUE);
            }
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Usuario invalido");
        }
        return usuario; 
    }

    @Override
    public String retirar(float cantidad, String user, String pass) {
        Usuario usuario = usuarioRepository.findByUsuario(user);

        String respuesta = "";

        try {
            if (usuario == null) {
                respuesta = "No se puede retirar la cantidad solicitada";
                throw new Exception("Imposible realizar este proceso");
            }
            float resta = Float.parseFloat(usuario.getMonto()) - cantidad;
            float cantidadCopy = cantidad;

            if (cantidad <= -0.0 || cantidad < 0 || resta <= -0) {
                respuesta = "No se puede retirar la cantidad solicitada";
                throw new Exception("Imposible realizar este proceso");
            }
            if (!usuario.getPass().equals(pass)) {
                respuesta = "No se puede retirar la cantidad solicitada";
                throw new Exception("Usuario invalido por el sistema");
            }
            if (usuario.getActivo()) {
                Map<Float, Integer> claudiadolar = new HashMap<>();
                Map<Float, Integer> amplopesos = new HashMap<>();

                List<Denominacion> denominaciones = denominacionService.listarDenominaciones();
                float total = 0;
                for (Denominacion deno : denominaciones) {
                    total += deno.getCantidad() * deno.getDenominacion();
                }
                float isPossible = total - cantidad;
                if (isPossible <= 0) {
                    throw new Exception("No es posible realizar esta transaccion");
                }
                for (Denominacion denominacion : denominaciones) {
                    if (denominacion.getTipo().equals("Billete")) {
                        claudiadolar.put((float) denominacion.getDenominacion(), denominacion.getCantidad());
                    }
                    if (denominacion.getTipo().equals("Moneda")) {
                        amplopesos.put((float) denominacion.getDenominacion(), denominacion.getCantidad());
                    }
                }

                TreeMap<Float, Integer> billetesOrdenados = new TreeMap<>(java.util.Collections.reverseOrder());
                billetesOrdenados.putAll(claudiadolar);

                TreeMap<Float, Integer> monedasOrdenadas = new TreeMap<>(java.util.Collections.reverseOrder());
                monedasOrdenadas.putAll(amplopesos);
                respuesta = "Voy a etregar: \n";
                for (float billete : billetesOrdenados.keySet()) {
                    int cantidadBilletes = (int) (cantidad / billete);
                    if (cantidadBilletes > 0 && claudiadolar.get(billete) > 0) {
                        int billetesRetirados = Math.min(cantidadBilletes, claudiadolar.get(billete));
                        respuesta += " " + billetesRetirados + " de " + billete + " en billete(s)\n";
                        cantidad -= billetesRetirados * billete;
                        claudiadolar.put(billete, claudiadolar.get(billete) - billetesRetirados);
                    }
                }

                for (float moneda : monedasOrdenadas.keySet()) {
                    int cantidadMonedas = (int) (cantidad / moneda);
                    if (cantidadMonedas > 0 && amplopesos.get(moneda) > 0) {
                        int cantidadRetirada = Math.min(cantidadMonedas, amplopesos.get(moneda));
                        respuesta += " " + cantidadRetirada + " de " + moneda + " en moenda(s)\n";
                        cantidad -= cantidadRetirada * moneda;
                        amplopesos.put(moneda, amplopesos.get(moneda) - cantidadRetirada);
                    }
                }

                for (int i = 0; i < denominaciones.size(); i++) {
                    Denominacion denominacion = denominaciones.get(i);
                    float valorDenominacion = denominacion.getDenominacion();

                    if (claudiadolar.containsKey(valorDenominacion)) {
                        denominacion.setCantidad(claudiadolar.get(valorDenominacion));
                    }

                    if (amplopesos.containsKey(valorDenominacion)) {
                        denominacion.setCantidad(amplopesos.get(valorDenominacion));
                    }
                }

                for (Denominacion de : denominaciones) {
                    System.out.println(de.getCantidad() + " " + de.getDenominacion());
                }

                String nuevoMonto = String.valueOf(Float.parseFloat(usuario.getMonto()) - cantidadCopy);
                System.out.println("Este es el nuevo monto: " + nuevoMonto);
                usuario.setMonto(nuevoMonto);

                Transaccion transaccion = new Transaccion();
                transaccion.setDate(new Date());
                transaccion.setUsuario(usuario.getUsuario());
                Random random = new Random();
                int codigoTransaccion = random.nextInt(9000) + 1000;
                List<Transaccion> transacciones = (List<Transaccion>) transaccoinesRepository.findAll();
                List<Integer> codigoTransacciones = new ArrayList<>();
                transaccion.setMonto(String.valueOf(cantidadCopy));
                if (transacciones.size() != 0) {
                    for (Transaccion transact : transacciones) {
                        codigoTransacciones.add(Integer.parseInt(transact.getIdentificador()));
                    }

                    while (codigoTransacciones.contains(codigoTransaccion)) {
                        codigoTransaccion = random.nextInt(9000) + 1000;
                    }
                }
                transaccion.setIdentificador(String.valueOf(codigoTransaccion));

                denominacionService.saveAll(denominaciones);
                usuarioRepository.save(usuario);
                transaccionService.registrarTransaccion(transaccion);
            } else {
                respuesta = "Usuario no valido por el sistema";
            }
        } catch (Exception e) {
            respuesta = "Ocurrio un error al realizar la operacion";
        }

        return respuesta;
    }

    @Override
    public List<Transaccion> consultarHistorial(String nombre) {
        List<Transaccion> transacciones = transaccoinesRepository.findByUser(nombre);
        return transacciones;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        return usuarios;
    }

}
