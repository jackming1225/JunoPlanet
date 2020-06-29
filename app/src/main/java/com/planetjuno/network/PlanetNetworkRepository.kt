package com.planetjuno.network

import com.planetjuno.model.ResponsePlanet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PlanetNetworkRepository {

    /**
     *  Fetch Renewal Schemes
     */
    fun fetchPlanetData(
        success: (Response<ResponsePlanet>) -> Unit,
        error: (Response<ResponsePlanet>) -> Unit,
        failure: (t: Throwable) -> Unit, date: String?
    ) {
        val planetApiInterface: PlanetApiInterface? = PlanetApiInterface.create()
        val call = planetApiInterface?.getPlanetData(date)

        call?.enqueue(object : Callback<ResponsePlanet> {
            override fun onResponse(
                call: Call<ResponsePlanet>,
                response: Response<ResponsePlanet>
            ) {
                when {
                    response.isSuccessful -> success(response)
                    else -> error(response)

                }
            }

            override fun onFailure(call: Call<ResponsePlanet>, t: Throwable) {
                failure(t)
            }
        })
    }
}