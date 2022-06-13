package pe.com.finalproject.clases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Empleado {
    private long codigo;
    private String nombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private String dni;
    private String direccion;
    private String telefono;
    private String celular;
    private String correo;
    private String sexo;
    private boolean estado;
    private Distrito distrito;
}