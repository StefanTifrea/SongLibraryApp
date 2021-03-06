package com.example.myapplication.todo.data.remote

import com.example.myapplication.todo.data.Song
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ItemApi {
    private const val URL = "http://192.168.0.52:3000/"

    interface Service {
        @GET("/item")
        suspend fun find(): List<Song>

        @GET("/item/{id}")
        suspend fun read(@Path("id") itemId: String): Song;

        @Headers("Content-Type: application/json")
        @POST("/item")
        suspend fun create(@Body song: Song): Song

        @Headers("Content-Type: application/json")
        @PUT("/item/{id}")
        suspend fun update(@Path("id") itemId: String, @Body song: Song): Song
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}