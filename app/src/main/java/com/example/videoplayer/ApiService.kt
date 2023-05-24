package com.example.videoplayer

import kotlinx.coroutines.internal.PrepareOp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/api/user/")
    fun register(@Body registrationData: RegistrationData): Call<DefaultResponse>

    @POST("/api/user/login/")
    fun login (@Body loginData: LoginData) : Call<DefaultResponse>

    @GET("/api/videos/")
    suspend fun getVideos(): List<Video>
}