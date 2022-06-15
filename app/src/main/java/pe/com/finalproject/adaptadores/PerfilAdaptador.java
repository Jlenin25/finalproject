package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Perfil;

public class PerfilAdaptador extends BaseAdapter {
    private ArrayList<Perfil> listaperfil;
    private LayoutInflater layoutInflater;

    public PerfilAdaptador(Context context, ArrayList<Perfil>aperfil) {
        this.listaperfil=aperfil;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaperfil.size();
    }

    @Override
    public Object getItem(int position) {
        return listaperfil.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_perfil,parent,false);
            Perfil objperfil=(Perfil)getItem(position);
            //creamos los controles
            TextView lstCodPer=convertView.findViewById(R.id.lblCodPer);
            TextView lstNomPer=convertView.findViewById(R.id.lblNomPer);
            TextView lstEstPer=convertView.findViewById(R.id.lblEstPer);
            //agregamos valores a la lista
            lstCodPer.setText(""+objperfil.getCodigo());
            lstNomPer.setText(""+objperfil.getNombre());
            if(objperfil.isEstado()){
                lstEstPer.setText("Habilitado");
            }else{
                lstEstPer.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}
