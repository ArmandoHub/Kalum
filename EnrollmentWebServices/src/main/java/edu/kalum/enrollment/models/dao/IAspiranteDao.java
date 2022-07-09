package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.domain.Aspirante;

import java.util.List;

public interface IAspiranteDao {

    public Aspirante findById(String expedienteId);
    public List<Aspirante> findAll();
    public void save(Aspirante aspirante);
    public void update(Aspirante aspirante);
    public void delete(Aspirante aspirante);


}
