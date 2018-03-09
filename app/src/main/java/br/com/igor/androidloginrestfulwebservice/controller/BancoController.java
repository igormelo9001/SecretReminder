package br.com.igor.androidloginrestfulwebservice.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by igor on 08/03/2018.
 */

public class BancoController {

    private SQLiteDatabase db;
    private Banco banco;

    public BancoController(Context context){
        banco = new Banco(context);
    }

    public String insereDado(String url_site, String usuario, String senha){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Banco.URL_SITE, url_site);
        valores.put(Banco.USUARIO, usuario);
        valores.put(Banco.SENHA, senha);

        resultado = db.insert(Banco.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.URL_SITE};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.URL_SITE,banco.USUARIO,banco.SENHA};
        String where = Banco.ID + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(Banco.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, String url, String usuario, String senha){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = Banco.ID + "=" + id;

        valores = new ContentValues();
        valores.put(Banco.URL_SITE, url);
        valores.put(Banco.USUARIO, usuario);
        valores.put(Banco.SENHA, senha);

        db.update(Banco.TABELA,valores,where,null);
        db.close();
    }

    public void deletaRegistro(int id){
        String where = Banco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(Banco.TABELA,where,null);
        db.close();
    }

}
