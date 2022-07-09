package edu.kalum.enrollment.models.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CARRERA_TECNICA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({@NamedQuery(name = "CarreraTecnica.findAll", query = "select ct from CarreraTecnica ct order by ct.carreraTecnica")})
//NamedQueries hace un alias una consulta, el cual lo hara a la clase no directamente a la BD
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CarreraTecnica implements Serializable {

    @Id
    @Column(name = "CARRERA_ID")
    private String carreraId;

    @Column(name = "CARRERA_TECNICA")
    private String carreraTecnica;

    @XmlTransient
    @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.EAGER) //<- con EAGER le decimos que nos trainga los elementos que se relacionan con el objeto
    private List<Aspirante> aspirantes; //<-- una carreraTecnica puede tener varis aspirantes, por eso se usa una List

    @Override
    public String toString(){
        return "Carreras tecnicas: {" + this.carreraId + ", " + this.carreraTecnica + "}";
    }

}
