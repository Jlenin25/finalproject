package pe.com.finalproject.implementaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pe.com.finalproject.bd.Conexion;
import pe.com.finalproject.clases.Cliente;
import pe.com.finalproject.clases.Distrito;

import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.interfaces.ICliente;

public class ImpCliente implements ICliente {
    Cliente objcliente =new Cliente();
    private ArrayList<Cliente> registrocliente =null;
    private SQLiteDatabase database;
    private Cursor cursor;
    private ContentValues valores;
    private SQLiteOpenHelper xcon;
    private int version=1;
    private String nombre="bdregistro2022";
    private Conexion objconexion;

    public ImpCliente() {
        registrocliente =new ArrayList<>();
    }

    @Override
    public boolean RegistrarCliente(Cliente c, Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomcli",c.getNombre());
            valores.put("apepcli",c.getApellidopaterno());
            valores.put("apemcli",c.getApellidomaterno());
            valores.put("dnicli",c.getDni());
            valores.put("dircli",c.getDireccion());
            valores.put("telcli",c.getTelefono());
            valores.put("celcli",c.getCelular());
            valores.put("corcli",c.getCorreo());
            valores.put("dircli",c.getDireccion());
            valores.put("sexcli",c.getSexo());
            valores.put("estcli",c.isEstado());
            valores.put("coddis",c.getDistrito().getCodigo());
            //ejecutamos la sentencia
            long res=database.insert("t_cliente",null,valores);

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
    public boolean ActualizarCliente(Cliente c) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("codcli",c.getCodigo());
            valores.put("nomcli",c.getNombre());
            valores.put("apepcli",c.getApellidopaterno());
            valores.put("apemcli",c.getApellidomaterno());
            valores.put("dnicli",c.getDni());
            valores.put("telcli",c.getTelefono());
            valores.put("celcli",c.getCelular());
            valores.put("corcli",c.getCorreo());
            valores.put("dircli",c.getDireccion());
            valores.put("sexcli",c.getSexo());
            valores.put("estcli",c.isEstado());

            Log.e("distrito",""+c.getDistrito().getCodigo());

            valores.put("coddis",c.getDistrito().getCodigo());
            //ejecutamos la sentencia
            int res=(int)database.update("t_cliente",valores,"codcli="+c.getCodigo(),null);
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
    public boolean EliminarCliente(Cliente c) {
        try {
            valores=new ContentValues();
            valores.put("estcli",0);
            int res=(int)database.update("t_cliente",valores,"codcli="+c.getCodigo(),null);
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
    public ArrayList<Cliente> MostrarCliente(Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        //realizamos la consulta a la tabla
        cursor=database.rawQuery(
                "select " +
                        "c.codcli, " +
                        "c.nomcli, " +
                        "c.apepcli, " +
                        "c.apemcli, " +
                        "c.dnicli, " +
                        "c.dircli, " +
                        "c.telcli, " +
                        "c.celcli, " +
                        "c.corcli, " +
                        "c.sexcli, " +
                        "c.estcli, " +
                        "d.coddis, " +
                        "d.nomdis from t_cliente c " +
                        "inner join t_distrito d on c.coddis=d.coddis " +
                        "where c.estcli=1",


                null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                //asignamos los valores
                Cliente objcliente =new Cliente();
                Distrito objdistrito= new Distrito();
                Perfil objperfil=new Perfil();
                objcliente.setCodigo(cursor.getInt(0));
                objcliente.setNombre(cursor.getString(1));
                objcliente.setApellidopaterno(cursor.getString(2));
                objcliente.setApellidomaterno(cursor.getString(3));
                objcliente.setDni(cursor.getString(4));
                objcliente.setDireccion(cursor.getString(5));
                objcliente.setTelefono(cursor.getString(6));
                objcliente.setCelular(cursor.getString(7));
                objcliente.setCorreo(cursor.getString(8));
                objcliente.setSexo(cursor.getString(9));
                if(cursor.getInt(10)==1){
                    objcliente.setEstado(true);
                }else{
                    objcliente.setEstado(false);
                }

                objdistrito.setCodigo(cursor.getInt(11));
                objdistrito.setNombre(cursor.getString(12));
                objcliente.setDistrito(objdistrito);



                //enviamos los datos al arreglo
                registrocliente.add(objcliente);

            }
        }else{
            registrocliente =null;
        }
        return registrocliente;
    }
}
