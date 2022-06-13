package pe.com.finalproject.clases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Distrito {
    private long codigo;
    private String nombre;
    private boolean estado;
}