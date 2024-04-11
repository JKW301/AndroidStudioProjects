package com.example.cous_android_d

class Burger {
    var id = -1
    var nom = ""
    var prix = 0
    var description = ""

    constructor(id: Int, nom: String, prix: Int, description: String) {
        this.id = id
        this.nom = nom
        this.prix = prix
        this.description = description
    }
}