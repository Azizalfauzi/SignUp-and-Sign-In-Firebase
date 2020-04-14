package org.d3ifcool.crudfirebase

class Hero(
    val id: String,
    val nama: String,
    val email: String,
    val nim: String,
    val kelas: String
) {
    constructor() : this("", "", "", "", "") {

    }
}