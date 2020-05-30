package dev.nias.perikopen.model

data class HomeEvent(var name: String) {
    var perikopens: MutableList<Perikopen> = mutableListOf()
}