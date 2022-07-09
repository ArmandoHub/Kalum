package edu.kalum.enrollment.models.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@Entity
@Table(name = "JORNADA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({@NamedQuery(name = "jornada.findAll",query = "select jnd from Jornada as jnd")})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Jornada {

    @Id
    @Column(name = "JORNADA_ID")
    private String jornadaId;

    @Column(name = "JORNADA")
    private String jornada;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @XmlTransient
    @OneToMany(mappedBy = "jornada", fetch = FetchType.EAGER)
    private List<Aspirante> aspirantes;

    @Override
    public String toString(){
        return "Jornadas: {" + this.jornadaId + ", " + this.jornada + "}";
    }

}
