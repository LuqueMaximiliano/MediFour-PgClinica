package App.MediFour.MediFour.controladores;

import App.MediFour.MediFour.entidades.Usuario;
import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar") //localhost:8080/usuario/listar
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.listarTodosUsuarios();
        model.addAttribute("usuario", usuarios);

        return "PanelAdminUsuarios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/bajaUsuario/{id}")
    public String bajaUsuario(@PathVariable String id, ModelMap model) throws MiExcepcion {
        usuarioServicio.bajaUsuario(id);
        return "redirect:/usuario/listar";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/altaUsuario/{id}")
    public String altaUsuario(@PathVariable String id, ModelMap model) throws MiExcepcion {
        usuarioServicio.altaUsuario(id);
        return "redirect:/usuario/listar";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/cambiar-rol/{id}")
    public String cambiarRol(@PathVariable String id, ModelMap model) throws MiExcepcion {

        usuarioServicio.cambiarRol(id); // Llama al método de servicio para cambiar el rol
        return "redirect:/usuario/listar";

    }

}//Class
