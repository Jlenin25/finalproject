package pe.com.finalproject.actividad;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import pe.com.finalproject.R;
import pe.com.finalproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;


public class FragmentoBienvenida extends Fragment {
    private FragmentTransaction ft;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Intent formulario;
    private Utilidad objutilidad= new Utilidad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.jmiDistrito:
                FragmentoDistrito fdistrito= new FragmentoDistrito();
                ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor,fdistrito,null);
                ft.addToBackStack(null);
                ft.commit();
                return true;

            case R.id.jmiPerfil:
                FragmentoPerfil fperfil= new FragmentoPerfil();
                ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor,fperfil,null);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            case R.id.jmiEmpleado:
                FragmentoEmpleado fempleado= new FragmentoEmpleado();
                ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor,fempleado,null);
                ft.addToBackStack(null);
                ft.commit();
                return true;

            case R.id.jmiCliente:
                FragmentoCliente fcliente= new FragmentoCliente();
                ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.contenedor,fcliente,null);
                ft.addToBackStack(null);
                ft.commit();
                return true;

            case R.id.jmiCerrarSesion:
                formulario= new Intent(getApplicationContext(),ActividadIngreso.class);
                startActivity(formulario);
                finish();
                return true;
            case R.id.jmiSalir:
                objutilidad.SalirSistema(this);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
