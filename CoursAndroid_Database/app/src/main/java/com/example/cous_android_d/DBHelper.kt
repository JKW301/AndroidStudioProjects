package com.example.cous_android_d

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context:Context, factory: SQLiteDatabase.CursorFactory) :
    SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, prix INTEGER, description TEXT);")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //onUpgrade ne fonctionne que s'il y a déjà une bdd
        db.execSQL("DROP TABLE " + TABLE_NAME)
        onCreate(db)
    }

    companion object{
        private DB_NAME = "FASTFOODBDD"
        private val DB_VERSION = 1
        private val TABLE_NAME = "BURGER"
    }

    fun insertBurger(burger:Burger):Long{
        var cv = ContentValues() //c'est un tableau associatif
        cv.put("nom", burger.nom)
        cv.put("prix", burger.prix)
        cv.put("description", burger.description)

        var db = this.writableDatabase

        db.insert(TABLE_NAME, null, cv)

        db.close()
    }
}