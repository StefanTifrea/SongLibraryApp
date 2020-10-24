package com.example.myapplication

data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val time: Int
) {
    override fun toString(): String = "$artist - $name : $time"
}