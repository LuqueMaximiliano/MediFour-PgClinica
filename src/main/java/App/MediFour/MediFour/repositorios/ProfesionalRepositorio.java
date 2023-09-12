package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.enumeraciones.Especialidad;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT m FROM Profesional m WHERE m.especialidad = :especialidad")
    public Profesional buscarPorEspecialidad(@Param("especialidad") Especialidad especialidad);

    @Query("SELECT m FROM Profesional m WHERE m.especialidad = :especialidad")
    public List<Profesional> buscarNombresPorEspecialidad(@Param("especialidad") Especialidad especialidad);

    @Query("SELECT m FROM Profesional m WHERE m.nombre = :nombre")
    public List<Profesional> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT m FROM Profesional m WHERE m.email = :email")
    public Profesional buscarPorEmail(@Param("email") String email);

    @Query("SELECT m FROM Profesional m WHERE m.telefono = :telefono")
    public Profesional buscarPorTelefono(@Param("telefono") String telefono);

    @Query("SELECT p FROM Profesional p WHERE p.id = :id")
    public Profesional buscarProfesionalPorID(@Param("id") String id);

    @Query("SELECT m FROM Profesional m WHERE m.especialidad = :especialidad")
    public Collection<Profesional> listarProfesionalPorEspecialidad(@Param("especialidad") Especialidad especialidad);

    List<Profesional> findByActivoTrue(); // Utiliza el nombre de método con la convención para filtrar profesionales activos

}
