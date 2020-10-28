package com.example.myapplication.todo.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.TAG
import com.example.myapplication.todo.data.ItemRepository
import com.example.myapplication.todo.data.Song
import kotlinx.coroutines.launch
import java.util.*

class ItemEditViewModel : ViewModel() {
    private val mutableSong = MutableLiveData<Song>().apply { value = Song("", "", "",100, Date()) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val item: LiveData<Song> = mutableSong
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadSong(songId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadItem...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableSong.value = ItemRepository.load(songId)
                Log.i(TAG, "loadItem succeeded")
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItem failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(name: String, artist: String, length: Int, date: Date) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem...");
            val song = mutableSong.value ?: return@launch
            song.name = name
            song.artist = artist
            song.time = length
            song.releaseDate = date
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (song.id.isNotEmpty()) {
                    mutableSong.value = ItemRepository.update(song)
                } else {
                    mutableSong.value = ItemRepository.save(song)
                }
                Log.i(TAG, "saveOrUpdateItem succeeded");
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateItem failed", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }
}