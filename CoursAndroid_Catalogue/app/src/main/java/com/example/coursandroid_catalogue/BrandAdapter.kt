package com.example.coursandroid_catalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

class BrandAdapter: BaseAdapter {

    var context : Context
    var lbrand : MutableList<Brand>

    constructor(context: Context, lbrand: MutableList<Brand>) : super() {
        this.context = context
        this.lbrand = lbrand
    }


    override fun getCount(): Int {
        return this.lbrand.size
    }

    override fun getItem(position: Int): Any {
        return this.lbrand.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v:View
        if(convertView == null) {
            var inflater = LayoutInflater.from(this.context)
            v = inflater.inflate(R.layout.row_brand,null)
        } else {
            v = convertView
        }

        var current = getItem(position) as Brand
        var tv_n = v.findViewById<TextView>(R.id.tv_name)
        tv_n.text = current.name

        var tv_count = v.findViewById<TextView>(R.id.tv_count)
        tv_count.text = current.device_count.toString()

        return v
    }

}