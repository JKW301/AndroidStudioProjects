package com.example.laravelapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(context: Context, users: MutableList<User>) : ArrayAdapter<User>(context, 0, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        }

        val currentUser = getItem(position)

        val nameTextView = listItemView!!.findViewById<TextView>(R.id.name)
        if (currentUser != null) {
            nameTextView.text = currentUser.name
        }

        val emailTextView = listItemView.findViewById<TextView>(R.id.email)
        if (currentUser != null) {
            emailTextView.text = currentUser.email
        }
        return listItemView
    }
}