package br.com.igor.androidloginrestfulwebservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.igor.androidloginrestfulwebservice.model.ResLogin;
import br.com.igor.androidloginrestfulwebservice.model.Usuario;
import br.com.igor.androidloginrestfulwebservice.remote.ApiUtils;
import br.com.igor.androidloginrestfulwebservice.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Button btnCadastro;
    UserService userService;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnCadastro = findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        userService = ApiUtils.getUserService();
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                usuario = new Usuario(username, password);

                //validar login e senha
                if (validateLogin(username, password)) {
                    doLogin(usuario);
                }
            }
        });
    }

    private void doLogin(final Usuario usuario) {
        Call<ResLogin> call = userService.login(usuario);
        call.enqueue(new Callback<ResLogin>() {
                         @Override
                         public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                             //Log.i("LogX", response.body().getToken());
                             if (response.isSuccessful()) {
                                 ResLogin resLogin = response.body();
                                 if (resLogin.getType().equals("sucess")) {
                                     // salva Token
                                     SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                                     SharedPreferences.Editor editor = sharedPref.edit();
                                     editor.putString("token", resLogin.getToken());
                                     editor.commit();
                                     // login start MainActivity
                                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                     startActivity(intent);
                                     finish();
                                 } else {
                                     Toast.makeText(LoginActivity.this, resLogin.getMessage(), Toast.LENGTH_SHORT).show();
                                 }

                             } else {
                                 Log.i("LogX", response.toString());
                                 Toast.makeText(LoginActivity.this, "Error Please try again!", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<ResLogin> call, Throwable t) {
                             Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }

        );
    }

    private boolean validateLogin(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
