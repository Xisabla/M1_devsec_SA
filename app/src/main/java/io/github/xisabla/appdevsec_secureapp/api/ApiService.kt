package io.github.xisabla.appdevsec_secureapp.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("accounts")
    fun getAccountInformation(): Call<List<AccountsByID>>
}