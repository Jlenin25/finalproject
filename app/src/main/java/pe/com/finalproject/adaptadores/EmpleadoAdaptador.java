package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Empleado;

public class EmpleadoAdaptador extends BaseAdapter {
    private ArrayList<Empleado> listaempleado;
    private LayoutInflater layoutInflater;

    public EmpleadoAdaptador(Context context, ArrayList<Empleado>aempleado) {
        this.listaempleado=aempleado;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaempleado.size();
    }

    @Override
    public Object getItem(int position) {
        return listaempleado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_empleado,parent,false);
            Empleado objempleado=(Empleado) getItem(position);
            //creamos los controles
            TextView lstCodEmp=convertView.findViewById(R.id.lblCodEmp);
            TextView lstNomEmp=convertView.findViewById(R.id.lblNomEmp);
            TextView lblApepEmp=convertView.findViewById(R.id.lblApepEmp);
            TextView lblApemEmp=convertView.findViewById(R.id.lblApemEmp);
            TextView lblDniEmp=convertView.findViewById(R.id.lblDniEmp);
            TextView lblDirEmp=convertView.findViewById(R.id.lblDirEmp);
            TextView lblDisEmp=convertView.findViewById(R.id.lblDisEmp);
            TextView lstEstEmp=convertView.findViewById(R.id.lblEstEmp);
            //agregamos valores a la lista
            lstCodEmp.setText(""+objempleado.getCodigo());
            lstNomEmp.setText(""+objempleado.getNombre());
            lblApepEmp.setText(""+objempleado.getApellidopaterno());
            lblApemEmp.setText(""+objempleado.getApellidomaterno());
            lblDniEmp.setText(""+objempleado.getDni());
            lblDirEmp.setText(""+objempleado.getDireccion());
            lblDisEmp.setText(""+objempleado.getDistrito().getNombre());
            if(objempleado.isEstado()){
                lstEstEmp.setText("Habilitado");
            }else{
                lstEstEmp.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}
