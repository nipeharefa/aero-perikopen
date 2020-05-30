package dev.nias.perikopen.model

import java.util.*

data class Perikopen(val id: String, val weekName: String) {
    var date: Date = Date()
}