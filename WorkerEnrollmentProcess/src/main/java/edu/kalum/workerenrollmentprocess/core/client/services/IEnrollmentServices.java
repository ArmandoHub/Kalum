
package edu.kalum.workerenrollmentprocess.core.client.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IEnrollmentServices", targetNamespace = "http://beans.core.enrollment.kalum.edu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IEnrollmentServices {


    /**
     * 
     * @param arg0
     * @return
     *     returns edu.kalum.workerenrollmentprocess.core.client.services.EnrollmentResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "enrollmentProcess", targetNamespace = "http://beans.core.enrollment.kalum.edu/", className = "edu.kalum.workerenrollmentprocess.core.client.services.EnrollmentProcess")
    @ResponseWrapper(localName = "enrollmentProcessResponse", targetNamespace = "http://beans.core.enrollment.kalum.edu/", className = "edu.kalum.workerenrollmentprocess.core.client.services.EnrollmentProcessResponse")
    @Action(input = "http://beans.core.enrollment.kalum.edu/IEnrollmentServices/enrollmentProcessRequest", output = "http://beans.core.enrollment.kalum.edu/IEnrollmentServices/enrollmentProcessResponse")
    public EnrollmentResponse enrollmentProcess(
        @WebParam(name = "arg0", targetNamespace = "")
        EnrollmentRequest arg0);

}
