package App.MediFour.MediFour.enumeraciones;

public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String nombreEnCastellano;

    DiaSemana(String nombreEnCastellano) {
        this.nombreEnCastellano = nombreEnCastellano;
    }

    public String getNombreEnCastellano() {
        return nombreEnCastellano;
    }
}//Class
