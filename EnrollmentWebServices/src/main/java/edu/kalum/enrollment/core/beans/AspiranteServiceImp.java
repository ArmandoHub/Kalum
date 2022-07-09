package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.dao.IAspiranteDao;
import edu.kalum.enrollment.models.domain.Aspirante;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

@Stateless
@WebService(endpointInterface = "edu.kalum.enrollment.core.beans.IAspiranteService")
public class AspiranteServiceImp implements IAspiranteService{

    @Inject
    private IAspiranteDao aspiranteDao;

    @Override
    public List<Aspirante> listarAspirantes() {
        return aspiranteDao.findAll();
    }

    @Override
    public Aspirante listarAspirante(String expedienteId) {
        return aspiranteDao.findById(expedienteId);
    }

    @Override
    public void insertarAspirante(Aspirante aspirante) {
        aspiranteDao.save(aspirante);
    }

    @Override
    public void eliminarAspirante(Aspirante aspirante) {
        aspiranteDao.delete(aspirante);
    }

    @Override
    public void actualizarAspirante(Aspirante aspirante) {
        aspiranteDao.update(aspirante);
    }
}
