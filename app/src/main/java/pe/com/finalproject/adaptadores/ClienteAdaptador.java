package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Cliente;

public class ClienteAdaptador extends BaseAdapter {
    private ArrayList<Cliente> listacliente;
    private LayoutInflater layoutInflater;

    public ClienteAdaptador(Context context, ArrayList<Cliente> acliente) {
        this.listacliente = acliente;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listacliente.size();
    }

    @Override
    public Object getItem(int position) {
        return listacliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_cliente,parent,false);
            Cliente objcliente =(Cliente) getItem(position);
            //creamos los controles
            TextView lstCodCli =convertView.findViewById(R.id.lblCodCli);
            TextView lstNomCli =convertView.findViewById(R.id.lblNomCli);
            TextView lblApepCli =convertView.findViewById(R.id.lblApepCli);
            TextView lblApemCli =convertView.findViewById(R.id.lblApemCli);
            TextView lblDniCli =convertView.findViewById(R.id.lblDniCli);
            TextView lblDirCli =convertView.findViewById(R.id.lblDirCli);
            TextView lblDisCli =convertView.findViewById(R.id.lblDisCli);
            TextView lstEstCli =convertView.findViewById(R.id.lblEstCli);
            //agregamos valores a la lista
            lstCodCli.setText(""+ objcliente.getCodigo());
            lstNomCli.setText(""+ objcliente.getNombre());
            lblApepCli.setText(""+ objcliente.getApellidopaterno());
            lblApemCli.setText(""+ objcliente.getApellidomaterno());
            lblDniCli.setText(""+ objcliente.getDni());
            lblDirCli.setText(""+ objcliente.getDireccion());
            lblDisCli.setText(""+ objcliente.getDistrito().getNombre());
            if(objcliente.isEstado()){
                lstEstCli.setText("Habilitado");
            }else{
                lstEstCli.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}
