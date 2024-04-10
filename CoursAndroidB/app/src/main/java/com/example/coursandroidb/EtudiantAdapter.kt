package com.example.coursandroidb

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class EtudiantAdapter:BaseAdapter {

    var context:Context?=null
    var listEtu:MutableList<Etudiant>

    constructor(context: Context?, listEtu:MutableList<Etudiant>):super() {
        this.context = context
        this.listEtu = listEtu
    }

    constructor():super(){
        this.listEtu = mutableListOf()
    }

    override fun getCount(): Int {
        return this.listEtu.size
    }

    override fun getItem(position: Int): Any {
        return this.listEtu.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

}