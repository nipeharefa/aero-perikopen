package dev.nias.perikopen.model

data class HomeEvent(var name: String) {
    lateinit var id : String
    var perikopens: MutableList<Perikopen> = mutableListOf()
}