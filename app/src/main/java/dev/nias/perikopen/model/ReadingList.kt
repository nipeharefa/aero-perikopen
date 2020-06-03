package dev.nias.perikopen.model

data class ReadingList(var id : String) {
    var bookName: String = ""
    var bookValue : String = ""
    var displayOrder: Number = 0
}