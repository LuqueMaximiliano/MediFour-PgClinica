package App.MediFour.MediFour.entidades;

import App.MediFour.MediFour.enumeraciones.DiaSemana;
import App.MediFour.MediFour.enumeraciones.Especialidad;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Profesional")
public class Profesional extends Usuario implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid") //genera un valor de manera automatica al momento que el repo persita la entidad.
    @GenericGenerator(name = "uuid", strategy = "uuid2") //es una estrategia alternativa
    private String id;

    protected String matricula;
    @Enumerated(EnumType.STRING)
    protected Especialidad especialidad;
    @ElementCollection
    @CollectionTable(name = "Profesional_DiasDisponibles")
    @Enumerated(EnumType.STRING)
    protected List<DiaSemana> diasDisponibles;
    protected LocalTime horarioEntrada;
    protected LocalTime horarioSalida;
    protected Double precioConsulta;
    protected Double reputacion;
    protected String observaciones;

    @OneToMany(mappedBy = "profesional", fetch = FetchType.LAZY)
    private List<Turno> turnosCreados = new ArrayList<>();

    @OneToMany
    private List<FichaMedica> historialConsultas;
}
