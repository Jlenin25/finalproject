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

public class PerfilAdaptadorCombo extends BaseAdapter {
    private ArrayList<Perfil> listaperfil;
    private LayoutInflater layoutInflater;

    public PerfilAdaptadorCombo(Context context, ArrayList<Perfil>aperfil) {
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
            convertView=layoutInflater.inflate(R.layout.elemento_combo_distrito,parent,false);
            Perfil objperfil=(Perfil)getItem(position);
            //creamos los controles
            TextView lstNomPer=convertView.findViewById(R.id.lblCodCli);
            lstNomPer.setText(""+objperfil.getNombre());
        }
        return convertView;
    }
}
