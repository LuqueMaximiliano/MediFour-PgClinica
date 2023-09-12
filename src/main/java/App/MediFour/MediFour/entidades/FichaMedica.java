package App.MediFour.MediFour.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity //Establezco que es una entidad/objeto y que voy a persistir sus datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FichaMedica")
public class FichaMedica {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idfichaMedica;
    private String notas;
    @OneToOne
    private Turno turno;

}
