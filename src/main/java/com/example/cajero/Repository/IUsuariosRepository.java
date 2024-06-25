package com.example.cajero.Repository;

import com.example.cajero.Model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuariosRepository extends CrudRepository<Usuario, Integer> {
    
    @Query("SELECT u FROM Usuario u WHERE u.usuario = :user")
    public Usuario findByUsuario(@Param("user") String user);
    
}
