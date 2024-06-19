package com.example.laravelapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class HubActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var eventId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)
        val token = intent.getStringExtra("token")

        val see_profile = findViewById<Button>(R.id.profile)
        see_profile.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        val nfc_page = findViewById<Button>(R.id.nfc_btn)
        nfc_page.setOnClickListener {
            val intent = Intent(this, nfcActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }


        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.43.233/api/events"
        val eventList = mutableListOf<Event>()
        val adapter = EventAdapter(this, eventList)
        val listView = findViewById<ListView>(R.id.event_list_view)
        listView.adapter = adapter
        val jsonArrayRequest = object: JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                Log.d("API Response", response.toString())
                Toast.makeText(this, "Réponse de l'API : $response", Toast.LENGTH_LONG).show()
                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i)
                    val event = Event(
                        id = item.getInt("id"),
                        title_id = item.getString("title_id"),
                        start_date = item.getString("start_date"),
                        end_date = item.getString("end_date")
                    )
                    eventList.add(event)
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.e("API Error", error.toString())
                Toast.makeText(this, "Erreur de l'API : ${error.toString()}", Toast.LENGTH_LONG).show()
            }) {
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json; charset=UTF-8"
                params["Authorization"] = "Bearer $token"
                return params
            }
        }
        queue.add(jsonArrayRequest)
    }
}

