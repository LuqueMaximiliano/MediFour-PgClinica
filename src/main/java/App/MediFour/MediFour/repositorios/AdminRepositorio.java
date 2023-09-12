package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepositorio extends JpaRepository<Admin, String> {

    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    public Admin buscarPorEmail(@Param("email") String email);

    @Query("SELECT a FROM Admin a WHERE a.telefono = :telefono")
    public Admin buscarPorTelefono(@Param("telefono") String telefono);
}
