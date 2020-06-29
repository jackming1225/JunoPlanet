package com.planetjuno.network

import com.planetjuno.model.ResponsePlanet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetApiInterface {

    @GET("?api_key=DEMO_KEY")
    fun getPlanetData(@Query("date") date: String?): Call<ResponsePlanet>

    @GET("withdrawal/limits/")
    fun getPlanetDataForDate(): Call<ResponsePlanet>

    /**
     * Companion object to create the Rupeek Quick ApiService
     */
    companion object Factory {
        fun create(): PlanetApiInterface? {
            return PlanetApiClient.getClient()?.create(PlanetApiInterface::class.java)
        }

    }
}