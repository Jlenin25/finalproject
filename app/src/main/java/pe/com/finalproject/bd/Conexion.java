package pe.com.finalproject.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexion extends SQLiteOpenHelper{
    //creamos una variable para la conexion
    private SQLiteDatabase xcon;
    //creamos variables para las tablas
    private String t_perfil, t_distrito,t_empleado, t_cliente, t_categoria, t_producto;

    public Conexion(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        //creamos la conexion
        //getWritableDatabase() --> sirve para cerar y escribir en la BD
        xcon=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creamos la tablas
        t_perfil="CREATE TABLE t_perfil(" +
                "codper INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomper text NOT NULL," +
                "estper integer NOT NULL)";
        t_distrito="CREATE TABLE t_distrito(" +
                "coddis INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomdis text NOT NULL," +
                "estdis integer NOT NULL)";

        t_cliente="CREATE TABLE t_cliente(" +
                "codcli INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomcli text NOT NULL," +
                "apepcli text NOT NULL," +
                "apemcli text NOT NULL," +
                "dnicli text NOT NULL," +
                "telcli text NOT NULL," +
                "celcli text NOT NULL," +
                "corcli text NOT NULL," +
                "dircli text NOT NULL,"+
                "sexcli text NOT NULL," +
                "estcli INTEGER NOT NULL," +
                "coddis INTEGER NOT NULL," +
                "foreign key (coddis) references t_distrito(coddis))";

        t_empleado="CREATE TABLE t_empleado(" +
                "codemp INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomemp text NOT NULL," +
                "apepemp text NOT NULL," +
                "apememp text NOT NULL," +
                "dniemp text NOT NULL," +
                "telemp text NOT NULL," +
                "diremp text NOT NULL," +
                "celemp text NOT NULL," +
                "coremp text NOT NULL," +
                "sexemp text NOT NULL," +
                "usuemp text NOT NULL," +
                "claemp text NOT NULL," +
                "estemp INTEGER NOT NULL," +
                "codper INTEGER NOT NULL," +
                "coddis INTEGER NOT NULL," +
                "foreign key (codper) references t_perfil(codper)," +
                "foreign key (coddis) references t_distrito(coddis)"+
                ")";
        t_categoria="CREATE TABLE t_categoria(" +
                "codcat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomcat text NOT NULL," +
                "estcat integer NOT NULL)";

        t_producto="CREATE TABLE t_producto(" +
                "codpro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nompro text NOT NULL," +
                "precpro text NOT NULL," +
                "prevpro text NOT NULL," +
                "canpro text NOT NULL," +
                "estpro INTEGER NOT NULL," +
                "codcat INTEGER NOT NULL," +
                "foreign key (codcat) references t_categoria(codcat))";


        //ejecutamos las tablas
        db.execSQL(t_perfil);
        db.execSQL(t_distrito);
        db.execSQL(t_cliente);
        db.execSQL(t_empleado);
        db.execSQL(t_categoria);
        db.execSQL(t_producto);

        db.execSQL("insert into t_distrito(nomdis,estdis) values('Cercado de Lima',1)");
        db.execSQL("insert into t_distrito(nomdis,estdis) values('Breña',1)");
        db.execSQL("insert into t_distrito(nomdis,estdis) values('Los Olivos',1)");
        db.execSQL("insert into t_perfil(nomper, estper) values('Administrador del Sistema',1)");
        db.execSQL("insert into t_perfil(nomper, estper) values('Vendedor',1)");
        db.execSQL("insert into t_perfil(nomper, estper) values('Cajero',1)");
        db.execSQL(
                "insert into t_empleado(nomemp, apepemp," +
                        "apememp,dniemp, diremp,telemp,celemp," +
                        "coremp,sexemp, usuemp, claemp, estemp," +
                        "coddis,codper) values('Jose Daniel','Osorio', " +
                        "'Morales','78963214','Calle Union 471'," +
                        "'2462119','999666333','josorio@gmail.com'," +
                        "'masculino','admin','123',1,2,1)");
        db.execSQL(
                "insert into t_cliente(nomcli, apepcli," +
                        "apemcli,dnicli,telcli,celcli," +
                        "corcli,dircli,sexcli, estcli," +
                        "coddis) values('Daniel Alberto','Sanchez', " +
                        "'Castro','87654321'," +
                        "'5463217','987612345','dansanchez@gmail.com','Calle San Jose 44'," +
                        "'masculino',1,1)");
        db.execSQL("insert into t_categoria(nomcat,estcat) values('Computo',1)");
        db.execSQL("insert into t_categoria(nomcat,estcat) values('Limpieza',1)");
        db.execSQL(
                "insert into t_producto(nompro, precpro," +
                        "prevpro,canpro,estpro,codcat)" +
                        "values('Laptop Intel','188', " +
                        "'229','15',1,1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists t_perfil");
        db.execSQL("drop table if exists t_distrito");
        db.execSQL("drop table if exists t_empleado");
        db.execSQL("drop table if exists t_cliente");
        db.execSQL("drop table if exists t_categoria");
        db.execSQL("drop table if exists t_producto");

    }
}
