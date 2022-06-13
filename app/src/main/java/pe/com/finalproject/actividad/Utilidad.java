package pe.com.finalproject.actividad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Utilidad {
    //atributos
    private AlertDialog.Builder dialogo;
    private Toast mensaje;

    //creamos un procedimiento para los mensajes del Toast
    public void MensajeToast(final Context context, String men){
        mensaje=Toast.makeText(context,men,Toast.LENGTH_LONG);
        mensaje.show();
    }

    //creamos un procedimiento que nos permita salir
    public void SalirSistema(Context context){
        dialogo=new AlertDialog.Builder(context);
        dialogo.setTitle("Saliendo del Sistema");
        dialogo.setMessage("¿Estas seguro que quieres salir del sistema?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)context).finish();
            }
        });
        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogo.show();
    }

    //procedimiento para limpiar todos los controles
    public void Limpiar(ViewGroup viewGroup){
        for(int i=0, count=viewGroup.getChildCount();i<count;i++){
            View view=viewGroup.getChildAt(i);
            //limpiando la caja de texto
            if(view instanceof EditText){
                ((EditText)view).setText("");
            }
            //limpiando el rdiobutton
            if(view instanceof RadioGroup){
                ((RadioButton)((RadioGroup)view).getChildAt(0)).setChecked(false);
            }
            //limpiando el spinner
            if(view instanceof Spinner){
                ((Spinner)view).setSelection(0);
            }
            //limpiando la caja de texto
            if(view instanceof CheckBox){
                ((CheckBox)view).setChecked(false);
            }
            if(view instanceof ViewGroup &&(((ViewGroup)view).getChildCount() >0)){
                Limpiar((ViewGroup) view);
            }

        }
    }
}
