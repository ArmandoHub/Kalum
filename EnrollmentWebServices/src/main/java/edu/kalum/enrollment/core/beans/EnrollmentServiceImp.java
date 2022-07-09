package edu.kalum.enrollment.core.beans;

import edu.kalum.enrollment.models.dao.IAspiranteDao;
import edu.kalum.enrollment.models.dao.ICarreraTecnicaDao;
import edu.kalum.enrollment.models.dao.IEnrollmentProcessDao;
import edu.kalum.enrollment.models.domain.Aspirante;
import edu.kalum.enrollment.models.domain.CarreraTecnica;
import edu.kalum.enrollment.models.entities.EnrollmentRequest;
import edu.kalum.enrollment.models.entities.EnrollmentResponse;
import edu.kalum.enrollment.models.entities.StatusEnrollmentProcess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

@Stateless  //cada pediticion que llegue si hay una instancia ya en uso esa misma va a usar
@WebService(endpointInterface = "edu.kalum.enrollment.core.beans.IEnrollmentServices")
public class EnrollmentServiceImp implements IEnrollmentServices {

    @Inject
    private IEnrollmentProcessDao enrollmentProcessDao;

    @Inject
    private ICarreraTecnicaDao carreraTecnicaDao;

    @Inject
    private IAspiranteDao aspiranteDao;

    @Override
    public EnrollmentResponse enrollmentProcess(EnrollmentRequest request) {
        EnrollmentResponse enrollmentResponse = null;
        CarreraTecnica carreraTecnica = carreraTecnicaDao.findById(request.getCarreraId());

        if (carreraTecnica != null) {
            Aspirante aspirante = aspiranteDao.findById(request.getNoExpediente());

            if (aspirante != null) {
                StatusEnrollmentProcess respuesta = null;

                try {
                    respuesta = enrollmentProcessDao.executeEnrollmentProcess(request);
                } catch (Exception e) {
                    enrollmentResponse = new EnrollmentResponse(503, "Error".concat(e.getCause().getMessage()));
                }

                if (respuesta != null) {
                    enrollmentResponse = new EnrollmentResponse(201, respuesta.getStatus());
                }
            } else {
                enrollmentResponse = new EnrollmentResponse(400, "El aspirante con el expediente: " + request.getNoExpediente() + " no existe");
            }
        } else {
            enrollmentResponse = new EnrollmentResponse(400, "La carrera con el ID " + request.getCarreraId() + " no existe");
        }
        return enrollmentResponse;
    }

}
