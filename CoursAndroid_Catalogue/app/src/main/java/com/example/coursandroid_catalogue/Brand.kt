package com.example.coursandroid_catalogue

class Brand {
    var id : Int = 0;
    var name : String = ""
    var slug : String = ""
    var device_count : Int = 0
    var detail = ""

    constructor(id: Int, name: String, slug: String, device_count: Int, detail: String) {
        this.id = id
        this.name = name
        this.slug = slug
        this.device_count = device_count
        this.detail = detail
    }
}