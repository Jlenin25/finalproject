package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pe.com.finalproject.bd.Conexion;
import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.interfaces.IPerfil;

public class ImpPerfil implements IPerfil {
    private ArrayList<Perfil> registroperfil=null;
    //creamos una variable para la base de datos
    private SQLiteDatabase database;
    //creamos un cursor --> ResultSet
    private Cursor cursor;
    //creamos una variable para el manejo de datos
    private ContentValues valores;
    //creamos una variable para la conexion
    private SQLiteOpenHelper xcon;
    //creamos una variable para la version de la BD
    private int version=1;
    //creamos una variable para el nombre de la base de datos
    private String nombre="bdregistro2022";
    //creamos un objeto de la clase Conexion
    private Conexion objconexion;

    public ImpPerfil() {
        registroperfil=new ArrayList<>();
    }

    @Override
    public boolean RegistrarPerfil(Perfil p, Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomper",p.getNombre());
            valores.put("estper",p.isEstado());
            //ejecutamos la sentencia
            long res=database.insert("t_perfil",null,valores);
            if(res>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            Log.e("Error registro:",ex.toString());
            return false;
        }
    }

    @Override
    public boolean ActualizarPerfil(Perfil p) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomper",p.getNombre());
            valores.put("estper",p.isEstado());
            //ejecutamos la sentencia
            int res=(int)database.update("t_perfil",valores,"codper="+p.getCodigo(),null);
            if(res==1){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            Log.e("Error registro:",ex.toString());
            return false;
        }
    }

    @Override
    public boolean EliminarPerfil(Perfil p) {
        try {
            valores=new ContentValues();
            valores.put("estper",0);
            int res=(int)database.update("t_perfil",valores,"codper="+p.getCodigo(),null);
            if(res==1){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            Log.e("Error registro:",ex.toString());
            return false;
        }
    }

    @Override
    public ArrayList<Perfil> MostrarPerfil(Context context ) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery("select * from t_perfil where estper=1",null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //creamos un objeto de la clase Perfil
                Perfil objperfil=new Perfil();
                //asignamos los valores
                objperfil.setCodigo(cursor.getInt(0));
                objperfil.setNombre(cursor.getString(1));
                if(cursor.getInt(2)==1){
                    objperfil.setEstado(true);
                }else{
                    objperfil.setEstado(false);
                }
                //enviamos los datos al arreglo
                registroperfil.add(objperfil);

            }
        }else{
            registroperfil=null;
        }
        return registroperfil;
    }
}
