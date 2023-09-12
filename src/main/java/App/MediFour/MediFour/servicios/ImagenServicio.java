package App.MediFour.MediFour.servicios;

import App.MediFour.MediFour.entidades.Imagen;
import App.MediFour.MediFour.excepciones.MiExcepcion;
import App.MediFour.MediFour.repositorios.ImagenRepositorio;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws MiExcepcion {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                throw new MiExcepcion("Error al guardar la imagen.");
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExcepcion {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen ImagenPacientePorDefecto() {
        try {
            // Carga la imagen por defecto desde la ruta relativa
            Resource resource = new ClassPathResource("static/assets/img/pacientePorDefecto.jpg");
            InputStream inputStream = resource.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            // Convierte la imagen cargada en un arreglo de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] contenidoImagenPorDefecto = outputStream.toByteArray();

            // Crea una nueva instancia de Imagen y asigna el contenido
            Imagen imagenPorDefecto = new Imagen();
            imagenPorDefecto.setMime("image/jpeg"); // Establece el tipo MIME apropiado
            imagenPorDefecto.setNombre("pacientePorDefecto.jpg"); // Nombre de la imagen por defecto
            imagenPorDefecto.setContenido(contenidoImagenPorDefecto);

            // Guarda la imagen por defecto en la base de datos antes de retornarla
            imagenRepositorio.save(imagenPorDefecto);

            return imagenPorDefecto;
        } catch (IOException e) {
            // Maneja la excepción apropiadamente, por ejemplo, registrándola o lanzándola nuevamente
            e.printStackTrace();
            // Retorna null o maneja el caso de error de otra manera
            return null;
        }
    }

    public Imagen ImagenProfesionalPorDefecto() {
        try {
            // Carga la imagen por defecto desde la ruta relativa
            Resource resource = new ClassPathResource("static/assets/img/profesionalPorDefecto.jpg");
            InputStream inputStream = resource.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            // Convierte la imagen cargada en un arreglo de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] contenidoImagenPorDefecto = outputStream.toByteArray();

            // Crea una nueva instancia de Imagen y asigna el contenido
            Imagen imagenPorDefecto = new Imagen();
            imagenPorDefecto.setMime("image/jpeg"); // Establece el tipo MIME apropiado
            imagenPorDefecto.setNombre("profesionalPorDefecto.jpg"); // Nombre de la imagen por defecto
            imagenPorDefecto.setContenido(contenidoImagenPorDefecto);

            // Guarda la imagen por defecto en la base de datos antes de retornarla
            imagenRepositorio.save(imagenPorDefecto);

            return imagenPorDefecto;
        } catch (IOException e) {
            // Maneja la excepción apropiadamente, por ejemplo, registrándola o lanzándola nuevamente
            e.printStackTrace();
            // Retorna null o maneja el caso de error de otra manera
            return null;
        }
    }

//    
}//class
