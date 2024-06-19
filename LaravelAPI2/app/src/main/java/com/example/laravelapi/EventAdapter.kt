package com.example.laravelapi

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.Locale

class EventAdapter(context: Context, events: MutableList<Event>) : ArrayAdapter<Event>(context, 0, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false)
        }

        val currentEvent = getItem(position)

        val idTextView = listItemView!!.findViewById<TextView>(R.id.id)
        if (currentEvent != null) {
            idTextView.text = currentEvent.id.toString()
        }

        val title_idTextView = listItemView!!.findViewById<TextView>(R.id.title_id)
        if (currentEvent != null) {
            title_idTextView.text = currentEvent.title_id
        }

        val start_dateTextView = listItemView!!.findViewById<TextView>(R.id.start_date)
        if (currentEvent != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val startDate = inputFormat.parse(currentEvent.start_date)
            val formattedStartDate = outputFormat.format(startDate)
            start_dateTextView.text = formattedStartDate
        }

        val end_dateTextView = listItemView.findViewById<TextView>(R.id.end_date)
        if (currentEvent != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val endDate = inputFormat.parse(currentEvent.end_date)
            val formattedEndDate = outputFormat.format(endDate)
            end_dateTextView.text = formattedEndDate
        }
        return listItemView
    }
}