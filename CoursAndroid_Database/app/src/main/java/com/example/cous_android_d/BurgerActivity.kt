package com.example.cous_android_d

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText


class BurgerActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_burger)

        var b_save = findViewById<Button>(R.id.rec_burger)

        var b_nom = findViewById<EditText>(R.id.edt_burger_nom)
        var b_prix = Integer.parseInt(findViewById<EditText>(R.id.edt_burger_prix).toString());

        var b_desc = findViewById<EditText>(R.id.edt_burger_desc)


        b_save.setOnClickListener{
            var b = Burger(id = , nom = b_nom, prix = b_prix, description = b_desc)

            var bdd = DBHelper(this, null)
            bdd.insertBurger(b)
        }


    }
}
