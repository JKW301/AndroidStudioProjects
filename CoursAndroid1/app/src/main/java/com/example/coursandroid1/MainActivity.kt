package com.example.coursandroid1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var klick = findViewById<Button>(R.id.KLICK)
        klick.setOnClickListener {
            Toast.makeText(applicationContext, "Hallo !", Toast.LENGTH_SHORT).show()
        }

        var mich = findViewById<Button>(R.id.mich)
        mich.setOnClickListener {
            Toast.makeText(applicationContext, "Hallo !", Toast.LENGTH_SHORT).show()
        }

        var popup = AlertDialog.Builder(this)
        popup.setTitle(getString(R.string.pop_title))
        popup.setMessage(getString(R.string.pop_content))
        popup.setPositiveButton(getString(R.string.pop_oui)){dialog, which ->
            finish() //pour fermer l'activity courant : donc le Main
        }
        popup.setNegativeButton(getString(R.string.pop_non)){dialog, which ->
            dialog.dismiss()
        }
        popup.setCancelable(false)
        popup.show()

        var clique = findViewById<Button>(R.id.clique)
        clique.setOnClickListener {
            var i = Intent(this, MainActivity2::class.java)

            var edt_n = findViewById<EditText>(R.id.名前)
            var contenu:String = edt_n.text.toString()
            if(contenu > "") { //ou if(contenu.length>0)
                startActivity(i)
                Toast.makeText(applicationContext, "ようこそ $contenu", Toast.LENGTH_SHORT).show()
            } else {
                edt_n.error="Nom requis"
                Toast.makeText(applicationContext, "Erreur : champs vide !", Toast.LENGTH_SHORT).show()
            }
        }

        var ボタン = findViewById<Button>(R.id.ボタン)
        ボタン.setOnClickListener{
            var edt_n = findViewById<EditText>(R.id.名前)
            var contenu:String = edt_n.text.toString()
            var i = Intent(this,MainActivity2::class.java)
            i.putExtra("nom", contenu)
            startActivity(i)
        }
    }
}
