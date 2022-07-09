package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.domain.Aspirante;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AspiranteDaoImp implements IAspiranteDao{

    @PersistenceContext(unitName = "kalum-dev-PU")
    private EntityManager entityManager;

    @Override
    public Aspirante findById(String expedienteId) {
        return entityManager.find(Aspirante.class, expedienteId);
    }

    @Override
    public List<Aspirante> findAll() {
        return entityManager.createNamedQuery("expediente.findAll").getResultList();
    }

    @Override
    public void save(Aspirante aspirante) {
        entityManager.persist(aspirante);
    }

    @Override
    public void update(Aspirante aspirante) {
        entityManager.merge(aspirante);
    }

    @Override
    public void delete(Aspirante aspirante) {
        entityManager.remove(entityManager.merge(aspirante));
    }
}
