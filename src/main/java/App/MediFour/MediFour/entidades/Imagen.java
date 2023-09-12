package App.MediFour.MediFour.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "Imagen")
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String nombre;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

}
