package com.example.myapplication.todo.data

import android.util.Log
import com.example.myapplication.core.TAG
import com.example.myapplication.todo.data.remote.ItemApi

object ItemRepository {
    private var cachedItems: MutableList<Song>? = null;

    suspend fun loadAll(): List<Song> {
        Log.i(TAG, "loadAll")
        if (cachedItems != null) {
            return cachedItems as List<Song>;
        }
        cachedItems = mutableListOf()
        val items = ItemApi.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Song>
    }

    suspend fun load(itemId: String): Song {
        Log.i(TAG, "load")
        val item = cachedItems?.find { it.id == itemId }
        if (item != null) {
            return item
        }
        return ItemApi.service.read(itemId)
    }

    suspend fun save(song: Song): Song {
        Log.i(TAG, "save")
        val createdItem = ItemApi.service.create(song)
        Log.i(TAG, song.name + "exists")
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(song: Song): Song {
        Log.i(TAG, "update")
        val updatedSong = ItemApi.service.update(song.id, song)
        val index = cachedItems?.indexOfFirst { it.id == song.id }
        if (index != null) {
            cachedItems?.set(index, updatedSong)
        }
        return updatedSong
    }
}