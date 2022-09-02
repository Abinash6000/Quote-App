package com.example.quote

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Created a RequestManager Class
class RequestManager(mContext: Context) {
    // initializing the object of retrofit
    var retrofit = Retrofit.Builder()
        .baseUrl("https://type.fit/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun GetAllQuotes(listener: QuotesResponseListener){
        val call = retrofit.create(CallQuotes::class.java).callQuotes()
        call.enqueue(object : Callback<List<QuotesResponse>>{
            override fun onResponse(
                call: Call<List<QuotesResponse>>,
                response: Response<List<QuotesResponse>>
            ) {
                if (!response.isSuccessful){
                    listener.didError(response.message())
                    return
                }
                response.body()?.let { listener.didFetch(it, response.message()) }
            }

            override fun onFailure(call: Call<List<QuotesResponse>>, t: Throwable) {
                t.message?.let { listener.didError(it) }
            }

        })
    }

    private interface  CallQuotes {
        @GET("api/quotes")
        fun callQuotes(): Call<List<QuotesResponse>>
    }
}