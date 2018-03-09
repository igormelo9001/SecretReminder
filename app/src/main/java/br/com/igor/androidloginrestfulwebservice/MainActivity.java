package br.com.igor.androidloginrestfulwebservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import br.com.igor.androidloginrestfulwebservice.controller.Banco;
import br.com.igor.androidloginrestfulwebservice.controller.BancoController;
import br.com.igor.androidloginrestfulwebservice.model.ResImage;
import br.com.igor.androidloginrestfulwebservice.model.ResLogin;
import br.com.igor.androidloginrestfulwebservice.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    TextView txtUsername;
    Button logout;
    Button cadastrarNovo;
    ImageView imagem;
    private ListView lista;
    Cursor cursor;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getApplicationContext().getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);

        if(token == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        BancoController crud = new BancoController(getBaseContext());
        cursor = crud.carregaDados();

        String[] nomeCampos = new String[] {Banco.ID, Banco.URL_SITE};
        int[] idViews = new int[]{R.id.idLogin, R.id.urlSite};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.logins,cursor,nomeCampos,idViews,0);
        lista = (ListView)findViewById(R.id.listView);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(Banco.ID));
                Intent intent = new Intent(MainActivity.this, Alterar.class);
                intent.putExtra("codigo", codigo);
                startActivity(intent);
                // finish();
            }
        });


        cadastrarNovo = findViewById(R.id.btnUrlLogin);
        cadastrarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroLoginActivity.class);
                startActivity(intent);
            }
        });

        logout = (Button)findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("token"); // will delete key key_name4
                editor.commit(); // commit changes

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private View pegarLogo(String token){
        Call<ResImage> call = userService.getLogo(imagem);
        if(token != null){
            call.enqueue(new Callback<ResImage>() {
                @Override
                public void onResponse(Call<ResImage> call, Response<ResImage> response) {
                    if(response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<ResImage> call, Throwable t) {

                }
            });
        }
        return imagem;
    }

}
