package App.MediFour.MediFour.controladores;

import App.MediFour.MediFour.entidades.Paciente;
import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.entidades.Turno;
import App.MediFour.MediFour.servicios.ProfesionalServicio;
import App.MediFour.MediFour.servicios.TurnoServicio;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private TurnoServicio turnoServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listar")
    public String obtenerTodosLosTurnos(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado utilizando la sesión
        if (session.getAttribute("usuariosession") == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login.html";
        } else {
            List<Turno> turno = turnoServicio.obtenerTodosLosTurnos();
            model.addAttribute("turnos", turno);
            return "turno_List.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listar/{profesionalId}")
    public String obtenerTurnosDeProfesional(@PathVariable("profesionalId") String profesionalId, ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado utilizando la sesión
        if (session.getAttribute("usuariosession") == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login.html";
        } else {
            // Recupera el profesional por su ID
            Profesional profesional = profesionalServicio.profesionalPorID(profesionalId);

            if (profesional != null) {
                // Utiliza el servicio para obtener los turnos del profesional y agregarlos al modelo
                List<Turno> turnos = turnoServicio.obtenerTurnosDeProfesionalOrdenados(profesional);
                model.addAttribute("turnos", turnos);
                model.addAttribute("profesional", profesional);

                return "turno_List.html";
            } else {
                // El profesional no se encontró, maneja la lógica adecuada aquí
                return "error.html";
            }
        }
    }

    @PostMapping("/agendar-turno")
    public String agendarTurno(@RequestParam String turnoId, HttpSession session, ModelMap modelo) {
        try {
            // Verifica si el usuario está autenticado utilizando la sesión
            Paciente paciente = (Paciente) session.getAttribute("usuariosession");
            if (paciente == null) {
                modelo.addAttribute("error", "Debes estar logueado para agendar un turno.");
                return "login.html";
            }
            // Llama al servicio para asignar el turno al paciente
            turnoServicio.asignarTurnoAPaciente(turnoId, paciente);
            modelo.addAttribute("exito", "Turno agendado correctamente.");
            // Si no hay URL original, redirige al usuario a la página de lista de turnos
            return "turno_List.html";
        } catch (Exception ex) {
            modelo.addAttribute("error", "Error al agendar el turno: " + ex.getMessage());
            return "turno_List.html"; // Otra página de error o manejo de errores según tus necesidades
        }
    }

    @GetMapping("/mis_turnos")
    public String obtenerMisTurnos(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado como paciente
        Paciente paciente = (Paciente) session.getAttribute("usuariosession");
        if (paciente == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login.html";
        }

        // Recupera los turnos del paciente desde la base de datos
        List<Turno> misTurnos = turnoServicio.obtenerTurnosDelPaciente(paciente);
        model.addAttribute("misTurnos", misTurnos);

        return "mis_turnos.html";
    }

    @PreAuthorize("hasRole('PROFESIONAL')")
    @GetMapping("/mis_turnos_profesional")
    public String obtenerMisTurnosProfesional(ModelMap model, HttpSession session) {
        // Verifica si el usuario está autenticado como paciente
        Profesional profesional = (Profesional) session.getAttribute("usuariosession");
        if (profesional == null) {
            // Si no está autenticado, redirige al usuario a la página de inicio de sesión
            return "login.html";
        }

        // Recupera los turnos del paciente desde la base de datos
        List<Turno> misTurnosProfesional = turnoServicio.obtenerTurnosDeProfesional(profesional);
        model.addAttribute("misTurnosProfesional", misTurnosProfesional);

        return "mis_turnos_profesional.html";
    }

}//Class
