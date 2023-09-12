package App.MediFour.MediFour.entidades;

import App.MediFour.MediFour.enumeraciones.ObraSocial;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "Paciente")
public class Paciente extends Usuario implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid") //genera un valor de manera automatica al momento que el repo persita la entidad.
    @GenericGenerator(name = "uuid", strategy = "uuid2") //es una estrategia alternativa
    private String id;

    protected Boolean tieneObraSocial;
    @Enumerated(EnumType.STRING)
    protected ObraSocial obraSocial;
    protected Integer numeroAfiliado;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<Turno> listaDeturnos;

    @OneToMany
    private List<FichaMedica> historialMedico;

}
