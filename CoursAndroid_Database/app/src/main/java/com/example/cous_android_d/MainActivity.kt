package com.example.cous_android_d

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addBurger = findViewById<Button>(R.id.btn_add_Burger)
        addBurger.setOnClickListener{
            var i = Intent(this, BurgerActivity::class.java)
            startActivity(i)
        }
    }
}