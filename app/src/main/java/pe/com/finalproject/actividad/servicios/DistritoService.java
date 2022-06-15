package pe.com.finalproject.actividad.servicios;

import java.util.List;

import pe.com.finalproject.clases.Distrito;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DistritoService {
    @GET("/distrito/all")
    Call<List<Distrito>> MostrarDistrito();
    @GET("/distrito")
    Call<List<Distrito>> MostrarDistritoPersonalizado();
    @POST("/distrito")
    Call<List<Distrito>>RegistrarDistrito(@Body Distrito d);
    @PUT("/distrito/{id}")
    Call<List<Distrito>> ActualziarDistrito(@Path ("id") long id,@Body Distrito d);
    @DELETE("/distrito/{id}")
    Call<List<Distrito>> EliminarDistrito(@Path ("id") long id);
}