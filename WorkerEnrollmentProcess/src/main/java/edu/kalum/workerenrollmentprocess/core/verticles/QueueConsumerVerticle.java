package edu.kalum.workerenrollmentprocess.core.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.QueueOptions;
import io.vertx.rabbitmq.RabbitMQClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumerVerticle extends AbstractVerticle {

    //Vamos a crear un bus de eventos, el llamara a los verticle para que realizen sus operaciones
    //se pueden crear varios evenBus, pero por el momento solo se creara uno.

    private EventBus eventBus; //<-- es quien controlara los verticles
    private RabbitMQClient rabbitMQClient; //<-- cliente para rabbitMQ
    private static final Logger logger = LoggerFactory.getLogger(QueueConsumerVerticle.class);
    private static final String EVENT_BUS_ENROLLMENT_PROCESS = "EVENT_BUS_ENROLLMENT_PROCESS";


    @Override
    public void start(){
        this.eventBus = vertx.eventBus(); //<-- cuando se inicia se hara referencia a la propiedad evenBus y que vertx jale al
        //    eventBus que ha sido creado, si ya se ha creado el eventBus los jalara (this.eventBus)
        this.vertx.setTimer(5000, handler -> { //<-- despues de que se ejecute el aplicativo le decimos que espere 5000 ms y que despues ejecute la funcion readMessageEvent
            readMessageEvent();
        });
    }

    //Este metodo se encargara de ller el mensaje, del lado del eventBus
    public void readMessageEvent(){

        /*this.eventBus.consumer("BUS_EVENT_KALUM"); //Con esto voy a estar leyendo la informacion que tiene el bus
                                                          //Podemos enviar o recibir mensajes al bus de eventos
                                                          //COn request enviamos un mensaje al bus de eventos y con consumer le preguntamos*/

        //con JsonObject
        JsonObject config = new JsonObject()
                .put("user",config().getString("edu.kalum.broker.username")) //<-- funciona como clave:"valor", clave = "user" y valor es el valor de esta clave:valor = "edu.kalum.broker.username"
                .put("password",config().getString("edu.kalum.broker.password"))
                .put("host",config().getString("edu.kalum.broker.host"))
                .put("port",config().getString("edu.kalum.broker.port"))
                .put("virtualHost",config().getString("edu.kalum.broker.virtualHost"))
                .put("queue",config().getString("edu.kalum.broker.queue"));
        // ^ con esto estamos traendo las configuraciones para que se conecte a rabbitMQ

        this.rabbitMQClient =RabbitMQClient.create(vertx, config); //<-- creara un objeto apartir de vertx y la configuraciob de rabbitMQ

        this.rabbitMQClient.start(startResult -> { //<-- con esto empezamos a leer los mensajes de la cola

            if (startResult.succeeded()) {
                logger.info("se realizo la conexion a rabbitMQ Exitosamente");

                this.rabbitMQClient.basicQos(1, asyncResult -> { //<-- con esto le indicamos que vamos a leer un mensaje asincono (basicQos)
                    logger.info("Estableciendo lectura de mensaje 1 a 1");
                });

                this.rabbitMQClient.basicConsumer(config().getString("edu.kalum.broker.queue"), new QueueOptions().setAutoAck(false), consumerResult ->{
                    if (consumerResult.succeeded()) {
                        consumerResult.result().handler(message ->{
                            DeliveryOptions options = new DeliveryOptions();
                            options.addHeader("count","1");
                            this.eventBus.request(this.EVENT_BUS_ENROLLMENT_PROCESS, message.body().toJsonObject(),options, replyHandler -> {
                                if (replyHandler.succeeded()) {
                                    if (replyHandler.result().body() != null && replyHandler.result().body().toString().equalsIgnoreCase("ok")) {
                                        this.rabbitMQClient.basicAck(message.envelope().deliveryTag(), false, asyncResponse -> {
                                            logger.info("Process enrollment succes 1");
                                        });
                                    } else if(replyHandler.result().body() != null && replyHandler.result().body().toString().equalsIgnoreCase("fail")) {
                                        this.rabbitMQClient.basicNack(message.envelope().deliveryTag(),false,true, asyncResponse -> {
                                            logger.info("Failed enrollent process");
                                        });
                                    }
                                }
                            });
                        });
                    } else {
                        logger.error("ERROR".concat(consumerResult.cause().getMessage()));
                    }
                });

            } else {
                logger.error("Hubo un error en el proceso de conexion a rabbitMQ (" .concat(startResult.cause().getMessage()),")");
            }

        });



    }
}
