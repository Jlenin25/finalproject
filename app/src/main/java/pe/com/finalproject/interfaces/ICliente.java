package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;

import pe.com.finalproject.clases.Cliente;

public interface ICliente {
    public boolean ValidarUsuario(Cliente c, Context context);
    public boolean RegistrarCliente(Cliente c, Context context);
    public boolean ActualizarCliente(Cliente c);
    public boolean EliminarCliente(Cliente c);
    public ArrayList<Cliente> MostrarCliente(Context context);
}
