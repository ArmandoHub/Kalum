package edu.kalum.enrollment.models.dao;

import edu.kalum.enrollment.models.entities.EnrollmentRequest;
import edu.kalum.enrollment.models.entities.StatusEnrollmentProcess;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Stateless
public class EnrollmentProcessImp implements IEnrollmentProcessDao{
    @PersistenceContext(unitName = "kalum-dev-PU")
    private EntityManager entityManager;



    @Override
    public StatusEnrollmentProcess executeEnrollmentProcess(EnrollmentRequest request) {
        StoredProcedureQuery enrollmentProcess = entityManager.createStoredProcedureQuery("sp_enrollment_process")
                .registerStoredProcedureParameter("_no_expediente",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_ciclo",String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_mes_inicio_pago", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_carrera_id",String.class, ParameterMode.IN);
        enrollmentProcess.setParameter("_no_expediente", request.getNoExpediente());
        enrollmentProcess.setParameter("_ciclo", request.getCiclo());
        enrollmentProcess.setParameter("_mes_inicio_pago", request.getMesInicioPago());
        enrollmentProcess.setParameter("_carrera_id", request.getCarreraId());
        enrollmentProcess.execute();
        Object[] resultado = (Object[])enrollmentProcess.getSingleResult();
        StatusEnrollmentProcess status = new StatusEnrollmentProcess(resultado[0].toString());

        return status;
    }
}
