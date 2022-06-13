package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pe.com.finalproject.bd.Conexion;
import pe.com.finalproject.clases.Distrito;
import pe.com.finalproject.clases.Empleado;
import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.interfaces.IEmpleado;

public class ImpEmpleado implements IEmpleado {
    Empleado objempleado=new Empleado();
    private ArrayList<Empleado> registroempleado=null;
    private SQLiteDatabase database;
    private Cursor cursor;
    private ContentValues valores;
    private SQLiteOpenHelper xcon;
    private int version=1;
    private String nombre="bdregistro2022";
    private Conexion objconexion;

    public ImpEmpleado() {
        registroempleado=new ArrayList<>();
    }


    @Override
    public boolean ValidarUsuario(Empleado e, Context context) {
        objconexion = new Conexion(context, nombre, null, version);
        database = objconexion.getWritableDatabase();
        String[] argumento=new String[]{e.getUsuario(),e.getClave()};
        cursor = database.rawQuery("select * from t_empleado where usuemp=? and claemp=?", argumento);
        int cont=0;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                cont++;
            }
        }
        if(cont==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean RegistrarEmpleado(Empleado e, Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomemp",e.getNombre());
            valores.put("apepemp",e.getApellidopaterno());
            valores.put("apememp",e.getApellidomaterno());
            valores.put("dniemp",e.getDni());
            valores.put("diremp",e.getDireccion());
            valores.put("telemp",e.getTelefono());
            valores.put("celemp",e.getCelular());
            valores.put("coremp",e.getCorreo());
            valores.put("sexemp",e.getSexo());
            valores.put("usuemp",e.getUsuario());
            valores.put("claemp",e.getClave());
            valores.put("estemp",e.isEstado());
            valores.put("coddis",e.getDistrito().getCodigo());
            valores.put("codper",e.getPerfil().getCodigo());
            //ejecutamos la sentencia
            long res=database.insert("t_empleado",null,valores);

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
    public boolean ActualizarEmpleado(Empleado e) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("codemp",e.getCodigo());
            valores.put("nomemp",e.getNombre());
            valores.put("apepemp",e.getApellidopaterno());
            valores.put("apememp",e.getApellidomaterno());
            valores.put("dniemp",e.getDni());
            valores.put("diremp",e.getDireccion());
            valores.put("telemp",e.getTelefono());
            valores.put("celemp",e.getCelular());
            valores.put("coremp",e.getCorreo());
            valores.put("sexemp",e.getSexo());
            valores.put("usuemp",e.getUsuario());
            valores.put("claemp",e.getClave());
            valores.put("estemp",e.isEstado());

            Log.e("distrito",""+e.getDistrito().getCodigo());
            Log.e("perfil",""+e.getPerfil().getCodigo());

            valores.put("coddis",e.getDistrito().getCodigo());
            valores.put("codper",e.getPerfil().getCodigo());
            //ejecutamos la sentencia
            int res=(int)database.update("t_empleado",valores,"codemp="+e.getCodigo(),null);
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
    public boolean EliminarEmpleado(Empleado e) {
        try {
            valores=new ContentValues();
            valores.put("estemp",0);
            int res=(int)database.update("t_empleado",valores,"codemp="+e.getCodigo(),null);
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
    public ArrayList<Empleado> MostrarEmpleado(Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery(
                "select " +
                        "e.codemp, " +
                        "e.nomemp, " +
                        "e.apepemp, " +
                        "e.apememp, " +
                        "e.dniemp, " +
                        "e.diremp, " +
                        "e.telemp, " +
                        "e.celemp, " +
                        "e.coremp, " +
                        "e.sexemp, " +
                        "e.usuemp, " +
                        "e.claemp," +
                        "e.estemp, " +
                        "p.codper, " +
                        "p.nomper," +
                        "d.coddis, " +
                        "d.nomdis from t_empleado e " +
                        "inner join t_perfil p on e.codper=p.codper " +
                        "inner join t_distrito d on e.coddis=d.coddis " +
                        "where e.estemp=1",
                null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //asignamos los valores
                Empleado objempleado=new Empleado();
                Distrito objdistrito= new Distrito();
                Perfil objperfil=new Perfil();
                objempleado.setCodigo(cursor.getInt(0));
                objempleado.setNombre(cursor.getString(1));
                objempleado.setApellidopaterno(cursor.getString(2));
                objempleado.setApellidomaterno(cursor.getString(3));
                objempleado.setDni(cursor.getString(4));
                objempleado.setDireccion(cursor.getString(5));
                objempleado.setTelefono(cursor.getString(6));
                objempleado.setCelular(cursor.getString(7));
                objempleado.setCorreo(cursor.getString(8));
                objempleado.setSexo(cursor.getString(9));
                objempleado.setUsuario(cursor.getString(10));
                objempleado.setClave(cursor.getString(11));
                if(cursor.getInt(12)==1){
                    objempleado.setEstado(true);
                }else{
                    objempleado.setEstado(false);
                }

                objperfil.setCodigo(cursor.getInt(13));
                objperfil.setNombre(cursor.getString(14));
                objempleado.setPerfil(objperfil);

                objdistrito.setCodigo(cursor.getInt(15));
                objdistrito.setNombre(cursor.getString(16));
                objempleado.setDistrito(objdistrito);



                //enviamos los datos al arreglo
                registroempleado.add(objempleado);

            }
        }else{
            registroempleado=null;
        }
        return registroempleado;
    }
}