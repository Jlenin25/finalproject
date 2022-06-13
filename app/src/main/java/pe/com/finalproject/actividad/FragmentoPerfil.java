package pe.com.finalproject.actividad;

import android.app.Fragment;
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
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.adaptadores.PerfilAdaptador;
import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.implementaciones.ImpPerfil;
import pe.com.finalproject.interfaces.IPerfil;

public class FragmentoPerfil extends Fragment {
    //declarando los controles
    private EditText txtNomper;
    private CheckBox chkEstper;
    private TextView lblCodper;
    private Button btnRegistrarper, btnActualizarper,btnEliminarper;
    private ListView lstPer;

    //adaptador de tipo Distrito
    private PerfilAdaptador perfilAdaptador;

    //variables
    private int cod=0, fila=-1;
    private String nom="";
    private boolean est=false, res=false;

    //implementar la interfaz
    private IPerfil perfiles=new ImpPerfil();

    //objeto de la clase Perfil
    private Perfil objperfil= new Perfil();

    //ArrayList de tipo Perfil
    private ArrayList<Perfil> registroperfil;

    //creamos un objeto de la clase utilidad
    Utilidad objutilidad= new Utilidad();

    //creamos una variable para trabajar los fragmentos
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View raiz=inflater.inflate(R.layout.fragmento_perfil, container, false);

        //creamos los controles
        txtNomper=raiz.findViewById(R.id.txtNomPer);
        chkEstper=raiz.findViewById(R.id.chkEstper);
        lblCodper=raiz.findViewById(R.id.lblCodper);
        btnRegistrarper=raiz.findViewById(R.id.btnRegistrarper);
        btnActualizarper=raiz.findViewById(R.id.btnActualizarper);
        btnEliminarper=raiz.findViewById(R.id.btnEliminarper);
        lstPer=raiz.findViewById(R.id.lstPer);

        //reamos el Arraylist Perfil
        registroperfil=new ArrayList<>();
        //asignamos el valor de mostrar
        registroperfil=perfiles.MostrarPerfil(raiz.getContext());
        if(registroperfil!=null){
            //creamos el adaptador
            perfilAdaptador=new PerfilAdaptador(raiz.getContext(),registroperfil);
            //asginamos el adaptador
            lstPer.setAdapter(perfilAdaptador);
        }


        //eventos
        btnRegistrarper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNomper.getText().toString().equals("")){
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNomper.requestFocus();
                }else{
                    //capturando valores
                    nom=txtNomper.getText().toString();
                    if(chkEstper.isChecked()){
                        est=true;
                    }else{
                        est=false;
                    }
                    //enviando los valores a la clase
                    objperfil.setNombre(nom);
                    objperfil.setEstado(est);
                    res=perfiles.RegistrarPerfil(objperfil, raiz.getContext());
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el perfil");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmPerfil));
                        FragmentoPerfil fperfil=new FragmentoPerfil();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fperfil,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el perfil");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmPerfil));
                    }
                }

            }
        });

        lstPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila=position;
                //asignamos los valores a cada control
                lblCodper.setText(""+registroperfil.get(fila).getCodigo());
                txtNomper.setText(""+registroperfil.get(fila).getNombre());
                if(registroperfil.get(fila).isEstado()==true){
                    chkEstper.setChecked(true);
                }else {
                    chkEstper.setChecked(false);
                }
            }
        });

        btnActualizarper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fila>=0){
                    cod=Integer.parseInt(lblCodper.getText().toString());
                    nom=txtNomper.getText().toString();
                    if(chkEstper.isChecked()==true){
                        est=true;
                    }else{
                        est=false;
                    }
                    objperfil.setCodigo(cod);
                    objperfil.setNombre(nom);
                    objperfil.setEstado(est);
                    res=perfiles.ActualizarPerfil(objperfil);
                    if(res==true){
                        objutilidad.Limpiar((ViewGroup)raiz.findViewById(R.id.frmPerfil));
                        objutilidad.MensajeToast(raiz.getContext(), "Se actualizo el perfil");
                        perfilAdaptador.notifyDataSetChanged();
                        lstPer.setAdapter(perfilAdaptador);
                        FragmentoPerfil fperfil=new FragmentoPerfil();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fperfil,null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila=-1;
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el perfil");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmPerfil));
                    }

                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstPer.requestFocus();
                }

            }
        });

        btnEliminarper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fila>=0){
                    cod=Integer.parseInt(lblCodper.getText().toString());
                    objperfil.setCodigo(cod);
                    res=perfiles.EliminarPerfil(objperfil);
                    if(res==true){
                        objutilidad.Limpiar((ViewGroup)raiz.findViewById(R.id.frmPerfil));
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el perfil");
                        perfilAdaptador.notifyDataSetChanged();
                        lstPer.setAdapter(perfilAdaptador);
                        FragmentoPerfil fperfil=new FragmentoPerfil();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fperfil,null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila=-1;
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el perfil");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmPerfil));
                    }

                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstPer.requestFocus();
                }

            }
        });

        return raiz;
    }
}
