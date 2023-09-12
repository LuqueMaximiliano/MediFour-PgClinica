package App.MediFour.MediFour.servicios;

import App.MediFour.MediFour.entidades.Imagen;
import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.entidades.Usuario;
import App.MediFour.MediFour.enumeraciones.DiaSemana;
import App.MediFour.MediFour.enumeraciones.Especialidad;
import App.MediFour.MediFour.enumeraciones.Rol;
import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.repositorios.ProfesionalRepositorio;
import App.MediFour.MediFour.repositorios.UsuarioRepositorio;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfesionalServicio extends UsuarioServicio {

    @Autowired
    private ProfesionalRepositorio profesionalRepo;

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarProfesional(MultipartFile archivo, String nombre, String apellido, LocalDate fechaNacimiento,
            Integer dni, String telefono, String email, String matricula,
            Especialidad especialidad, List<DiaSemana> diasDisponibles,
            LocalTime horarioEntrada, LocalTime horarioSalida, Double precioConsulta, String observaciones,
            String password, String password2) throws MiExcepcion {

        Profesional profesional = new Profesional();
        usuarioServicio.validar(nombre, apellido, fechaNacimiento, dni, telefono, email, password, password2);
        validarProfesional(matricula, especialidad, diasDisponibles, horarioEntrada, horarioSalida, precioConsulta);

        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setFechaNacimiento(fechaNacimiento);
        profesional.setDni(dni);
        profesional.setTelefono(telefono);
        profesional.setEmail(email);

        // Configurar atributos específicos de Profesional
        profesional.setMatricula(matricula);
        profesional.setEspecialidad(especialidad);
        profesional.setDiasDisponibles(diasDisponibles);
        profesional.setHorarioEntrada(horarioEntrada);
        profesional.setHorarioSalida(horarioSalida);
        profesional.setPrecioConsulta(precioConsulta);
        profesional.setObservaciones(observaciones);
        profesional.setReputacion(0.0); // Establecer la reputación en 0.0

        profesional.setActivo(false); //lo dajamos inactivo hasta q un admin lo valide
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setRol(Rol.PROFESIONAL);

// Comprobar si se proporcionó un nuevo archivo de imagen y guardarla
        if (archivo != null && !archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            profesional.setImagen(imagen);
        } else {
            // Si no se proporcionó una imagen, asigna la imagen por defecto desde el servicio de Imagen
            Imagen imagenPorDefecto = imagenServicio.ImagenProfesionalPorDefecto();
            if (imagenPorDefecto != null) {
                profesional.setImagen(imagenPorDefecto);
            } else {
                // Si no se pudo obtener la imagen por defecto, maneja el caso de error de alguna manera
                throw new MiExcepcion("No se pudo obtener la imagen por defecto.");
            }
        }

        profesionalRepo.save(profesional);
    }

    public List<Profesional> listarProfesionalesActivos() {
        return profesionalRepo.findByActivoTrue();
    }

    public List<Profesional> listarTodosProfesionales() {
        return profesionalRepo.findAll();
    }

    public Profesional profesionalPorID(String id) {
        return profesionalRepo.getOne(id);
    }

    @Transactional
    public void bajaProfesional(String id) throws MiExcepcion {
        if (id == null || id.isEmpty()) {
            throw new MiExcepcion("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = (Profesional) respuesta.get();
            profesional.setActivo(false); // Establece el estado del profesional como inactivo
            usuarioRepo.save(profesional);
        }
    }

    @Transactional
    public void altaProfesional(String id) throws MiExcepcion {
        if (id == null || id.isEmpty()) {
            throw new MiExcepcion("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            Profesional profesional = (Profesional) respuesta.get();
            profesional.setActivo(true); // Establece el estado del profesional como activo
            usuarioRepo.save(profesional);
        }
    }

    public void validarProfesional(String matricula, Especialidad especialidad, List<DiaSemana> diasDisponibles,
            LocalTime horarioEntrada, LocalTime horarioSalida, Double precioConsulta) throws MiExcepcion {

        if (matricula == null || matricula.isEmpty()) {
            throw new MiExcepcion("La matrícula no puede ser nula o estar vacía.");
        }

        if (especialidad == null) {
            throw new MiExcepcion("La especialidad no puede ser nula.");
        }

        if (diasDisponibles == null || diasDisponibles.isEmpty()) {
            throw new MiExcepcion("Debe seleccionar al menos un día disponible.");
        }

        if (horarioEntrada == null || horarioSalida == null || horarioEntrada.isAfter(horarioSalida)) {
            throw new MiExcepcion("Los horarios de entrada y salida deben ser válidos.");
        }

        if (precioConsulta == null || precioConsulta <= 0) {
            throw new MiExcepcion("El precio de la consulta debe ser mayor que cero.");
        }
    }

    @Transactional
    public void actualizarProfesional(MultipartFile archivo, String id, String nombre, String apellido,
            LocalDate fechaNacimiento,
            Integer dni, String telefono, String email, String matricula,
            Especialidad especialidad, List<DiaSemana> diasDisponibles,
            LocalTime horarioEntrada, LocalTime horarioSalida, Double precioConsulta, String observaciones,
            Boolean activo) throws MiExcepcion {

        System.out.println("Estoy en el ServicioProfesional");

        usuarioServicio.validarModificarUsuario(nombre, apellido, fechaNacimiento, dni, telefono, email);
        validarProfesional(matricula, especialidad, diasDisponibles, horarioEntrada, horarioSalida, precioConsulta);

        System.out.println("Estoy en el ServicioProfesional despues de validar");

        Optional<Profesional> respuesta = profesionalRepo.findById(id);

        System.out.println("Estoy en el ServicioProfesional despues del optional");

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setFechaNacimiento(fechaNacimiento);
            profesional.setDni(dni);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);

            // Configurar atributos específicos de Profesional
            profesional.setMatricula(matricula);
            profesional.setEspecialidad(especialidad);
            profesional.setDiasDisponibles(diasDisponibles);
            profesional.setHorarioEntrada(horarioEntrada);
            profesional.setHorarioSalida(horarioSalida);
            profesional.setPrecioConsulta(precioConsulta);
            profesional.setObservaciones(observaciones);
            profesional.setActivo(activo);

            //probar este metodo para contraseña
            // Solo actualiza la contraseña si se proporciona una nueva
            /* if (!password.isEmpty() && !password.equals(profesional.getPassword())) {
                    profesional.setPassword(new BCryptPasswordEncoder().encode(password));
                }
                profesional.setPassword(new BCryptPasswordEncoder().encode(password));*/
            // Comprobar si se proporcionó un nuevo archivo de imagen
            if (archivo != null && !archivo.isEmpty()) {
                String idImagen = null;
                if (profesional.getImagen() != null) {
                    idImagen = profesional.getImagen().getId();
                }
                Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
                profesional.setImagen(imagen);
            }

            profesionalRepo.save(profesional);
        }
    }

}//Class
