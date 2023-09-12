package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.HistoriaClinica;
import App.MediFour.MediFour.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT p FROM HistoriaClinica p WHERE p.paciente = :paciente")
    public HistoriaClinica buscarPaciente(@Param("paciente") Paciente paciente);

}
