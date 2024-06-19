package com.example.laravelapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

import android.widget.ListView
import android.widget.Toast


class SecondActivity : AppCompatActivity() {
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val token = intent.getStringExtra("token")
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.43.233/api/user"
        val userList = mutableListOf<User>()
        val adapter = UserAdapter(this, userList)
        val listView = findViewById<ListView>(R.id.user_list_view)
        listView.adapter = adapter
        val jsonRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                Log.d("API Response", response.toString())
                Toast.makeText(this, "Réponse de l'API : $response", Toast.LENGTH_LONG).show()

                val user = User(
                    id = response.getInt("id"),
                    name = response.getString("name"),
                    email = response.getString("email")
                )
                userId = user.id
                userList.add(user)
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
        queue.add(jsonRequest)
        val updateButton = findViewById<Button>(R.id.update_button)
        val editName = findViewById<EditText>(R.id.edit_name)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        updateButton.setOnClickListener {
            val newName = editName.text.toString()
            val newEmail = editEmail.text.toString()
            val url = "http://192.168.43.233/api/users/$userId"
            val jsonRequest = object : JsonObjectRequest(
                Request.Method.PUT, url, JSONObject().put("name", newName).put("email", newEmail),
                Response.Listener<JSONObject> { response ->
                    Log.d("API Response", response.toString())
                    Toast.makeText(this, "Réponse de l'API : $response", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    Log.e("API Error", error.toString())
                    Toast.makeText(this, "Erreur de l'API : ${error.toString()}", Toast.LENGTH_LONG)
                        .show()
                }) {
                override fun getHeaders(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["Content-Type"] = "application/json; charset=UTF-8"
                    params["Authorization"] = "Bearer $token"
                    return params
                }
            }
            queue.add(jsonRequest)
        }
    }
}