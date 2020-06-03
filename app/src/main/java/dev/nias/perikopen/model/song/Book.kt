package dev.nias.perikopen.model.song

class Book(var name : String) : BookInterface {
    override lateinit var songs: MutableList<ISong>

    init {
        this.songs = mutableListOf()
    }

    override fun getBookName(): String {
        return name
    }
}