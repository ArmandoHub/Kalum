package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.domain.Aspirante;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAspiranteService {

    @WebMethod
    public List<Aspirante> listarAspirantes();

    @WebMethod
    public Aspirante listarAspirante(String expedienteId);

    @WebMethod
    public void insertarAspirante(Aspirante aspirante);

    @WebMethod
    public void eliminarAspirante(Aspirante aspirante);

    @WebMethod
    public void actualizarAspirante(Aspirante aspirante);
}
