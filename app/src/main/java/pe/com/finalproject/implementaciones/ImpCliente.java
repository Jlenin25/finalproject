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
    public boolean ValidarUsuario(Cliente e, Context context) {
        objconexion = new Conexion(context, nombre, null, version);
        database = objconexion.getWritableDatabase();
        String[] argumento=new String[]{e.getUsuario(),e.getClave()};
        cursor = database.rawQuery("select * from t_cliente where usucli=? and clacli=?", argumento);
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
    public boolean RegistrarCliente(Cliente e, Context context) {
        //creamos el objeto de la conexion
        objconexion=new Conexion(context,nombre,null,version);
        //indicamos que la BD sera de escritura
        database=objconexion.getWritableDatabase();
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("nomcli",e.getNombre());
            valores.put("apepcli",e.getApellidopaterno());
            valores.put("apemcli",e.getApellidomaterno());
            valores.put("dnicli",e.getDni());
            valores.put("dircli",e.getDireccion());
            valores.put("telcli",e.getTelefono());
            valores.put("celcli",e.getCelular());
            valores.put("corcli",e.getCorreo());
            valores.put("sexcli",e.getSexo());
            valores.put("usucli",e.getUsuario());
            valores.put("clacli",e.getClave());
            valores.put("estcli",e.isEstado());
            valores.put("coddis",e.getDistrito().getCodigo());
            valores.put("codper",e.getPerfil().getCodigo());
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
    public boolean ActualizarCliente(Cliente e) {
        try {
            //creamos los valores
            valores=new ContentValues();
            //asignamos los valores
            valores.put("codcli",e.getCodigo());
            valores.put("nomcli",e.getNombre());
            valores.put("apepcli",e.getApellidopaterno());
            valores.put("apemcli",e.getApellidomaterno());
            valores.put("dnicli",e.getDni());
            valores.put("dircli",e.getDireccion());
            valores.put("telcli",e.getTelefono());
            valores.put("celcli",e.getCelular());
            valores.put("corcli",e.getCorreo());
            valores.put("sexcli",e.getSexo());
            valores.put("usucli",e.getUsuario());
            valores.put("clacli",e.getClave());
            valores.put("estcli",e.isEstado());

            Log.e("distrito",""+e.getDistrito().getCodigo());
            Log.e("perfil",""+e.getPerfil().getCodigo());

            valores.put("coddis",e.getDistrito().getCodigo());
            valores.put("codper",e.getPerfil().getCodigo());
            //ejecutamos la sentencia
            int res=(int)database.update("t_cliente",valores,"codcli="+e.getCodigo(),null);
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
    public boolean EliminarCliente(Cliente e) {
        try {
            valores=new ContentValues();
            valores.put("estcli",0);
            int res=(int)database.update("t_cliente",valores,"codcli="+e.getCodigo(),null);
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
                        "e.codcli, " +
                        "e.nomcli, " +
                        "e.apepcli, " +
                        "e.apemcli, " +
                        "e.dnicli, " +
                        "e.dircli, " +
                        "e.telcli, " +
                        "e.celcli, " +
                        "e.corcli, " +
                        "e.sexcli, " +
                        "e.usucli, " +
                        "e.clacli," +
                        "e.estcli, " +
                        "p.codper, " +
                        "p.nomper," +
                        "d.coddis, " +
                        "d.nomdis from t_cliente e " +
                        "inner join t_perfil p on e.codper=p.codper " +
                        "inner join t_distrito d on e.coddis=d.coddis " +
                        "where e.estcli=1",


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
                objcliente.setUsuario(cursor.getString(10));
                objcliente.setClave(cursor.getString(11));
                if(cursor.getInt(12)==1){
                    objcliente.setEstado(true);
                }else{
                    objcliente.setEstado(false);
                }

                objperfil.setCodigo(cursor.getInt(13));
                objperfil.setNombre(cursor.getString(14));
                objcliente.setPerfil(objperfil);

                objdistrito.setCodigo(cursor.getInt(15));
                objdistrito.setNombre(cursor.getString(16));
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
