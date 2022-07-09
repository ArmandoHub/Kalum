package edu.kalum.enrollment.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data                   //Con esto lombok crea metodos setters y getters de la clase
@NoArgsConstructor      // Con esto lombok crea el constructor vacio de la clase
@AllArgsConstructor     // con esto lombok crea el constructor con parametros
public class EnrollmentResponse implements Serializable {
    private int statusCode;
    private String descripcion;

}
