package br.com.igor.androidloginrestfulwebservice;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.igor.androidloginrestfulwebservice.controller.Banco;
import br.com.igor.androidloginrestfulwebservice.controller.BancoController;

public class Alterar extends AppCompatActivity {

    EditText txtUrlSite;
    EditText txtUsuario;
    EditText txtSenha;
    Button btnAlterar;
    Button btnDeletar;
    Cursor cursor;
    BancoController crud;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        codigo = this.getIntent().getStringExtra("codigo");

        crud = new BancoController(getBaseContext());

        txtUrlSite = findViewById(R.id.altUrl);
        txtUsuario = findViewById(R.id.altUsuario);
        txtSenha = findViewById(R.id.altSenha);

        btnAlterar = findViewById(R.id.botaoAlterarSenhas);

        cursor = crud.carregaDadoById(Integer.parseInt(codigo));
        txtUrlSite.setText(cursor.getString(cursor.getColumnIndexOrThrow(Banco.URL_SITE)));
        txtUsuario.setText(cursor.getString(cursor.getColumnIndexOrThrow(Banco.USUARIO)));
        txtSenha.setText(cursor.getString(cursor.getColumnIndexOrThrow(Banco.SENHA)));

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.alteraRegistro(Integer.parseInt(codigo),
                        txtUrlSite.getText().toString(),txtUsuario.getText().toString(),
                        txtSenha.getText().toString());
                        Intent intent = new Intent(Alterar.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeletar = findViewById(R.id.botaoDeletar);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.deletaRegistro(Integer.parseInt(codigo));
                Intent intent = new Intent(Alterar.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
