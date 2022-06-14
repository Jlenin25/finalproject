package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;

import pe.com.finalproject.clases.Producto;

public interface IProducto {
    public boolean RegistrarProducto(Producto c, Context context);
    public boolean ActualizarProducto(Producto c);
    public boolean EliminarProducto(Producto c);
    public ArrayList<Producto> MostrarProducto(Context context);
}