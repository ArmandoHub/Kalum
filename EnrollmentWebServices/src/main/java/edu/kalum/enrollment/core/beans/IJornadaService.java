package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.domain.Jornada;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IJornadaService {

    @WebMethod
    public List<Jornada> listarJornadas();

    @WebMethod
    public Jornada listarJornada(String jornadaID);

    @WebMethod
    public void insertarJornada(Jornada jornada);

    @WebMethod
    public void actualizarJornada(Jornada jornada);

    @WebMethod
    public void eliminarJornada(Jornada jornada);

}
