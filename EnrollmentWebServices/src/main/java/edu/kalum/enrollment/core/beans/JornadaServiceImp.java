package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.dao.IJornadaDao;
import edu.kalum.enrollment.models.domain.Jornada;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

@Stateless
@WebService(endpointInterface = "edu.kalum.enrollment.core.beans.IJornadaService")
public class JornadaServiceImp implements IJornadaService{

    @Inject
    private IJornadaDao jornadaDao;

    @Override
    public List<Jornada> listarJornadas() {
        return jornadaDao.findAll();
    }

    @Override
    public Jornada listarJornada(String jornadaId) {
        return jornadaDao.findById(jornadaId);
    }

    @Override
    public void insertarJornada(Jornada jornada) {
        jornadaDao.save(jornada);
    }

    @Override
    public void actualizarJornada(Jornada jornada) {
        jornadaDao.update(jornada);
    }

    @Override
    public void eliminarJornada(Jornada jornada) {
        jornadaDao.delete(jornada);
    }
}
