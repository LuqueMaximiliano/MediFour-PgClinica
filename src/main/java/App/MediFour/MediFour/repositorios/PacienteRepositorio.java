package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Paciente;
import App.MediFour.MediFour.enumeraciones.ObraSocial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {

    @Query("SELECT p FROM Paciente p WHERE p.dni = :dni")
    public Paciente buscarPorDNI(@Param("dni") Integer dni);

    @Query("SELECT p FROM Paciente p WHERE p.obraSocial = :obraSocial")
    public List<Paciente> buscarPorObraSocial(@Param("obraSocial") ObraSocial obraSocial);

    @Query("SELECT p FROM Paciente p WHERE p.id = :id")
    public Paciente buscarPorId(@Param("id") String id);

    @Query("SELECT p FROM Paciente p WHERE p.id = :id")
    public Paciente buscarPacientePorID(@Param("id") String id);

    @Query("SELECT m FROM Paciente m WHERE m.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);

    List<Paciente> findByActivoTrue(); // Utiliza el nombre de método con la convención para filtrar pacientes activos

}
