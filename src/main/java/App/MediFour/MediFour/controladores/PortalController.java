package App.MediFour.MediFour.controladores;

import App.MediFour.MediFour.entidades.Profesional;
import App.MediFour.MediFour.entidades.Usuario;
import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author scand
 */
@Controller
@Slf4j
public class PortalController {

    @Autowired
    private UsuarioServicio usuarioServicio;
    

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @GetMapping("/register_usuario")
    public String mostrarPaginaRegistro() {
        return "register_usuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");

            return "login.html";
        } catch (MiExcepcion ex) {
            Logger.getLogger(PortalController.class.getName()).log(Level.SEVERE, null, ex);

            modelo.put("error", ex.getMessage());

            return "registro.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false)String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos");
            }
        
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap model){
        
        //Profesional profesional = new Profesional();
        
        //model.addAttribute("profesional",profesional);
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
       /*if(logueado.getRol().toString().equals("PROFESIONAL")){
                return "redirect:/admin/dashboard";
       }*/
        
        return "inicio.html";
    }
}
