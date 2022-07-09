package edu.kalum.workerenrollmentprocess.core;

import edu.kalum.workerenrollmentprocess.core.verticles.EnrollmentProcessVerticle;
import edu.kalum.workerenrollmentprocess.core.verticles.QueueConsumerVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class WorkerEnrollmentProcessApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WorkerEnrollmentProcessApplication.class);

    private Vertx vertx; //<-- creamos un objeto de vertx el cual enlaza todos los verticles

    @Autowired
    private Environment env; //<-- este objeto se inyectara (inyeccion de dependencias)

    @Autowired
    private QueueConsumerVerticle queueConsumerVerticle; //<-- este tambien no se instanciara, solo se inyectara

    @Autowired
    private EnrollmentProcessVerticle enrollmentProcessVerticle;


    public static void main(String[] args) {
        SpringApplication.run(WorkerEnrollmentProcessApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.debug("Iniciando appConsumer rabbitMQ ---> ");


    }

    @PostConstruct
    public void deployVerticle(){ //<-- este metodo se ejecute luego de crear el constructor de la clase con @PostConstruct
        this.vertx = Vertx.vertx(); //<-- es el objeto que creamos arriba, este objeto va a contener toda la arquitectura y almacenara todos los objetos del evenBus

        ConfigStoreOptions fileConfig = new ConfigStoreOptions().setType("file").setConfig(new JsonObject().put("path","dev".concat(".json")));
        //1. creamos un archivo de tipo ConfigStoreOptions
        //2. con setType le indicamos el tipo de configuracion, este caso es de tipo archivo, pudee ser a niver de BD o de servicio remoto
        //3. con setConfig creamos un objeto de tipo JsonObjetc
        //4. con put le indicamos la ruta donde se encuentra el archivo (el arcivo se llama dev.json) y le concatenamos la extencion que es .json


        ConfigStoreOptions sysPropStore = new ConfigStoreOptions().setType("sys");
        //1. con setType le indicamos que es un archhivo de tipo sys

        ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions().addStore(fileConfig).addStore(sysPropStore);
        //1. con ConfigRetrieverOptions espera las configuraciones anteriores
        //2. con addStore le agregamos las configuraciones (fileCOnfig y sysPropStore)

        ConfigRetriever configRetriever = ConfigRetriever.create(vertx, configRetrieverOptions);
        //1. en configRetriever le vamos a setear el objeto vertx y las opciones de configuracion que recibira esta instancia

        configRetriever.getConfig(config -> {
            if (config.succeeded()){

                this.vertx.deployVerticle(queueConsumerVerticle, new DeploymentOptions().setConfig(config.result()));
                //1. deployVerticle() espera como parametros el verticle que se quiere desplegar
                //2. DeploymentOptions() es parte de la funcion deployVerticle()
                //3. con setConfig le pasamos el resultado del config de la linea 67

                this.vertx.deployVerticle(enrollmentProcessVerticle, new DeploymentOptions().setConfig(config.result()));

                logger.info("Se realizo el deployment de los verticles exitosamente!");
                logger.info("_______________________________________________________");

            } else {
                logger.error("Error al desplegar los verticles (".concat(config.cause().getMessage()) + ")");
            }
        });

    }

}
