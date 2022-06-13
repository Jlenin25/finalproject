package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;

import pe.com.finalproject.clases.Empleado;

public interface IEmpleado {
    public boolean ValidarUsuario(Empleado d, Context context);
    public boolean RegistrarEmpleado(Empleado d, Context context);
    public boolean ActualizarEmpleado(Empleado d);
    public boolean EliminarEmpleado(Empleado d);
    public ArrayList<Empleado> MostrarEmpleado(Context context);
}