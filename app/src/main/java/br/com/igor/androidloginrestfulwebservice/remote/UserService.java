package br.com.igor.androidloginrestfulwebservice.remote;



import android.widget.ImageView;

import br.com.igor.androidloginrestfulwebservice.model.ResImage;
import br.com.igor.androidloginrestfulwebservice.model.ResLogin;
import br.com.igor.androidloginrestfulwebservice.model.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;

/**
 * Created by igor on 07/03/2018.
 */

public interface UserService {

    @POST("login")
    Call<ResLogin> login(@Body() Usuario usuario);

    @POST("register")
    Call<ResLogin> cadastro(@Body() Usuario usuario);

    @GET("logo")
    Call<ResImage> getLogo(@Body ImageView imageView);

}
