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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.finalproject.R;
import pe.com.finalproject.adaptadores.DistritoAdaptadorCombo;
import pe.com.finalproject.adaptadores.EmpleadoAdaptador;
import pe.com.finalproject.adaptadores.PerfilAdaptadorCombo;
import pe.com.finalproject.clases.Distrito;
import pe.com.finalproject.clases.Empleado;
import pe.com.finalproject.clases.Perfil;
import pe.com.finalproject.implementaciones.ImpDistrito;
import pe.com.finalproject.implementaciones.ImpEmpleado;
import pe.com.finalproject.implementaciones.ImpPerfil;
import pe.com.finalproject.interfaces.IDistrito;
import pe.com.finalproject.interfaces.IEmpleado;
import pe.com.finalproject.interfaces.IPerfil;

public class FragmentoEmpleado extends Fragment {
    //declarando los controles
    private EditText txtNomemp, txtApepemp,txtApememp, txtDniemp,
            txtDiremp, txtTelemp,txtCelemp,txtCoremp,txtUsuemp,
            txtClaemp;
    private Spinner cboDistrito,cboPerfil;
    private RadioButton rbMasemp,rbFememp, rbOtremp;
    private RadioGroup rbgSexemp;
    private CheckBox chkEstEmp;
    private TextView lblCodemp,lblCoddis, lblCodper;
    private Button btnRegistraremp, btnActualizaremp,btnEliminaremp;
    private ListView lstEmp;

    //adaptador de tipo Distrito
    private EmpleadoAdaptador empleadoadaptador;
    private PerfilAdaptadorCombo  perfiladaptadorcombo;
    private DistritoAdaptadorCombo distritoadaptadorcombo;

    //variables
    private int cod=0, fila=-1, indice=-1, codd=0, codp=0,pos=-1;
    private String nom="", apep="",apem="",dni="",dir="",dis="",tel=""
            ,cel="",cor="",sex="",usu="",cla="",per="";
    private boolean est=false, res=false;

    //implementar la interfaz
    private IEmpleado empleados= new ImpEmpleado();
    private IPerfil perfiles=new ImpPerfil();
    private IDistrito distritos=new ImpDistrito();

    //objeto de la clase Distrito

    //ArrayList de tipo distrito
    private ArrayList<Empleado> registroempleado;
    private ArrayList<Distrito> registrodistrito;
    private ArrayList<Perfil> registroperfil;


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
        final View raiz=inflater.inflate(R.layout.fragmento_empleado, container, false);

        //creamos los controles
        txtNomemp=raiz.findViewById(R.id.txtNomemp);
        txtApepemp=raiz.findViewById(R.id.txtApepemp);
        txtApememp=raiz.findViewById(R.id.txtApememp);
        txtDniemp=raiz.findViewById(R.id.txtDniemp);
        txtDiremp=raiz.findViewById(R.id.txtDiremp);
        cboDistrito=raiz.findViewById(R.id.cboDistrito);
        txtTelemp=raiz.findViewById(R.id.txtTelemp);
        txtCelemp=raiz.findViewById(R.id.txtCelemp);
        txtCoremp=raiz.findViewById(R.id.txtCoremp);
        rbMasemp=raiz.findViewById(R.id.rbMasemp);
        rbFememp=raiz.findViewById(R.id.rbFememp);
        rbOtremp=raiz.findViewById(R.id.rbOtremp);
        rbgSexemp=raiz.findViewById(R.id.rbgSexo);
        txtUsuemp=raiz.findViewById(R.id.txtUsuemp);
        txtClaemp=raiz.findViewById(R.id.txtClaemp);
        cboPerfil=raiz.findViewById(R.id.cboPerfil);
        chkEstEmp=raiz.findViewById(R.id.chkEstemp);
        lblCodemp=raiz.findViewById(R.id.lblCodCli);
        lblCoddis=raiz.findViewById(R.id.lblCodDis);
        lblCodper=raiz.findViewById(R.id.lblCodper);
        btnRegistraremp=raiz.findViewById(R.id.btnRegistraremp);
        btnActualizaremp=raiz.findViewById(R.id.btnActualizaremp);
        btnEliminaremp=raiz.findViewById(R.id.btnEliminaremp);
        lstEmp=raiz.findViewById(R.id.lstEmp);

        registrodistrito=new ArrayList<>();
        registroperfil=new ArrayList<>();
        registroempleado=new ArrayList<>();

        registrodistrito=distritos.MostrarDistrito(raiz.getContext());
        if(registrodistrito!=null){
            distritoadaptadorcombo=new DistritoAdaptadorCombo(raiz.getContext(),registrodistrito);
            cboDistrito.setAdapter(distritoadaptadorcombo);
        }

        registroperfil=perfiles.MostrarPerfil(raiz.getContext());
        if(registroperfil!=null){
            perfiladaptadorcombo=new PerfilAdaptadorCombo(raiz.getContext(),registroperfil);
            cboPerfil.setAdapter(perfiladaptadorcombo);
        }

        registroempleado=empleados.MostrarEmpleado(raiz.getContext());
        if(registroempleado!=null) {
            empleadoadaptador=new EmpleadoAdaptador(raiz.getContext(),registroempleado);
            lstEmp.setAdapter(empleadoadaptador);
        }


        //eventos
        btnRegistraremp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNomemp.getText().toString().equals("")){
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNomemp.requestFocus();
                } else if(cboDistrito.getSelectedItemPosition()==0){
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un distrito");
                    txtNomemp.requestFocus();
                }else if(rbMasemp.isChecked()==false && rbFememp.isChecked()==false && rbOtremp.isChecked()==false){
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un sexo");
                    txtNomemp.requestFocus();
                }else{
                    Empleado objempleado=new Empleado();
                    Perfil objperfil= new Perfil();
                    Distrito objdistrito=new Distrito();
                    //capturando valores
                    nom=txtNomemp.getText().toString();
                    apep=txtApepemp.getText().toString();
                    apem=txtApememp.getText().toString();
                    dni=txtDniemp.getText().toString();
                    dir=txtDiremp.getText().toString();

                    pos=cboDistrito.getSelectedItemPosition();
                    dis=registrodistrito.get(pos).getNombre();
                    codd= Math.toIntExact(registrodistrito.get(pos).getCodigo());

                    tel=txtTelemp.getText().toString();
                    cel=txtCelemp.getText().toString();
                    cor=txtCoremp.getText().toString();
                    if(rbMasemp.isChecked()==true){
                        sex="Masculino";
                    }else if(rbFememp.isChecked()==true){
                        sex="Femenino";
                    }else if(rbOtremp.isChecked()==true){
                        sex="Otros";
                    }else{
                        sex="";
                    }
                    usu=txtUsuemp.getText().toString();
                    cla=txtClaemp.getText().toString();
                    pos=cboPerfil.getSelectedItemPosition();
                    per=registroperfil.get(pos).getNombre();
                    codp= Math.toIntExact(registroperfil.get(pos).getCodigo());

                    if(chkEstEmp.isChecked()){
                        est=true;
                    }else{
                        est=false;
                    }
                    //enviando los valores a la clase
                    objempleado.setCodigo(cod);
                    objempleado.setNombre(nom);
                    objempleado.setApellidopaterno(apep);
                    objempleado.setApellidomaterno(apem);
                    objempleado.setDni(dni);
                    objempleado.setDireccion(dir);

                    objdistrito.setNombre(dis);
                    objdistrito.setCodigo(codd);
                    objempleado.setDistrito(objdistrito);

                    objempleado.setTelefono(tel);
                    objempleado.setCelular(cel);
                    objempleado.setCorreo(cor);
                    objempleado.setSexo(sex);
                    objempleado.setUsuario(usu);
                    objempleado.setClave(cla);

                    objperfil.setNombre(per);
                    objperfil.setCodigo(codp);
                    objempleado.setPerfil(objperfil);

                    objempleado.setEstado(est);
                    res=empleados.RegistrarEmpleado(objempleado,raiz.getContext());
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el empleado");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                        FragmentoEmpleado fempleado=new FragmentoEmpleado();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fempleado,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el empleado");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    }
                }

            }
        });

        lstEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila=position;
                //asignamos los valores a cada control
                lblCodemp.setText(""+registroempleado.get(fila).getCodigo());
                txtNomemp.setText(""+registroempleado.get(fila).getNombre());
                txtApepemp.setText(""+registroempleado.get(fila).getApellidopaterno());
                txtApememp.setText(""+registroempleado.get(fila).getApellidomaterno());
                txtDniemp.setText(""+registroempleado.get(fila).getDni());
                txtDiremp.setText(""+registroempleado.get(fila).getDireccion());

                for(int i=0;i<registrodistrito.size();i++){
                    if(registrodistrito.get(i).getNombre().equals(registroempleado.get(fila).getDistrito().getNombre())){
                        indice=i;
                    }
                }
                cboDistrito.setSelection(indice);

                txtTelemp.setText(""+registroempleado.get(fila).getTelefono());
                txtCelemp.setText(""+registroempleado.get(fila).getCelular());
                txtCoremp.setText(""+registroempleado.get(fila).getCorreo());


                if(registroempleado.get(fila).getSexo().equals("Masculino")){
                    rbMasemp.setChecked(true);
                }else if(registroempleado.get(fila).getSexo().equals("Femenino")){
                    rbFememp.setChecked(true);
                }else{
                    rbOtremp.setChecked(true);
                }
                txtUsuemp.setText(""+registroempleado.get(fila).getUsuario());
                txtClaemp.setText(""+registroempleado.get(fila).getClave());

                for(int i=0;i<registroperfil.size();i++){
                    if(registroperfil.get(i).getNombre().equals(registroempleado.get(fila).getPerfil().getNombre())){
                        indice=i;
                    }
                }
                cboPerfil.setSelection(indice);


                if(registroempleado.get(fila).isEstado()==true){
                    chkEstEmp.setChecked(true);
                }else {
                    chkEstEmp.setChecked(false);
                }
            }
        });

        btnActualizaremp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado objempleado=new Empleado();

                Perfil objperfil= new Perfil();
                Distrito objdistrito=new Distrito();

                cod=Integer.parseInt(lblCodemp.getText().toString());
                nom=txtNomemp.getText().toString();
                apep=txtApepemp.getText().toString();
                apem=txtApememp.getText().toString();
                dni=txtDniemp.getText().toString();
                dir=txtDiremp.getText().toString();

                pos=cboDistrito.getSelectedItemPosition();
                dis=registrodistrito.get(pos).getNombre();
                codd= Math.toIntExact(registrodistrito.get(pos).getCodigo());

                tel=txtTelemp.getText().toString();
                cel=txtCelemp.getText().toString();
                cor=txtCoremp.getText().toString();
                if(rbMasemp.isChecked()==true){
                    sex="Masculino";
                }else if(rbFememp.isChecked()==true){
                    sex="Femenino";
                }else if(rbOtremp.isChecked()==true){
                    sex="Otros";
                }else{
                    sex="";
                }
                usu=txtUsuemp.getText().toString();
                cla=txtClaemp.getText().toString();

                pos=cboPerfil.getSelectedItemPosition();
                per=registroperfil.get(pos).getNombre();
                codp= Math.toIntExact(registroperfil.get(pos).getCodigo());

                if(chkEstEmp.isChecked()){
                    est=true;
                }else{
                    est=false;
                }
                //enviando los valores a la clase
                objempleado.setCodigo(cod);
                objempleado.setNombre(nom);
                objempleado.setApellidopaterno(apep);
                objempleado.setApellidomaterno(apem);
                objempleado.setDni(dni);
                objempleado.setDireccion(dir);

                objdistrito.setCodigo(codd);
                objdistrito.setNombre(dis);
                objempleado.setDistrito(objdistrito);

                objempleado.setTelefono(tel);
                objempleado.setCelular(cel);
                objempleado.setCorreo(cor);
                objempleado.setSexo(sex);
                objempleado.setUsuario(usu);
                objempleado.setClave(cla);

                objperfil.setCodigo(codp);
                objperfil.setNombre(per);
                objempleado.setPerfil(objperfil);

                objempleado.setEstado(est);

                res=empleados.ActualizarEmpleado(objempleado);
                if(res){
                    objutilidad.MensajeToast(raiz.getContext(), "Se registro el empleado");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    FragmentoEmpleado fempleado=new FragmentoEmpleado();
                    ft= getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,fempleado,null);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el empleado");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                }
            }
        });

        btnEliminaremp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fila>=0){
                    Empleado objempleado=new Empleado();
                    cod=Integer.parseInt(lblCodemp.getText().toString());
                    objempleado.setCodigo(cod);
                    res=empleados.EliminarEmpleado(objempleado);
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el empleado");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                        FragmentoEmpleado fempleado=new FragmentoEmpleado();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fempleado,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el empleado");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    }

                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstEmp.requestFocus();
                }

            }
        });

        return raiz;
    }
}
