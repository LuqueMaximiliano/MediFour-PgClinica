package App.MediFour.MediFour.entidades;

import App.MediFour.MediFour.enumeraciones.Rol;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid") //genera un valor de manera automatica al momento que el repo persita la entidad.
    @GenericGenerator(name = "uuid", strategy = "uuid2") //es una estrategia alternativa
    private String id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    @Column(unique = true)
    private Integer dni;
    @Column(unique = true)
    private String telefono;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDate alta;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;

    @PrePersist
    protected void onCreate() {
        alta = LocalDate.now(); // Establece la fecha de alta como la fecha actual al crear el paciente
    }
}
