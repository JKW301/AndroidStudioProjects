package com.example.android3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.reflect.Method

class MainActivityPhone : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_phone)
        var slug = intent.getStringExtra("slug")
        var url = "https://phone-specs-api.vercel.app/brands/"+slug

        var queue = Volley.newRequestQueue(this)
        var requete = StringRequest(
            Request.Method.GET,url,
            {
                contenu ->
                var phones = JSONObject(contenu).getJSONObject("data").getJSONArray("phones")
                var lphones = mutableListOf<Phone>()
                var i = 0
                for(i in 0 .. phones.length()-1){
                    var current = phones.getJSONObject(i)
                    lphones.add(
                        Phone(
                        current.getString("slug"),
                        current.getString("phone_name"),
                        current.getString("image")
                        )
                    )
                }
                findViewById<ListView>(R.id.lv_phones).adapter = PhoneAdapter(applicationContext,lphones)
            },
            {error ->
                Toast.makeText(applicationContext,error.message, Toast.LENGTH_LONG).show()
            })
                    queue.add(requete)

        var back = findViewById<Button>(R.id.btn_back)
        back.setOnClickListener {
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)}

    }

}