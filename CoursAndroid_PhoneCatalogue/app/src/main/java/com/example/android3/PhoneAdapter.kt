package com.example.android3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PhoneAdapter: BaseAdapter {

    var context:Context
    var lphone:MutableList<Phone>

    constructor(context: Context, lphone: MutableList<Phone>) : super() {
        this.context = context
        this.lphone = lphone
    }

    override fun getCount(): Int {
        return this.lphone.size
    }

    override fun getItem(position: Int): Any {
        return this.lphone.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v:View
        if(convertView==null){
            var inflater = LayoutInflater.from(this.context)
            v = inflater.inflate(R.layout.row_phone,null)
        } else {
            v = convertView
        }
        var current_phone = getItem(position) as Phone
        v.findViewById<TextView>(R.id.tv_phone_name).setText(current_phone.phone_name)

        var imagev = v.findViewById<ImageView>(R.id.imgv_phone)

        Picasso.get().load(current_phone.phone_image).into(imagev)


        return v
    }
}