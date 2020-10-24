package com.example.myapplication

import android.util.Log

object ItemRepository {
    suspend fun getAll(): List<Song> {
        Log.i(TAG, "getAll");
        return ItemApi.service.getAll()
    }
}