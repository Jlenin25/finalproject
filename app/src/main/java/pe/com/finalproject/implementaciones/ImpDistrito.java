package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import pe.com.finalproject.clases.Distrito;
import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.interfaces.IDistrito;
import pe.com.finalproject.bd.Conexion;

public class ImpDistrito implements IDistrito {
    private ArrayList<Distrito> registrodistrito=null;
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


    public ImpDistrito() {
        registrodistrito=new ArrayList<>();
    }

    @Override
    public boolean RegistrarDistrito(Distrito d, Context context) {


        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomdis",d.getNombre());
            valores.put("estdis",d.isEstado());
            //ejecutamos la sentencia
            long res=database.insert("t_distrito",null,valores);
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
    public boolean ActualizarDistrito(Distrito d) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomdis",d.getNombre());
            valores.put("estdis",d.isEstado());
            //ejecutamos la sentencia
            int res=(int)database.update("t_distrito",valores,"coddis="+d.getCodigo(),null);
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
    public boolean EliminarDistrito(Distrito d) {
        try {
            valores=new ContentValues();
            valores.put("estdis",0);
            int res=(int)database.update("t_distrito",valores,"codis="+d.getCodigo(),null);
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
    public ArrayList<Distrito> MostrarDistrito(Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery("select * from t_distrito where estdis=1",null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //creamos un objeto de la clase Perfil
                Distrito objdistrito=new Distrito();
                //asignamos los valores
                objdistrito.setCodigo(cursor.getInt(0));
                objdistrito.setNombre(cursor.getString(1));
                if(cursor.getInt(2)==1){
                    objdistrito.setEstado(true);
                }else{
                    objdistrito.setEstado(false);
                }
                //enviamos los datos al arreglo
                registrodistrito.add(objdistrito);

            }
        }else{
            registrodistrito=null;
        }
        return registrodistrito;
    }
}
