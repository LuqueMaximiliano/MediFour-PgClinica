package App.MediFour.MediFour.servicios;

import App.MediFour.MediFour.entidades.Usuario;

import App.MediFour.MediFour.enumeraciones.Rol;

import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.repositorios.UsuarioRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {
    //se implementa la interfaz UserDetailsService para poder autenticar a los 
    //usuarios que se logueen

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    public Usuario getOne(String id) {
        return usuarioRepo.getOne(id);
    }

    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws MiExcepcion {

        validarCorto(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);

        usuario.setPassword(password);

        usuario.setRol(Rol.USER);

        usuarioRepo.save(usuario);

    }

    private void validarCorto(String nombre, String email, String password, String password2) throws MiExcepcion {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("El email no puede ser nulo o estar vacio");
        }

        if (password.isEmpty() || password == null || password.length() >= 5) {
            throw new MiExcepcion("La contraseña no puede estar vacia, y debe tener mas de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> aux = new ArrayList();
        List<Usuario> usuarios = new ArrayList();
        aux = usuarioRepo.findAll();
        for (Usuario usuario : aux) {
            if (usuario.getActivo().equals(Boolean.TRUE)) {
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepo.findByActivoTrue();
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepo.findAll();
    }

    @Transactional
    public void bajaUsuario(String id) throws MiExcepcion {
        if (id == null || id.isEmpty()) {
            throw new MiExcepcion("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            Usuario user = (Usuario) respuesta.get();
            user.setActivo(false); // Establece el estado del profesional como inactivo
            usuarioRepo.save(user);
        }
    }

    @Transactional
    public void altaUsuario(String id) throws MiExcepcion {
        if (id == null || id.isEmpty()) {
            throw new MiExcepcion("El id ingresado no puede ser nulo o estar vacio");
        }
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            Usuario user = (Usuario) respuesta.get();
            user.setActivo(true); // Establece el estado del profesional como activo
            usuarioRepo.save(user);
        }
    }

    public void validar(String nombre, String apellido, LocalDate fechaNacimiento,
            Integer dni, String telefono, String email, String password, String password2) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede ser nulo o estar vacío.");
        }
        if (fechaNacimiento == null) {
            throw new MiExcepcion("La fecha de nacimiento no puede ser nula.");
        }

        if (dni == null || dni < 999) {
            throw new MiExcepcion("El DNI no puede ser nulo y debe contener al menos 4 dígitos.");
        }
        if (usuarioRepo.buscarPorDni(dni) != null) {
            throw new MiExcepcion("El DNI está en uso.");
        }
        if (telefono == null || telefono.isEmpty() || telefono.length() <= 4) {
            throw new MiExcepcion("El teléfono no puede estar vacío y debe tener más de 4 caracteres.");
        }
        if (usuarioRepo.buscarPorTelefono(telefono) != null) {
            throw new MiExcepcion("El teléfono ya está en uso.");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede ser nulo o estar vacío.");
        }
        if (usuarioRepo.buscarPorEmail(email) != null) {
            throw new MiExcepcion("El email ya está en uso.");
        }
        if (password == null || password.isEmpty() || password.length() <= 4) {
            throw new MiExcepcion("La contraseña no puede estar vacía y debe tener más de 4 caracteres.");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales.");
        }
    }

    public void validarModificarUsuario(String nombre, String apellido, LocalDate fechaNacimiento,
            Integer dni, String telefono, String email) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede ser nulo o estar vacío.");
        }
        if (fechaNacimiento == null) {
            throw new MiExcepcion("La fecha de nacimiento no puede ser nula.");
        }

        if (dni == null || dni < 999) {
            throw new MiExcepcion("El DNI no puede ser nulo y debe contener al menos 4 dígitos.");
        }
        /*if (usuarioRepo.buscarPorDni(dni) != null) {
            throw new MiExcepcion("El DNI está en uso.");
        }*/
        if (telefono == null || telefono.isEmpty() || telefono.length() <= 4) {
            throw new MiExcepcion("El teléfono no puede estar vacío y debe tener más de 4 caracteres.");
        }
        /*if (usuarioRepo.buscarPorTelefono(telefono) != null) {
            throw new MiExcepcion("El teléfono ya está en uso.");
        }*/
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede ser nulo o estar vacío.");
        }
        /*if (usuarioRepo.buscarPorEmail(email) != null) {
            throw new MiExcepcion("El email ya está en uso.");
        }*/

    }

    public void validarModificarPaciente(String nombre, String apellido, LocalDate fechaNacimiento,
            Integer dni, String telefono, String email, String password, String password2) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("El nombre no puede ser nulo o estar vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("El apellido no puede ser nulo o estar vacío.");
        }
        if (fechaNacimiento == null) {
            throw new MiExcepcion("La fecha de nacimiento no puede ser nula.");
        }

        if (dni == null || dni < 999) {
            throw new MiExcepcion("El DNI no puede ser nulo y debe contener al menos 4 dígitos.");
        }
        /*if (usuarioRepo.buscarPorDni(dni) != null) {
            throw new MiExcepcion("El DNI está en uso.");
        }*/
        if (telefono == null || telefono.isEmpty() || telefono.length() <= 4) {
            throw new MiExcepcion("El teléfono no puede estar vacío y debe tener más de 4 caracteres.");
        }
        /*if (usuarioRepo.buscarPorTelefono(telefono) != null) {
            throw new MiExcepcion("El teléfono ya está en uso.");
        }*/
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("El email no puede ser nulo o estar vacío.");
        }
        /*if (usuarioRepo.buscarPorEmail(email) != null) {
            throw new MiExcepcion("El email ya está en uso.");
        }*/
        if (password == null || password.isEmpty() || password.length() <= 4) {
            throw new MiExcepcion("La contraseña no puede estar vacía y debe tener más de 4 caracteres.");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales.");
        }

    }

    //clase abstracta de UserDetailsService para poder autenticar a usuarios    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo.buscarPorEmail(email);

        if (usuario != null && usuario.getActivo().equals(Boolean.TRUE)) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); //Rol usuario

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            switch (usuario.getRol()) {
                case USER:
                    usuario.setRol(Rol.PROFESIONAL);
                    break;
                case PROFESIONAL:
                    usuario.setRol(Rol.ADMIN);
                    break;
                case ADMIN:
                    usuario.setRol(Rol.USER);
                    break;
                default:
                    break;
            }
        }
    }

    //prueba
}
