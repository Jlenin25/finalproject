package pe.com.finalproject.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.clases.Categoria;

public class CategoriaAdaptador extends BaseAdapter {
    private ArrayList<Categoria> listacategoria;
    private LayoutInflater layoutInflater;

    public CategoriaAdaptador(Context context, ArrayList<Categoria>acategoria) {
        this.listacategoria=acategoria;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listacategoria.size();
    }

    @Override
    public Object getItem(int position) {
        return listacategoria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.elementos_lista_categoria,parent,false);
            Categoria objcategoria=(Categoria)getItem(position);
            //creamos los controles
            TextView lstCodCat=convertView.findViewById(R.id.lblCodCat);
            TextView lstNomCat=convertView.findViewById(R.id.lblNomCat);
            TextView lstEstCat=convertView.findViewById(R.id.lblEstCat);
            //agregamos valores a la lista
            lstCodCat.setText(""+objcategoria.getCodigo());
            lstNomCat.setText(""+objcategoria.getNombre());
            if(objcategoria.isEstado()){
                lstEstCat.setText("Habilitado");
            }else{
                lstEstCat.setText("Deshabilitado");
            }
        }
        return convertView;
    }
}