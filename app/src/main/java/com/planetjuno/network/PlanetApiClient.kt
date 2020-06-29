package com.planetjuno.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PlanetApiClient {

    companion object {

        private var retrofit: Retrofit? = null

        var logging = HttpLoggingInterceptor()
        var httpClient = OkHttpClient.Builder()

        fun getClient(): Retrofit? {

            httpClient.addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            }

            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(30, TimeUnit.SECONDS)
            httpClient.addInterceptor(logging)


            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://api.nasa.gov/planetary/apod/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit
        }

        fun clearRetrofit() {
            retrofit = null
        }
    }
}