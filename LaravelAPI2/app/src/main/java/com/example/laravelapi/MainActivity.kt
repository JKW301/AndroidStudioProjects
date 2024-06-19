package com.example.laravelapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.mdp)
        val loginButton = findViewById<Button>(R.id.login_btn)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.43.233/api/login"

            val jsonRequest = object: JsonObjectRequest(
                Request.Method.POST, url, JSONObject().put("email", email).put("password", password),
                Response.Listener<JSONObject> { response ->
                    Log.d("API Response", response.toString())
                    Toast.makeText(this, "Réponse de l'API : $response", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, HubActivity::class.java)
                    intent.putExtra("token", response.getString("token"))
                    startActivity(intent)

                },
                Response.ErrorListener { error ->
                    Log.e("API Error", error.toString())
                    Toast.makeText(this, "Erreur de l'API : ${error.toString()}", Toast.LENGTH_LONG).show()
                }) {
                override fun getHeaders(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Content-Type"] = "application/json; charset=UTF-8"
                    return params
                }
            }

            queue.add(jsonRequest)
        }
    }
}