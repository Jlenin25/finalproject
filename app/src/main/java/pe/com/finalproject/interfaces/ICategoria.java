package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pe.com.finalproject.clases.Categoria;

public interface ICategoria {
    public boolean RegistrarCategoria(Categoria d, Context context);
    public boolean ActualizarCategoria(Categoria d);
    public boolean EliminarCategoria(Categoria d);
    public ArrayList<Categoria> MostrarCategoria(Context context);
}
