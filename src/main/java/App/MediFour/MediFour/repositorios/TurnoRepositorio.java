package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Paciente;
import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.entidades.Turno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    @Query("SELECT t FROM Turno t WHERE t.profesional = :profesional")
    public List<Turno> buscarTurnosProfesional(@Param("profesional") Profesional profesional);

    @Query("SELECT t FROM Turno t WHERE t.paciente = :paciente")
    public List<Turno> buscarTurnosPaciente(@Param("paciente") Paciente paciente);

    @Query("SELECT DISTINCT t.paciente FROM Turno t WHERE t.profesional.id = :profesionalId")
    List<Paciente> obtenerNombresPacientesConTurnoPorMedico(@Param("profesionalId") String profesionalId);

    List<Turno> findByProfesionalOrderByFechaAscHoraAsc(Profesional profesional);

    List<Turno> findByPaciente(Paciente paciente);

    List<Turno> findByProfesional(Profesional profesional);
}
