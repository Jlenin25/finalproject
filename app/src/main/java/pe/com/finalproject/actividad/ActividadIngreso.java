package pe.com.finalproject.actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Empleado;
import pe.com.finalproject.implementaciones.ImpEmpleado;
import pe.com.finalproject.interfaces.IEmpleado;

public class ActividadIngreso extends AppCompatActivity {

    //1.- declaramos los controles
    //Caja de texto -->EditText
    private EditText txtUsu, txtCla;
    //botones --> Button
    private Button btnIngresar, btnSalir;
    //Mensaje emergente --> Toast
    private Toast mensaje;
    //Navegar entre Actividades
    private Intent formulario;
    //creamos un objeto de la clase utilidad
    private Utilidad objutilidad=new Utilidad();
    //creamos una variable para el contexto
    private Context context;
    //creamos un objeto de la clase empleado
    private Empleado objempleado=new Empleado();
    //declaramos variables
    private String usu="",cla="";
    private boolean res=false;
    //creamos la implementacion
    private IEmpleado empleados=new ImpEmpleado();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_ingreso);
        //2.- creamos los controles
        txtUsu=findViewById(R.id.txtUsu);
        txtCla=findViewById(R.id.txtCla);
        btnIngresar=findViewById(R.id.btnIngresar);
        btnSalir=findViewById(R.id.btnSalir);
        context=this;

        //3.- Se llaman a los eventos
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar los controles
                if(txtUsu.getText().toString().equals("")){
                    mensaje=Toast.makeText(getApplicationContext(),
                            "Ingrese el usuario",Toast.LENGTH_LONG);
                    mensaje.show();
                    txtUsu.requestFocus();
                }else if(txtCla.getText().toString().equals("")){
                    mensaje=Toast.makeText(getApplicationContext(),
                            "Ingrese la clave",Toast.LENGTH_LONG);
                    mensaje.show();
                    txtCla.requestFocus();
                }else{
                    usu=txtUsu.getText().toString();
                    cla=txtCla.getText().toString();
                    objempleado.setUsuario(usu);
                    objempleado.setClave(cla);
                    res=empleados.ValidarUsuario(objempleado,getApplicationContext());
                    if(res){
                        objutilidad.MensajeToast(context,"Bienvenidos al Sistema");
                        formulario= new Intent(getApplicationContext(),ActividadMenu.class);
                        startActivity(formulario);
                        finish();
                    }else{
                        objutilidad.MensajeToast(context,"Usuario o Clave no valida");
                        objutilidad.Limpiar((ViewGroup) findViewById(R.id.frmIngreso));
                        txtUsu.requestFocus();
                    }
                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objutilidad.SalirSistema(context);
            }
        });
    }
}