package br.com.igor.androidloginrestfulwebservice.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by igor on 07/03/2018.
 */

public class Banco extends SQLiteOpenHelper {

    protected static final String NOME_BANCO = "banco.db";
    protected static final String TABELA = "logins";
    public static final String ID = "_id";
    public static final String URL_SITE = "url";
    public static final String USUARIO = "usuario";
    public static final String SENHA = "senha";
    private static final int VERSAO = 1;

    public Banco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"("
                + ID + " integer primary key autoincrement,"
                + URL_SITE + " text,"
                + USUARIO + " text,"
                + SENHA + " text"
                +")";

            db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
