package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pe.com.finalproject.bd.Conexion;
import pe.com.finalproject.clases.Producto;

import pe.com.finalproject.clases.Categoria;
import pe.com.finalproject.interfaces.IProducto;

public class ImpProducto implements IProducto {
    Producto objproducto =new Producto();
    private ArrayList<Producto> registroproducto =null;
    private SQLiteDatabase database;
    private Cursor cursor;
    private ContentValues valores;
    private SQLiteOpenHelper xcon;
    private int version=1;
    private String nombre="bdregistro2022";
    private Conexion objconexion;

    public ImpProducto() {
        registroproducto =new ArrayList<>();
    }


    @Override
    public boolean RegistrarProducto(Producto e, Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nompro",e.getNombre());
            valores.put("precpro",e.getPreciocompra());
            valores.put("prevpro",e.getPrecioventa());
            valores.put("canpro",e.getCantidad());
            valores.put("estpro",e.isEstado());
            valores.put("codcat",e.getCategoria().getCodigo());
            //ejecutamos la sentencia
            long res=database.insert("t_producto",null,valores);

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
    public boolean ActualizarProducto(Producto e) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nompro",e.getNombre());
            valores.put("precpro",e.getPreciocompra());
            valores.put("prevpro",e.getPrecioventa());
            valores.put("canpro",e.getCantidad());
            valores.put("estpro",e.isEstado());
            valores.put("codcat",e.getCategoria().getCodigo());

            Log.e("categoria",""+e.getCategoria().getCodigo());

            valores.put("codcat",e.getCategoria().getCodigo());
            //ejecutamos la sentencia
            int res=(int)database.update("t_producto",valores,"codpro="+e.getCodigo(),null);
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
    public boolean EliminarProducto(Producto e) {
        try {
            valores=new ContentValues();
            valores.put("estpro",0);
            int res=(int)database.update("t_producto",valores,"codpro="+e.getCodigo(),null);
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
    public ArrayList<Producto> MostrarProducto(Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery(
                "select " +
                        "p.codpro, " +
                        "p.nompro, " +
                        "p.precpro, " +
                        "p.prevpro, " +
                        "p.canpro, " +
                        "p.estpro, " +
                        "c.codcat, " +
                        "c.nomcat from t_producto p " +
                        "inner join t_categoria c on p.codcat=c.codcat " +
                        "where p.estpro=1",


                null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //asignamos los valores
                Producto objproducto =new Producto();
                Categoria objcategoria=new Categoria();
                objproducto.setCodigo(cursor.getInt(0));
                objproducto.setNombre(cursor.getString(1));
                objproducto.setPreciocompra(cursor.getString(2));
                objproducto.setPrecioventa(cursor.getString(3));
                objproducto.setCantidad(cursor.getString(4));
                if(cursor.getInt(5)==1){
                    objproducto.setEstado(true);
                }else{
                    objproducto.setEstado(false);
                }

                objcategoria.setCodigo(cursor.getInt(6));
                objcategoria.setNombre(cursor.getString(7));
                objproducto.setCategoria(objcategoria);

                //enviamos los datos al arreglo
                registroproducto.add(objproducto);

            }
        }else{
            registroproducto =null;
        }
        return registroproducto;
    }
}
