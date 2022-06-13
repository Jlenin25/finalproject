package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pe.com.finalproject.clases.Distrito;

public interface IDistrito {
    public boolean RegistrarDistrito(Distrito d, Context context);
    public boolean ActualizarDistrito(Distrito d);
    public boolean EliminarDistrito(Distrito d);
    public ArrayList<Distrito> MostrarDistrito(Context context);
}
