package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Producto;

public class ProductoAdaptador extends BaseAdapter {
    private ArrayList<Producto> listaproducto;
    private LayoutInflater layoutInflater;

    public ProductoAdaptador(Context context, ArrayList<Producto> aproducto) {
        this.listaproducto = aproducto;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaproducto.size();
    }

    @Override
    public Object getItem(int position) {
        return listaproducto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_producto,parent,false);
            Producto objproducto =(Producto) getItem(position);
            //creamos los controles
            TextView lstCodCli =convertView.findViewById(R.id.lblCodPro);
            TextView lstNomCli =convertView.findViewById(R.id.lblNomPro);
            TextView lblPrecPro =convertView.findViewById(R.id.lblPrecPro);
            TextView lblPrevPro =convertView.findViewById(R.id.lblPrevPro);
            TextView lblCanPro =convertView.findViewById(R.id.lblCanPro);
            TextView lstEstPro =convertView.findViewById(R.id.lblEstPro);
            TextView lblCatPro =convertView.findViewById(R.id.lblCatPro);
            //agregamos valores a la lista
            lstCodCli.setText(""+ objproducto.getCodigo());
            lstNomCli.setText(""+ objproducto.getNombre());
            lblPrecPro.setText(""+ objproducto.getPreciocompra());
            lblPrevPro.setText(""+ objproducto.getPrecioventa());
            lblCanPro.setText(""+ objproducto.getCantidad());
            lblCatPro.setText(""+ objproducto.getCategoria().getNombre());
            if(objproducto.isEstado()){
                lstEstPro.setText("Habilitado");
            }else{
                lstEstPro.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}
