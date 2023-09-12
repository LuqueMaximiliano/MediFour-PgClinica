package App.MediFour.MediFour.servicios;

import App.MediFour.MediFour.entidades.FichaMedica;
import App.MediFour.MediFour.entidades.Paciente;
import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.entidades.Turno;
import App.MediFour.MediFour.repositorios.FichaMedicaRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaMedicaServicio {

    @Autowired
    private FichaMedicaRepositorio fichaMedicaRepo;

    public FichaMedica traerFichaMedica(Paciente paciente, Profesional profesional) {
        // Obtener la ficha médica correspondiente al turno actual
        FichaMedica fichaMedica = fichaMedicaRepo.buscarFichaMedicaPorPacienteYMedico(paciente.getId(), profesional.getId());

        return fichaMedica;

    }

    public void crearFicha(Turno turno) {
        FichaMedica ficha = new FichaMedica();
        ficha.setTurno(turno);
        fichaMedicaRepo.save(ficha);

    }

    public void agregarNota(String idFicha, String nota) {
        Optional<FichaMedica> respuesta = fichaMedicaRepo.findById(idFicha);
        FichaMedica ficha = respuesta.get();
        ficha.setNotas(nota);
        fichaMedicaRepo.save(ficha);
    }

}
