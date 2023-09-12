package App.MediFour.MediFour.repositorios;

import App.MediFour.MediFour.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
