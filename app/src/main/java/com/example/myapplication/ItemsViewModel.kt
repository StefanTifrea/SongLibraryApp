package com.example.myapplication

import java.util.ArrayList

object ItemsViewModel {
    val items: MutableList<Song> = ArrayList()

    private val COUNT = 100

    init {
        for (i in 1..COUNT) {
            addItem(createItem(i))
        }
    }

    private fun addItem(item: Song) {
        items.add(item)
    }

    private fun createItem(position: Int): Song {
        return Song(position.toString(), "Song $position", "Artist $position",  position);
    }
}