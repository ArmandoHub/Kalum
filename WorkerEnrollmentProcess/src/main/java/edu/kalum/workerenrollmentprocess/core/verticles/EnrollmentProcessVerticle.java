package edu.kalum.workerenrollmentprocess.core.verticles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.kalum.workerenrollmentprocess.core.client.services.EnrollmentRequest;
import edu.kalum.workerenrollmentprocess.core.client.services.EnrollmentServiceImpService;
import edu.kalum.workerenrollmentprocess.core.client.services.IEnrollmentServices;
import edu.kalum.workerenrollmentprocess.core.domains.EnrollmentProcessRequest;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class EnrollmentProcessVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(EnrollmentProcessVerticle.class);
    private static final String CIRCUIT_BREAKER_ENROLLMENT_PROCESS = "CIRCUIT_BREAKER_ENROLLMENT_PROCESS";
    private static final String EVENT_BUS_ENROLLMENT_PROCESS = "EVENT_BUS_ENROLLMENT_PROCESS";
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private EventBus eventBus;


    public void start(){
        this.eventBus = vertx.eventBus();
        CircuitBreaker breaker = CircuitBreaker.create( this.CIRCUIT_BREAKER_ENROLLMENT_PROCESS, vertx, new CircuitBreakerOptions()
                        .setMaxFailures(5)
                        .setMaxRetries(5)
                        .setFallbackOnFailure(true)
                        .setTimeout(100))
                .retryPolicy(retryCount -> retryCount * 100L)
                .openHandler(circuit -> { logger.warn("CIRUCUITO ABIERTO!!");})
                .closeHandler(circuit ->{logger.info("CIRUCUITO CERRADO");})
                .halfOpenHandler(circuit ->{logger.warn("CIRUCUITO MEDIO-ABIERTO");});
        logger.info("CIRCUIT BREAKER STATUS IS: ".concat(breaker.state().toString()));


        this.eventBus.consumer(this.EVENT_BUS_ENROLLMENT_PROCESS, message -> {
            EnrollmentProcessRequest enrollmentProcessRequest = gson.fromJson(message.body().toString(), EnrollmentProcessRequest.class);
            breaker.<JsonObject> execute(future -> {
                JsonObject  result = null;
                try {
                    IEnrollmentServices enrollmentServices = new EnrollmentServiceImpService().getEnrollmentServiceImpPort();
                    EnrollmentRequest enrollmentRequest = new EnrollmentRequest();

                    enrollmentRequest.setCarreraId(enrollmentProcessRequest.getCarreraId());
                    enrollmentRequest.setCiclo(enrollmentProcessRequest.getCiclo());
                    enrollmentRequest.setMesInicioPago(enrollmentProcessRequest.getMesInicioPago());
                    enrollmentRequest.setNoExpediente(enrollmentProcessRequest.getNoExpediente());

                    enrollmentServices.enrollmentProcess(enrollmentRequest);
                    result = new JsonObject().put("status",201).put("message","enrollment Proces Success");
                    future.complete(result);
                    logger.info("Enrollmento process services success 2");

                } catch (Exception e){
                    result = new JsonObject().put("status",503).put("message","Enrollment process fail");
                    future.fail(gson.toJson(result));
                    logger.error("Enrollment process services failed 3");
                }
            }).onComplete(response -> {
                if (response.succeeded()) {
                    message.replyAndRequest("ok", replyHandler -> {
                        logger.info("response to event bus success  ");
                    });
                } else {
                    message.replyAndRequest("fail", replyHandler -> {
                        logger.error("Response to event bus failed");
                    });
                }
            });
        });
    }
}
