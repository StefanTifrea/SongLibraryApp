package com.example.myapplication.todo.data

import java.util.*

data class Song(
    val id: String,
    var name: String,
    var artist: String,
    var time: Int,
    var releaseDate: Date
) {
    override fun toString(): String = "$artist - $name : $time \nRelease on $releaseDate"
}