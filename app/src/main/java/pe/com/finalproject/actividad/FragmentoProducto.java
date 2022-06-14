package pe.com.finalproject.actividad;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.Fragment;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.adaptadores.ProductoAdaptador;
import pe.com.finalproject.adaptadores.CategoriaAdaptadorCombo;
import pe.com.finalproject.clases.Producto;
import pe.com.finalproject.clases.Categoria;
import pe.com.finalproject.implementaciones.ImpProducto;
import pe.com.finalproject.implementaciones.ImpCategoria;
import pe.com.finalproject.interfaces.IProducto;
import pe.com.finalproject.interfaces.ICategoria;

public class FragmentoProducto extends Fragment {
    //declarando los controles
    private EditText txtNompro, txtPrecpro,txtPrevpro, txtCanpro;
    private Spinner cboCategoria;
    private CheckBox chkEstPro;
    private TextView lblCodpro,lblCodcat;
    private Button btnRegistrarpro, btnActualizarpro,btnEliminarpro;
    private ListView lstPro;

    //adaptador de tipo Distrito
    private ProductoAdaptador productoadaptador;
    private CategoriaAdaptadorCombo  categoriaadaptadorcombo;

    //variables
    private int cod=0, fila=-1, indice=-1, codc=0, pos=-1;
    private String nom="", prec="",prev="",can="",cat="";
    private boolean est=false, res=false;

    //implementar la interfaz
    private IProducto productos= new ImpProducto();
    private ICategoria categorias=new ImpCategoria();

    //objeto de la clase Distrito

    //ArrayList de tipo distrito
    private ArrayList<Producto> registroproducto;
    private ArrayList<Categoria> registrocategoria;


    FragmentTransaction ft;


    //creamos un objeto de la clase utilidad
    Utilidad objutilidad= new Utilidad();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View raiz=inflater.inflate(R.layout.fragmento_producto, container, false);

        //creamos los controles
        txtNompro=raiz.findViewById(R.id.txtNompro);
        txtPrecpro=raiz.findViewById(R.id.txtPrecpro);
        txtPrevpro=raiz.findViewById(R.id.txtPrevpro);
        txtCanpro=raiz.findViewById(R.id.txtCanpro);
        cboCategoria=raiz.findViewById(R.id.cboCategoria);
        chkEstPro=raiz.findViewById(R.id.chkEstpro);
        lblCodpro=raiz.findViewById(R.id.lblCodpro);
        lblCodcat=raiz.findViewById(R.id.lblCodcat);
        btnRegistrarpro=raiz.findViewById(R.id.btnRegistrarpro);
        btnActualizarpro=raiz.findViewById(R.id.btnActualizarpro);
        btnEliminarpro=raiz.findViewById(R.id.btnEliminarpro);
        lstPro=raiz.findViewById(R.id.lstPro);

        registrocategoria=new ArrayList<>();
        registroproducto=new ArrayList<>();

        registrocategoria=categorias.MostrarCategoria(raiz.getContext());
        if(registrocategoria!=null){
            categoriaadaptadorcombo=new CategoriaAdaptadorCombo(raiz.getContext(),registrocategoria);
            cboCategoria.setAdapter(categoriaadaptadorcombo);
        }

        registroproducto=productos.MostrarProducto(raiz.getContext());
        if(registroproducto!=null) {
            productoadaptador=new ProductoAdaptador(raiz.getContext(),registroproducto);
            lstPro.setAdapter(productoadaptador);
        }


        //eventos
        btnRegistrarpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNompro.getText().toString().equals("")){
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNompro.requestFocus();
                }else{
                    Producto objproducto=new Producto();
                    Categoria objcategoria= new Categoria();
                    //capturando valores
                    nom=txtNompro.getText().toString();
                    prec=txtPrecpro.getText().toString();
                    prev=txtPrevpro.getText().toString();
                    can=txtCanpro.getText().toString();

                    pos=cboCategoria.getSelectedItemPosition();
                    cat=registrocategoria.get(pos).getNombre();
                    codc= Math.toIntExact(registrocategoria.get(pos).getCodigo());

                    if(chkEstPro.isChecked()){
                        est=true;
                    }else{
                        est=false;
                    }
                    //enviando los valores a la clase
                    objproducto.setCodigo(cod);
                    objproducto.setNombre(nom);
                    objproducto.setPreciocompra(prec);
                    objproducto.setPrecioventa(prev);
                    objproducto.setCantidad(can);

                    objcategoria.setNombre(cat);
                    objcategoria.setCodigo(codc);
                    objproducto.setCategoria(objcategoria);

                    objproducto.setEstado(est);
                    res=productos.RegistrarProducto(objproducto,raiz.getContext());
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el producto");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmProducto));
                        FragmentoProducto fproducto=new FragmentoProducto();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fproducto,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el producto");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmProducto));
                    }
                }

            }
        });

        lstPro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila=position;
                //asignamos los valores a cada control
                lblCodpro.setText(""+registroproducto.get(fila).getCodigo());
                txtNompro.setText(""+registroproducto.get(fila).getNombre());
                txtPrecpro.setText(""+registroproducto.get(fila).getPreciocompra());
                txtPrevpro.setText(""+registroproducto.get(fila).getPrecioventa());
                txtCanpro.setText(""+registroproducto.get(fila).getCantidad());

                for(int i=0;i<registrocategoria.size();i++){
                    if(registrocategoria.get(i).getNombre().equals(registroproducto.get(fila).getCategoria().getNombre())){
                        indice=i;
                    }
                }
                cboCategoria.setSelection(indice);


                if(registroproducto.get(fila).isEstado()==true){
                    chkEstPro.setChecked(true);
                }else {
                    chkEstPro.setChecked(false);
                }
            }
        });

        btnActualizarpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto objproducto=new Producto();

                Categoria objcategoria= new Categoria();

                cod=Integer.parseInt(lblCodpro.getText().toString());
                nom=txtNompro.getText().toString();
                prec=txtPrecpro.getText().toString();
                prev=txtPrevpro.getText().toString();
                can=txtCanpro.getText().toString();

                pos=cboCategoria.getSelectedItemPosition();
                cat=registrocategoria.get(pos).getNombre();
                codc= Math.toIntExact(registrocategoria.get(pos).getCodigo());

                if(chkEstPro.isChecked()){
                    est=true;
                }else{
                    est=false;
                }
                //enviando los valores a la clase
                objproducto.setCodigo(cod);
                objproducto.setNombre(nom);
                objproducto.setPreciocompra(prec);
                objproducto.setPrecioventa(prev);
                objproducto.setCantidad(can);

                objcategoria.setCodigo(codc);
                objcategoria.setNombre(cat);
                objproducto.setCategoria(objcategoria);

                objproducto.setEstado(est);

                res=productos.ActualizarProducto(objproducto);
                if(res){
                    objutilidad.MensajeToast(raiz.getContext(), "Se registro el producto");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    FragmentoProducto fproducto=new FragmentoProducto();
                    ft= getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,fproducto,null);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el producto");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                }
            }
        });

        btnEliminarpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fila>=0){
                    Producto objproducto=new Producto();
                    cod=Integer.parseInt(lblCodpro.getText().toString());
                    objproducto.setCodigo(cod);
                    res=productos.EliminarProducto(objproducto);
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el producto");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                        FragmentoProducto fproducto=new FragmentoProducto();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fproducto,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el producto");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    }

                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstPro.requestFocus();
                }

            }
        });

        return raiz;
    }
}
