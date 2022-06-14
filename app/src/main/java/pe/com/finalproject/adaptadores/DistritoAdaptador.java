package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Distrito;

public class DistritoAdaptador extends BaseAdapter {
    private ArrayList<Distrito> listadistrito;
    private LayoutInflater layoutInflater;

    public DistritoAdaptador(Context context, ArrayList<Distrito>adistrito) {
        this.listadistrito=adistrito;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listadistrito.size();
    }

    @Override
    public Object getItem(int position) {
        return listadistrito.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_distrito,parent,false);
            Distrito objdistrito=(Distrito)getItem(position);
            //creamos los controles
            TextView lstCodDis=convertView.findViewById(R.id.lblCodDis);
            TextView lstNomDis=convertView.findViewById(R.id.lblNomDis);
            TextView lstEstDis=convertView.findViewById(R.id.lblEstDis);
            //agregamos valores a la lista
            lstCodDis.setText(""+objdistrito.getCodigo());
            lstNomDis.setText(""+objdistrito.getNombre());
            if(objdistrito.isEstado()){
                lstEstDis.setText("Habilitado");
            }else{
                lstEstDis.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}