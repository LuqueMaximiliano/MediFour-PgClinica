package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email =:email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT t FROM Usuario t WHERE t.telefono =:telefono")
    public Usuario buscarPorTelefono(@Param("telefono") String telefono);

    @Query("SELECT d FROM Usuario d WHERE d.dni =:dni")
    public Usuario buscarPorDni(@Param("dni") Integer dni);
    
    @Query("SELECT i FROM Usuario i WHERE i.id =:id")
    public Usuario buscarPorId(@Param("id") String id);

    List<Usuario> findByActivoTrue(); // Utiliza el nombre de método con la convención para filtrar profesionales activos

}
