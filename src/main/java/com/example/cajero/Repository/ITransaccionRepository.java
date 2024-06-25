package com.example.cajero.Repository;

import com.example.cajero.Model.Transaccion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransaccionRepository extends CrudRepository<Transaccion, Integer> {

    @Query("SELECT t FROM Transaccion t WHERE t.usuario = :user")
    public List<Transaccion> findByUser(@Param("user") String user);

}
