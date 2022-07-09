package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.domain.CarreraTecnica;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ICarreraTecnicaService{

    @WebMethod
    public List<CarreraTecnica> listarCarrerasTecnicas();

    @WebMethod
    public CarreraTecnica listarCarreraTecnica(String carreraTecnicaId);

    @WebMethod
    public void insertarCarreraTecnica(CarreraTecnica carreraTecnica);

    @WebMethod
    public void eliminarCarreraTecnica(CarreraTecnica carreraTecnica);

    @WebMethod
    public void actualizarCarreraTecnica(CarreraTecnica carreraTecnica);
}
