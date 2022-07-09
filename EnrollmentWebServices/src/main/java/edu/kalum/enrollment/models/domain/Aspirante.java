package edu.kalum.enrollment.models.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ASPIRANTE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(value = {@NamedQuery(name = "expediente.findAll", query = "select asp from Aspirante asp")})
public class Aspirante implements Serializable {

    @Id
    @Column(name = "NO_EXPEDIENTE")
    private String numeroExpediente;

    @Column(name = "EXAMEN_ID")
    private String examenId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CARRERA_ID", referencedColumnName = "CARRERA_ID")     //<- va enlazado a las propiedades que tengo del lado de la esquema de la tabla, hay que especificar
    private CarreraTecnica carreraTecnica;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "JORNADA", referencedColumnName = "JORNADA_ID")
    private Jornada jornada;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "TELEFONO")
    private int telefono;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ESTATUS")
    private String estatus;

    @Column(name = "NOMBRES")
    private String nombres;

    @Column(name = "APELLIDOS")
    private String apellidos;

}
