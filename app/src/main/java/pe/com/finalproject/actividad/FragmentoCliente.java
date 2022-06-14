package pe.com.finalproject.actividad;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import pe.com.finalproject.adaptadores.DistritoAdaptador;
import pe.com.finalproject.adaptadores.DistritoAdaptadorCombo;
import pe.com.finalproject.adaptadores.ClienteAdaptador;
import pe.com.finalproject.clases.Distrito;
import pe.com.finalproject.clases.Cliente;
import pe.com.finalproject.implementaciones.ImpDistrito;
import pe.com.finalproject.implementaciones.ImpCliente;
import pe.com.finalproject.interfaces.IDistrito;
import pe.com.finalproject.interfaces.ICliente;

public class FragmentoCliente extends Fragment {

    private EditText txtNomcli, txtApepcli,txtApemcli, txtDnicli, txtTelcli,txtCelcli,txtCorcli, txtDircli;
    private Spinner cboDistrito;
    private RadioButton rbMascli,rbFemcli, rbOtrcli;
    private RadioGroup rbgSexcli;
    private CheckBox chkEstCli;
    private TextView lblCodcli,lblCoddis;
    private Button btnRegistrarcli, btnActualizarcli,btnEliminarcli;
    private ListView lstCli;


    private ClienteAdaptador clienteadaptador;
    private DistritoAdaptadorCombo distritoadaptadorcombo;

    //variables
    private int cod=0, fila=-1, indice=-1, codd=0,pos=-1;
    private String nom="", apep="",apem="",dni="",dir="",dis="",tel=""
            ,cel="",cor="",sex="";
    private boolean est=false, res=false;

    //implementar la interfaz
    private ICliente clientes= new ImpCliente();
    private IDistrito distritos=new ImpDistrito();

    //objeto de la clase Distrito

    //ArrayList de tipo distrito
    private ArrayList<Cliente> registrocliente;
    private ArrayList<Distrito> registrodistrito;


    FragmentTransaction ft;


    Utilidad objutilidad= new Utilidad();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View raiz=inflater.inflate(R.layout.fragmento_cliente, container, false);

        //creamos los controles
        txtNomcli=raiz.findViewById(R.id.txtNomcli);
        txtApepcli=raiz.findViewById(R.id.txtApepcli);
        txtApemcli=raiz.findViewById(R.id.txtApemcli);
        txtDnicli=raiz.findViewById(R.id.txtDnicli);
        cboDistrito=raiz.findViewById(R.id.cboDistrito);
        txtTelcli=raiz.findViewById(R.id.txtTelcli);
        txtCelcli=raiz.findViewById(R.id.txtCelcli);
        txtCorcli=raiz.findViewById(R.id.txtCorcli);
        txtDircli=raiz.findViewById(R.id.txtDircli);
        rbMascli=raiz.findViewById(R.id.rbMascli);
        rbFemcli=raiz.findViewById(R.id.rbFemcli);
        rbOtrcli=raiz.findViewById(R.id.rbOtrcli);
        rbgSexcli=raiz.findViewById(R.id.rbgSexo);
        chkEstCli=raiz.findViewById(R.id.chkEstcli);
        lblCodcli=raiz.findViewById(R.id.lblCodcli);
        lblCoddis=raiz.findViewById(R.id.lblCoddis);
        btnRegistrarcli=raiz.findViewById(R.id.btnRegistrarcli);
        btnActualizarcli=raiz.findViewById(R.id.btnActualizarcli);
        btnEliminarcli=raiz.findViewById(R.id.btnEliminarcli);
        lstCli=raiz.findViewById(R.id.lstCli);

        registrodistrito=new ArrayList<>();
        registrocliente=new ArrayList<>();

        registrodistrito=distritos.MostrarDistrito(raiz.getContext());
        if(registrodistrito!=null){
            distritoadaptadorcombo=new DistritoAdaptadorCombo(raiz.getContext(),registrodistrito);
            cboDistrito.setAdapter(distritoadaptadorcombo);
        }

        registrocliente=clientes.MostrarCliente(raiz.getContext());
        if(registrocliente!=null) {
            clienteadaptador=new ClienteAdaptador(raiz.getContext(),registrocliente);
            lstCli.setAdapter(clienteadaptador);
        }



        btnRegistrarcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNomcli.getText().toString().equals("")){
                    objutilidad.MensajeToast(raiz.getContext(), "Ingrese un nombre");
                    txtNomcli.requestFocus();
                } else if(cboDistrito.getSelectedItemPosition()==0){
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un distrito");
                    txtNomcli.requestFocus();
                }else if(rbMascli.isChecked()==false && rbFemcli.isChecked()==false && rbOtrcli.isChecked()==false){
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un sexo");
                    txtNomcli.requestFocus();
                }else{
                    Cliente objcliente=new Cliente();
                    Distrito objdistrito=new Distrito();
                    //capturando valores
                    nom=txtNomcli.getText().toString();
                    apep=txtApepcli.getText().toString();
                    apem=txtApemcli.getText().toString();
                    dni=txtDnicli.getText().toString();
                    dir=txtDircli.getText().toString();

                    pos=cboDistrito.getSelectedItemPosition();
                    dis=registrodistrito.get(pos).getNombre();
                    codd= Math.toIntExact(registrodistrito.get(pos).getCodigo());

                    tel=txtTelcli.getText().toString();
                    cel=txtCelcli.getText().toString();
                    cor=txtCorcli.getText().toString();
                    if(rbMascli.isChecked()==true){
                        sex="Masculino";
                    }else if(rbFemcli.isChecked()==true){
                        sex="Femenino";
                    }else if(rbOtrcli.isChecked()==true){
                        sex="Otros";
                    }else{
                        sex="";
                    }

                    if(chkEstCli.isChecked()){
                        est=true;
                    }else{
                        est=false;
                    }
                    //enviando los valores a la clase
                    objcliente.setCodigo(cod);
                    objcliente.setNombre(nom);
                    objcliente.setApellidopaterno(apep);
                    objcliente.setApellidomaterno(apem);
                    objcliente.setDni(dni);

                    objdistrito.setNombre(dis);
                    objdistrito.setCodigo(codd);
                    objcliente.setDistrito(objdistrito);

                    objcliente.setTelefono(tel);
                    objcliente.setCelular(cel);
                    objcliente.setCorreo(cor);
                    objcliente.setDireccion(dir);
                    objcliente.setSexo(sex);

                    objcliente.setEstado(est);
                    res=clientes.RegistrarCliente(objcliente,raiz.getContext());
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se registro el cliente");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                        FragmentoCliente fcliente=new FragmentoCliente();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fcliente,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se registro el cliente");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    }
                }

            }
        });

        lstCli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fila=position;
                lblCodcli.setText(""+registrocliente.get(fila).getCodigo());
                txtNomcli.setText(""+registrocliente.get(fila).getNombre());
                txtApepcli.setText(""+registrocliente.get(fila).getApellidopaterno());
                txtApemcli.setText(""+registrocliente.get(fila).getApellidomaterno());
                txtDnicli.setText(""+registrocliente.get(fila).getDni());

                for(int i=0;i<registrodistrito.size();i++){
                    if(registrodistrito.get(i).getNombre().equals(registrocliente.get(fila).getDistrito().getNombre())){
                        indice=i;
                    }
                }
                cboDistrito.setSelection(indice);

                txtTelcli.setText(""+registrocliente.get(fila).getTelefono());
                txtCelcli.setText(""+registrocliente.get(fila).getCelular());
                txtCorcli.setText(""+registrocliente.get(fila).getCorreo());
                txtDircli.setText(""+registrocliente.get(fila).getDireccion());


                if(registrocliente.get(fila).getSexo().equals("Masculino")){
                    rbMascli.setChecked(true);
                }else if(registrocliente.get(fila).getSexo().equals("Femenino")){
                    rbFemcli.setChecked(true);
                }else{
                    rbOtrcli.setChecked(true);
                }


                if(registrocliente.get(fila).isEstado()==true){
                    chkEstCli.setChecked(true);
                }else {
                    chkEstCli.setChecked(false);
                }
            }
        });

        btnActualizarcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente objcliente=new Cliente();

                Distrito objdistrito=new Distrito();

                cod=Integer.parseInt(lblCodcli.getText().toString());
                nom=txtNomcli.getText().toString();
                apep=txtApepcli.getText().toString();
                apem=txtApemcli.getText().toString();
                dni=txtDnicli.getText().toString();

                pos=cboDistrito.getSelectedItemPosition();
                dis=registrodistrito.get(pos).getNombre();
                codd= Math.toIntExact(registrodistrito.get(pos).getCodigo());

                tel=txtTelcli.getText().toString();
                cel=txtCelcli.getText().toString();
                cor=txtCorcli.getText().toString();
                dir=txtDircli.getText().toString();
                if(rbMascli.isChecked()==true){
                    sex="Masculino";
                }else if(rbFemcli.isChecked()==true){
                    sex="Femenino";
                }else if(rbOtrcli.isChecked()==true){
                    sex="Otros";
                }else{
                    sex="";
                }


                if(chkEstCli.isChecked()){
                    est=true;
                }else{
                    est=false;
                }
                objcliente.setCodigo(cod);
                objcliente.setNombre(nom);
                objcliente.setApellidopaterno(apep);
                objcliente.setApellidomaterno(apem);
                objcliente.setDni(dni);

                objdistrito.setCodigo(codd);
                objdistrito.setNombre(dis);
                objcliente.setDistrito(objdistrito);

                objcliente.setTelefono(tel);
                objcliente.setCelular(cel);
                objcliente.setCorreo(cor);
                objcliente.setDireccion(dir);
                objcliente.setSexo(sex);

                objcliente.setEstado(est);

                res=clientes.ActualizarCliente(objcliente);
                if(res){
                    objutilidad.MensajeToast(raiz.getContext(), "Se registro el cliente");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    FragmentoCliente fcliente=new FragmentoCliente();
                    ft= getFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,fcliente,null);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "No se actualizo el cliente");
                    objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                }
            }
        });

        btnEliminarcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fila>=0){
                    Cliente objcliente=new Cliente();
                    cod=Integer.parseInt(lblCodcli.getText().toString());
                    objcliente.setCodigo(cod);
                    res=clientes.EliminarCliente(objcliente);
                    if(res){
                        objutilidad.MensajeToast(raiz.getContext(), "Se elimino el cliente");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                        FragmentoCliente fcliente=new FragmentoCliente();
                        ft= getFragmentManager().beginTransaction();
                        ft.replace(R.id.contenedor,fcliente,null);
                        ft.addToBackStack(null);
                        ft.commit();
                    }else{
                        objutilidad.MensajeToast(raiz.getContext(), "No se elimino el cliente");
                        objutilidad.Limpiar((ViewGroup) raiz.findViewById(R.id.frmCliente));
                    }

                }else{
                    objutilidad.MensajeToast(raiz.getContext(), "Seleccione un elemento de la lista");
                    lstCli.requestFocus();
                }

            }
        });

        return raiz;
    }
}
