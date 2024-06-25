package com.example.cajero.Repository;

import com.example.cajero.Model.Denominacion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDenominacionRepository extends CrudRepository<Denominacion, Integer>{
    
    @Query("SELECT d FROM Denominacion d ORDER BY d.denominacion DESC")
    public List<Denominacion> finAllOrderDesc();
}
