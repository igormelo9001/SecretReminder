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

import java.util.regex.Pattern;

import br.com.igor.androidloginrestfulwebservice.model.ResLogin;
import br.com.igor.androidloginrestfulwebservice.model.Usuario;
import br.com.igor.androidloginrestfulwebservice.remote.ApiUtils;
import br.com.igor.androidloginrestfulwebservice.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    EditText Tnome;
    EditText Temail;
    EditText Tsenha;
    Button botaoCadastrar;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Tnome = findViewById(R.id.login);
        Temail = findViewById(R.id.email);
        Tsenha = findViewById(R.id.senha);
       
        userService = ApiUtils.getUserService();
        botaoCadastrar = findViewById(R.id.btnCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = Tnome.getText().toString();
                String email = Temail.getText().toString();
                String senha = Tsenha.getText().toString();

                Usuario usuario = new Usuario(email, senha, nome);
                if(validateCadastro(nome, email, senha)){
                    cadastrar(usuario);
                }
            }
        });
    }

    private void cadastrar(Usuario usuario) {
        Call<ResLogin> call = userService.cadastro(usuario);
        call.enqueue(new Callback<ResLogin>() {
            @Override
            public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                if (response.isSuccessful()) {
                    ResLogin resLogin = response.body();
                    if (resLogin.getType().equals("sucess")) {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("token", resLogin.getToken());

                        editor.commit();
                        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CadastroActivity.this, resLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.i("LogX", response.toString());
                    Toast.makeText(CadastroActivity.this, "Por favor tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResLogin> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateCadastro(String username, String email, String password) {
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(this, "Digite um nome", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(email == null || email.trim().length() == 0){
            Toast.makeText(this,"Digite um email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Digite uma senha", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (password.length() < 8)return false;
//            Toast.makeText(this, "Digite uma senha que possua pelo menos 8 caracteres", Toast.LENGTH_SHORT).show();
//
//            // Criterio 2: Senha contém letras maiúsculas
//            Pattern p2 = Pattern.compile ("[A-Z]");
//            if ( ! p2.matcher (password).find()) return false;
//            Toast.makeText(this, "Digite uma senha que possua pelo menos um caracterer maiúsculo", Toast.LENGTH_SHORT).show();
//
//            // Criterio 3: Senha contém letras minúsculas
//            Pattern p3 = Pattern.compile ("[a-z]");
//            if ( ! p3.matcher (password).find())return false;
//            Toast.makeText(this, "Digite uma senha que possua pelo menos um caracterer minúsculo", Toast.LENGTH_SHORT).show();
//
//            // Criterio 4: Senha contém dígitos
//            Pattern p4 = Pattern.compile ("[0-9]");
//            if ( ! p4.matcher (password).find())return false;
//            Toast.makeText(this, "Digite uma senha que possua pelo um dígito", Toast.LENGTH_SHORT).show();
//
//            // Critério 5: Senha contém um dos seguintes caracteres especiais: + - * / = %
//            Pattern p5 = Pattern.compile ("[-+*/=%]");
//            if ( ! p5.matcher (password).find())return false;
//            Toast.makeText(this, "Digite uma senha que possua pelo menos um caracter especial", Toast.LENGTH_SHORT).show();
//
//            // senão...

        return true;
    }
}
