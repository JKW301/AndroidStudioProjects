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

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b = findViewById<Button>(R.id.btn_clic_load_brand)
        b.setOnClickListener{
            var queue = Volley.newRequestQueue(applicationContext)
            var requete = StringRequest(
                Request.Method.GET,"https:///phone-specs-api.vercel.app/brands",
                { resultat ->
                    var jso_global = JSONObject(resultat)
                    var jsa_brands = jso_global.getJSONArray("data")
                    var list_brands = mutableListOf<Brand>()
                    var compteur = 0
                    for (compteur in 0 .. jsa_brands.length()-1){
                        var br = jsa_brands.getJSONObject(compteur)
                        var marque:Brand = Brand(br.getInt("brand_id"),
                                                br.getString("brand_name"),
                                                br.getString("brand_slug"),
                                                br.getInt("device_count"),
                                                br.getString("detail"))
                        list_brands.add(marque)
                    }

                    var lv = findViewById<ListView>(R.id.lv_brands)
                    var adap = BrandAdapter(applicationContext,list_brands)
                    lv.adapter = adap
                    lv.setOnItemClickListener{adapter,view,pos,id->
                        var ba = adapter.adapter as BrandAdapter
                        var item = ba.getItem(pos) as Brand
                        var i = Intent(this,MainActivityPhone::class.java)
                        i.putExtra("slug",item.slug)
                        startActivity(i)

                    }

                }, { error -> Toast.makeText(applicationContext,error.message,
                    Toast.LENGTH_SHORT).show()})
            queue.add(requete)

            var test1 = findViewById<Button>(R.id.btntest)
            test1.setOnClickListener {
                var i = Intent(this,MainActivityPhone::class.java)
                startActivity(i)}
        }

    }


}