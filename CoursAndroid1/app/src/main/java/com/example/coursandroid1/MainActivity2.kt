package com.example.coursandroid1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var str = intent.getStringExtra("nom")
        var disp = findViewById<TextView>(R.id.name_receiver)
        disp.text = str

        var sharedpref = getSharedPreferences("savedata", MODE_PRIVATE)
        var c = sharedpref.getString("valeur", "")
        //TODO: inialiser le contenu de l'editText avec la valeur stockée
        var ed = findViewById<EditText>(R.id.履歴書)
        ed.setText(c)


        var save = findViewById<Button>(R.id.セーブ)
        save.setOnClickListener{
            var e = findViewById<EditText>(R.id.履歴書)
            var e_content = e.text.toString()

            var sharedpref = getSharedPreferences("savedata", MODE_PRIVATE)
            var editeur = sharedpref.edit()
            editeur.putString("valeur", e_content)
            editeur.apply()
        }
        var clean = findViewById<Button>(R.id.消し)
    }
}
