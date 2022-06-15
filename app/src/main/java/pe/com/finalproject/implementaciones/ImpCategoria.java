package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import pe.com.finalproject.clases.Categoria;
import pe.com.finalproject.interfaces.ICategoria;
import pe.com.finalproject.bd.Conexion;

public class ImpCategoria implements ICategoria {
    private ArrayList<Categoria> registrocategoria =null;
    private SQLiteDatabase database;
    private Cursor cursor;
    private ContentValues valores;
    private SQLiteOpenHelper xcon;
    private int version=1;
    private String nombre="bdregistro2022";
    private Conexion objconexion;


    public ImpCategoria() {
        registrocategoria =new ArrayList<>();
    }

    @Override
    public boolean RegistrarCategoria(Categoria d, Context context) {


        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomcat",d.getNombre());
            valores.put("estcat",d.isEstado());
            //ejecutamos la sentencia
            long res=database.insert("t_categoria",null,valores);
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
    public boolean ActualizarCategoria(Categoria d) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomcat",d.getNombre());
            valores.put("estcat",d.isEstado());
            //ejecutamos la sentencia
            int res=(int)database.update("t_categoria",valores,"codcat="+d.getCodigo(),null);
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
    public boolean EliminarCategoria(Categoria d) {
        try {
            valores=new ContentValues();
            valores.put("estcat",0);
            int res=(int)database.update("t_categoria"
                    ,valores,"codcat="+d.getCodigo(),null);
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
    public ArrayList<Categoria> MostrarCategoria(Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery("select * from t_categoria where estcat=1",null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //creamos un objeto de la clase Perfil
                Categoria objcategoria =new Categoria();
                //asignamos los valores
                objcategoria.setCodigo(cursor.getInt(0));
                objcategoria.setNombre(cursor.getString(1));
                if(cursor.getInt(2)==1){
                    objcategoria.setEstado(true);
                }else{
                    objcategoria.setEstado(false);
                }
                //enviamos los datos al arreglo
                registrocategoria.add(objcategoria);

            }
        }else{
            registrocategoria =null;
        }
        return registrocategoria;
    }
}
