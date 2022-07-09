package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.domain.CarreraTecnica;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless //<- esta notacion hace que este componente va a implementar del componente cuando lo necesitemos
           //   ayuda a optimizar memoria
public class CarreraTecnicaDaoImp implements ICarreraTecnicaDao{

    @PersistenceContext(name = "kalum-dev-PU")
    private EntityManager entityManager;

    @Override
    public List<CarreraTecnica> findAll() {
        return entityManager.createNamedQuery("CarreraTecnica.findAll").getResultList();
    }

    @Override
    public CarreraTecnica findById(String carreraTecnicaId) {
        return entityManager.find(CarreraTecnica.class, carreraTecnicaId);
    }

    @Override
    public void save(CarreraTecnica carreraTecnica) {
        entityManager.persist(carreraTecnica);
    }

    @Override
    public void update(CarreraTecnica carreraTecnica) {
        entityManager.merge(carreraTecnica);
    }

    @Override
    public void delete(CarreraTecnica carreraTecnica) {
        entityManager.remove(entityManager.merge(carreraTecnica));
    }
}
