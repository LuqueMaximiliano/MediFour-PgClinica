package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepositorio extends JpaRepository<FichaMedica, String> {

    @Query("SELECT fm FROM FichaMedica fm JOIN fm.turno t WHERE t.paciente.id = :idPaciente AND t.profesional.id = :idMedico")
    FichaMedica buscarFichaMedicaPorPacienteYMedico(@Param("idPaciente") String idPaciente, @Param("idMedico") String idMedico);
}
