package br.com.igor.androidloginrestfulwebservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.igor.androidloginrestfulwebservice.controller.BancoController;

public class CadastroLoginActivity extends AppCompatActivity {

    EditText tUrl;
    EditText tUsuario;
    EditText tSenha;
    Button btnSenhas;
    Button btnVisualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_login);

        tUrl = findViewById(R.id.txtUrl);
        tUsuario = findViewById(R.id.txtUsuario);
        tSenha = findViewById(R.id.txtSenha);

        btnSenhas = findViewById(R.id.botaoCadastrarSenhas);

        btnSenhas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoController crud = new BancoController(getBaseContext());
                String url = tUrl.getText().toString();
                String usuario = tUsuario.getText().toString();
                String senha = tSenha.getText().toString();

                String resultado;

                resultado = crud.insereDado(url, usuario, senha);

                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
            }
        });
        btnVisualizar = findViewById(R.id.botaoVisualizar);

        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
