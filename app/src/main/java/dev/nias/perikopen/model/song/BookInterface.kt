package dev.nias.perikopen.model.song

interface BookInterface {
    var songs : MutableList<ISong>
    fun getBookName() : String
}