package com.bertosoft.incidencias.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject

class AdminDbHelper (
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) :
    SQLiteOpenHelper(
        context, DATABASE_NAME,
        factory, DATABASE_VERSION
    ) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Incidencias.db"
    }

    private var strSql = ""

    //
    //metodo para crear la base de datos
    //
    override fun onCreate(db: SQLiteDatabase) {
        strSql = "CREATE TABLE IF NOT EXISTS Incidencias (_id INTEGER PRIMARY KEY, " +
                "Fecha TEXT, " +
                "Hed TEXT, " +
                "Hen TEXT, " +
                "Hef TEXT, " +
                "Voladuras TEXT)"

        db.execSQL(strSql)
    }

    //
    // Metodo onUpgrade
    //
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(/* sql = */ "DROP TABLE IF EXISTS Incidencias")
        onCreate(db)
    }
}