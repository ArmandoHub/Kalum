package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.domain.Jornada;

import java.util.List;

public interface IJornadaDao {

    public List<Jornada> findAll();
    public Jornada findById(String jornadaId);
    public void save(Jornada jornada);
    public void update(Jornada jornada);
    public void delete(Jornada jornada);

}
