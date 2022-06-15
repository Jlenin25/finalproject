package pe.com.finalproject.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distrito {

    @SerializedName("codigo")
    @Expose
    private long codigo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("estado")
    @Expose
    private boolean estado;

    //contructor

    public Distrito() {
    }

    public Distrito(long codigo, String nombre, boolean estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}