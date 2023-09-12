package App.MediFour.MediFour.controladores;

import App.MediFour.MediFour.entidades.Paciente;
import App.MediFour.MediFour.entidades.Turno;
import App.MediFour.MediFour.entidades.Usuario;
import App.MediFour.MediFour.enumeraciones.ObraSocial;
import App.MediFour.MediFour.enumeraciones.Rol;
import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.repositorios.PacienteRepositorio;
import App.MediFour.MediFour.servicios.PacienteServicio;
import App.MediFour.MediFour.servicios.TurnoServicio;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private TurnoServicio turnoServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private PacienteRepositorio pacienteRepo;

    @GetMapping("/registrar-form")
    public String mostrarFormularioRegistro(Model modelo) {
//        try {
//            modelo.addAttribute("tieneObraSocial", false);
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//            return "paciente_form.html";
//        }
        return "paciente_form.html";
    }

    @PostMapping("/registrar")
    public String registrarPaciente(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam Integer dni,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam(required = false) Boolean tieneObraSocial,
            @RequestParam(required = false) ObraSocial obraSocial,
            @RequestParam(required = false) Integer numeroAfiliado,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo) {

        try {
            //Boolean tieneObraSocialBool = tieneObraSocial != null && tieneObraSocial.equals("true");
            pacienteServicio.registrarPaciente(archivo, nombre, apellido, fechaNacimiento, dni, telefono, email, tieneObraSocial, obraSocial, numeroAfiliado, password, password2);

            modelo.put("exito", "El paciente fue registrado correctamente!");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "paciente_form.html";
        }
        return "redirect:/login";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listar") //localhost:8080/paciente/listar
    public String listarPacientesActivos(Model model) {
        List<Paciente> pacientes = pacienteServicio.listarPacientesActivos();
        model.addAttribute("pacientes", pacientes);

        return "paciente_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/listarAdmin") //localhost:8080/paciente/listar
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteServicio.listarTodosPacientes();
        model.addAttribute("pacientes", pacientes);

        return "PanelAdminPacientes.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/consultaDni") //localhost:8080/paciente/consulta
    public String listarPacienteXdni(@RequestParam Integer dni, ModelMap model) {
        Paciente paciente = pacienteServicio.listarPacienteXdni(dni);
        model.addAttribute("paciente", paciente);

        return "paciente_consulta";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/consultaObra") //localhost:8080/paciente/consulta
    public String listarPacientesXobraSocial(@RequestParam("obra") String obraValue, ModelMap model) {
        ObraSocial obra = ObraSocial.valueOf(obraValue);//se transforma obraValue a enum ObraSocial
        List<Paciente> paciente = pacienteServicio.listarPacientesXobraSocial(obra);
        model.addAttribute("paciente", paciente);

        return "paciente_consulta";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/bajaPaciente/{id}")
    public String bajaPaciente(@PathVariable String id, ModelMap model) throws MiExcepcion {
        pacienteServicio.bajaPaciente(id);

        return "redirect:/paciente/listar";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/altaPaciente/{id}")
    public String altaPaciente(@PathVariable String id, ModelMap model) throws MiExcepcion {
        pacienteServicio.altaPaciente(id);

        return "redirect:/paciente/listar";
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/perfil/{id}") //localhost:8080/paciente/perfil
    public String mostrarPacientePerfil(@PathVariable String id, ModelMap model, HttpSession session) {

        Paciente paciente = pacienteServicio.pacientePorID(id);

        model.addAttribute("paciente", paciente);

        return "paciente_perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}") //localhost:8080/paciente/perfil
    public String actualizarPacientePerfil(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam Integer dni,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam(required = false) Boolean tieneObraSocial,
            @RequestParam(required = false) ObraSocial obraSocial,
            @RequestParam(required = false) Integer numeroAfiliado,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam(required = false) MultipartFile archivo,
            ModelMap modelo) throws MiExcepcion {
        try {
            pacienteServicio.actualizarPaciente(archivo, id, nombre, apellido, fechaNacimiento, dni, telefono, email, tieneObraSocial, obraSocial, numeroAfiliado, password, password2);
            modelo.put("exito", "Paciente actualizado correctamente!");
            System.out.println("Estoy en Post Controller Try");
            return "redirect:/login";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            System.out.println("Estoy en Post Controller Catch");
            return "paciente_perfil";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/ver-turno/{id}") // Ruta para ver el detalle de un turno
    public String verDetalleTurno(@PathVariable String id, ModelMap model) {
        try {
            Turno turno = turnoServicio.obtenerTurnoPorId(id);

            if (turno != null) {
                model.addAttribute("turno", turno);
                return "ver_turno.html"; // Renderiza la página de detalle de turno
            } else {
                model.addAttribute("error", "No se encontró el turno.");
            }
        } catch (MiExcepcion ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/paciente/listar-turnos"; // Redirige a la lista de turnos si hay un error
    }

// El paciente elige un turno existente
    @PostMapping("/elegir-turno")
    public ResponseEntity<String> elegirTurno(@RequestParam String idTurno, @RequestParam String idPaciente) {
        try {
            Paciente paciente = pacienteServicio.pacientePorID(idTurno);
            Turno turno = turnoServicio.obtenerTurnoPorId(idTurno);

            boolean exito = turnoServicio.elegirTurno(idTurno, paciente);

            if (exito) {
                return ResponseEntity.ok("Turno elegido exitosamente.");
            } else {
                return ResponseEntity.badRequest().body("No se pudo elegir el turno.");
            }
        } catch (MiExcepcion e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }

}//Class

