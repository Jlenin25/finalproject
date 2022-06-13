package pe.com.finalproject.interfaces;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pe.com.finalproject.clases.Perfil;

public interface IPerfil {
    public boolean RegistrarPerfil(Perfil p, Context context);
    public boolean ActualizarPerfil(Perfil p);
    public boolean EliminarPerfil(Perfil p);
    public ArrayList<Perfil> MostrarPerfil(Context context);
}
