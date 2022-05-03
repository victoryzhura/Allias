package com.example.allias.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://random-word-api.herokuapp.com/"

enum class WordListLang (val value: String) {
    CHOOSE_EN ("en"),
    CHOOSE_ES ("es"),
    CHOOSE_ZH ("zh"),
    CHOOSE_DE ("de")
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WordListService {
    @GET("all")
    fun getWordListAsync(@Query ("lang") type: String): Deferred<List<String>>
}

object WordListApi {
    val retrofitService: WordListService by lazy {
        retrofit.create(WordListService::class.java)
    }
}