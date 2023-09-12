package App.MediFour.MediFour.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "HistoriaClinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(generator = "uuid") //genera un valor de manera automatica al momento que el repo persita la entidad.
    @GenericGenerator(name = "uuid", strategy = "uuid2") //es una estrategia alternativa
    private String id;

    @OneToOne
    private Paciente paciente;

    @OneToMany
    private List<FichaMedica> fichasMedicas;

}
