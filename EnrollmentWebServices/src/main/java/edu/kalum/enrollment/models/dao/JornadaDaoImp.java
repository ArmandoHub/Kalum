package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.domain.Jornada;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class JornadaDaoImp implements IJornadaDao{

    @PersistenceContext(name = "kalum-dev-PU")
    private EntityManager entityManager;

    @Override
    public List<Jornada> findAll() {
        return entityManager.createNamedQuery("jornada.findAll").getResultList();
    }

    @Override
    public Jornada findById(String jornadaId) {
        return entityManager.find(Jornada.class, jornadaId);
    }

    @Override
    public void save(Jornada jornada) {
        entityManager.persist(jornada);
    }

    @Override
    public void update(Jornada jornada) {
        entityManager.merge(jornada);
    }

    @Override
    public void delete(Jornada jornada) {
        entityManager.remove(entityManager.merge(jornada));
    }
}
