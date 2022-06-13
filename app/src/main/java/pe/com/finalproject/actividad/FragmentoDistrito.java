package pe.com.finalproject.actividad;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.adaptadores.DistritoAdaptador;
import pe.com.finalproject.clases.Distrito;
import pe.com.finalproject.implementaciones.ImpDistrito;
import pe.com.finalproject.interfaces.IDistrito;

public class FragmentoDistrito extends Fragment {
    //declarando los controles
    private EditText txtNomdis;
    private CheckBox chkEstdis;
    private TextView lblCoddis;
    private Button btnRegistrardis, btnActualizardis, btnEliminardis;
    private ListView lstDis;

    //adaptador de tipo Distrito
    private DistritoAdaptador distritoAdaptador;

    //variables
    private int cod = 0, fila = -1;
    private String nom = "";
    private boolean est = false, res = false;

    //implementar la interfaz
    private IDistrito distritos = new ImpDistrito();

    //objeto de la clase Distrito
    private Distrito objdistrito = new Distrito();

    //ArrayList de tipo distrito
    private ArrayList<Distrito> registrodistrito;

    //creamos un objeto de la clase utilidad
    Utilidad objutilidad = new Utilidad();

    //creamos una variable para trabajar los fragmentos
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View raiz = inflater.inflate(R.layout.fragmento_distrito, container, false);

        //creamos los controles
        txtNomdis = raiz.findViewById(R.id.txtNomPer);
        chkEstdis = raiz.findViewById(R.id.chkEstper);
        lblCoddis = raiz.findViewById(R.id.lblCodper);
        btnRegistrardis = raiz.findViewById(R.id.btnRegistrarper);
        btnActualizardis = raiz.findViewById(R.id.btnActualizarper);
        btnEliminardis = raiz.findViewById(R.id.btnEliminarper);
        lstDis = raiz.findViewById(R.id.lstPer);

        //creamos el Arraylist Distrito
        registrodistrito = new ArrayList<>();
        //asignamos el valor de mostrar
        registrodistrito = distritos.MostrarDistrito(raiz.getContext());
        if (registrodistrito != null) {
            //creamos el adaptador
            distritoAdaptador = new DistritoAdaptador(raiz.getContext(), registrodistrito);
            //asginamos el adaptador
            lstDis.setAdapter(distritoAdaptador);
        }

        //eventos
        btnRegistrardis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNomdis.getText().toString().equals("")) {
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNomdis.requestFocus();
                } else {
                    //capturando valores
                    nom = txtNomdis.getText().toString();
                    if (chkEstdis.isChecked()) {
                        est = true;
                    } else {
                        est = false;
                    }
                    //enviando los valores a la clase
                    objdistrito.setNombre(nom);
                    objdistrito.setEstado(est);
                    res = distritos.RegistrarDistrito(objdistrito, raiz.getContext());
                    if (res) {
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el distrito");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmDistrito));
                        FragmentoDistrito fdistrito = new FragmentoDistrito();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fdistrito, null);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el distrito");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmDistrito));
                    }
                }

            }
        });

        lstDis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila = position;
                //asignamos los valores a cada control
                lblCoddis.setText("" + registrodistrito.get(fila).getCodigo());
                txtNomdis.setText("" + registrodistrito.get(fila).getNombre());
                if (registrodistrito.get(fila).isEstado() == true) {
                    chkEstdis.setChecked(true);
                } else {
                    chkEstdis.setChecked(false);
                }
            }
        });

        btnActualizardis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fila >= 0) {
                    cod = Integer.parseInt(lblCoddis.getText().toString());
                    nom = txtNomdis.getText().toString();
                    if (chkEstdis.isChecked() == true) {
                        est = true;
                    } else {
                        est = false;
                    }
                    objdistrito.setCodigo(cod);
                    objdistrito.setNombre(nom);
                    objdistrito.setEstado(est);
                    res = distritos.ActualizarDistrito(objdistrito);
                    if (res == true) {
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmDistrito));
                        objutilidad.MensajeToast(raiz.getContext(), "Se actualizo el Distrito");
                        distritoAdaptador.notifyDataSetChanged();
                        lstDis.setAdapter(distritoAdaptador);
                        FragmentoDistrito fdistrito = new FragmentoDistrito();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fdistrito, null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila = -1;
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el distrito");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmPerfil));
                    }

                } else {
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstDis.requestFocus();
                }

            }
        });

        btnEliminardis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fila >= 0) {
                    cod = Integer.parseInt(lblCoddis.getText().toString());
                    objdistrito.setCodigo(cod);
                    res = distritos.EliminarDistrito(objdistrito);
                    if (res == true) {
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmDistrito));
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el Distrito");
                        distritoAdaptador.notifyDataSetChanged();
                        lstDis.setAdapter(distritoAdaptador);
                        FragmentoDistrito fdistrito = new FragmentoDistrito();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fdistrito, null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila = -1;
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el Distrito");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmDistrito));
                    }

                } else {
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstDis.requestFocus();
                }

            }
        });

        return raiz;

    }
}
