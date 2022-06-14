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
import pe.com.finalproject.adaptadores.CategoriaAdaptador;
import pe.com.finalproject.clases.Categoria;
import pe.com.finalproject.implementaciones.ImpCategoria;
import pe.com.finalproject.interfaces.ICategoria;

public class FragmentoCategoria extends Fragment {
    //declarando los controles
    private EditText txtNomcat;
    private CheckBox chkEstcat;
    private TextView lblCodcat;
    private Button btnRegistrarcat, btnActualizarcat, btnEliminarcat;
    private ListView lstCat;

    //adaptador de tipo Categoria
    private CategoriaAdaptador categoriaAdaptador;

    //variables
    private int cod = 0, fila = -1;
    private String nom = "";
    private boolean est = false, res = false;

    //implementar la interfaz
    private ICategoria categorias = new ImpCategoria();

    //objeto de la clase Categoria
    private Categoria objcategoria = new Categoria();

    //ArrayList de tipo categoria
    private ArrayList<Categoria> registrocategoria;

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
        final View raiz = inflater.inflate(R.layout.fragmento_categoria, container, false);

        //creamos los controles
        txtNomcat = raiz.findViewById(R.id.txtNomcat);
        chkEstcat = raiz.findViewById(R.id.chkEstcat);
        lblCodcat = raiz.findViewById(R.id.lblCodCat);
        btnRegistrarcat = raiz.findViewById(R.id.btnRegistrarcat);
        btnActualizarcat = raiz.findViewById(R.id.btnActualizarcat);
        btnEliminarcat = raiz.findViewById(R.id.btnEliminarcat);
        lstCat = raiz.findViewById(R.id.lstCat);

        //creamos el Arraylist Categoria
        registrocategoria = new ArrayList<>();
        //asignamos el valor de mostrar
        registrocategoria = categorias.MostrarCategoria(raiz.getContext());
        if (registrocategoria != null) {
            //creamos el adaptador
            categoriaAdaptador = new CategoriaAdaptador(raiz.getContext(), registrocategoria);
            //asginamos el adaptador
            lstCat.setAdapter(categoriaAdaptador);
        }

        //eventos
        btnRegistrarcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNomcat.getText().toString().equals("")) {
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNomcat.requestFocus();
                } else {
                    //capturando valores
                    nom = txtNomcat.getText().toString();
                    if (chkEstcat.isChecked()) {
                        est = true;
                    } else {
                        est = false;
                    }
                    //enviando los valores a la clase
                    objcategoria.setNombre(nom);
                    objcategoria.setEstado(est);
                    res = categorias.RegistrarCategoria(objcategoria, raiz.getContext());
                    if (res) {
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el categoria");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                        FragmentoCategoria fcategoria = new FragmentoCategoria();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fcategoria, null);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el categoria");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                    }
                }

            }
        });

        lstCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila = position;
                //asignamos los valores a cada control
                lblCodcat.setText("" + registrocategoria.get(fila).getCodigo());
                txtNomcat.setText("" + registrocategoria.get(fila).getNombre());
                if (registrocategoria.get(fila).isEstado() == true) {
                    chkEstcat.setChecked(true);
                } else {
                    chkEstcat.setChecked(false);
                }
            }
        });

        btnActualizarcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fila >= 0) {
                    cod = Integer.parseInt(lblCodcat.getText().toString());
                    nom = txtNomcat.getText().toString();
                    if (chkEstcat.isChecked() == true) {
                        est = true;
                    } else {
                        est = false;
                    }
                    objcategoria.setCodigo(cod);
                    objcategoria.setNombre(nom);
                    objcategoria.setEstado(est);
                    res = categorias.ActualizarCategoria(objcategoria);
                    if (res == true) {
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                        objutilidad.MensajeToast(raiz.getContext(), "Se actualizo el Categoria");
                        categoriaAdaptador.notifyDataSetChanged();
                        lstCat.setAdapter(categoriaAdaptador);
                        FragmentoCategoria fcategoria = new FragmentoCategoria();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fcategoria, null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila = -1;
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el categoria");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                    }

                } else {
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstCat.requestFocus();
                }

            }
        });

        btnEliminarcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fila >= 0) {
                    cod = Integer.parseInt(lblCodcat.getText().toString());
                    objcategoria.setCodigo(cod);
                    res = categorias.EliminarCategoria(objcategoria);
                    if (res == true) {
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el Categoria");
                        categoriaAdaptador.notifyDataSetChanged();
                        lstCat.setAdapter(categoriaAdaptador);
                        FragmentoCategoria fcategoria = new FragmentoCategoria();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor, fcategoria, null);
                        ft.addToBackStack(null);
                        ft.commit();
                        fila = -1;
                    } else {
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el Categoria");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCategoria));
                    }

                } else {
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstCat.requestFocus();
                }

            }
        });

        return raiz;

    }
}
