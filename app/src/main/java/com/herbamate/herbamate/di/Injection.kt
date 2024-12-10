package com.herbamate.herbamate.di

import android.content.Context
import com.herbamate.herbamate.data.local.room.FavoriteDatabase
import com.herbamate.herbamate.data.remote.HerbAPI
import com.herbamate.herbamate.data.remote.RecommendationAPI
import com.herbamate.herbamate.repository.FavoriteRepository
import com.herbamate.herbamate.repository.HerbRepository
import com.herbamate.herbamate.repository.RecommendationRepository
import com.herbamate.herbamate.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injection {
    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favoriteDao()

        val appExecutors = AppExecutors()

        return FavoriteRepository.getInstance(dao, appExecutors)
    }

    private fun providesHerbApi () : HerbAPI {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .build()
            chain.proceed(requestHeader)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://herbamate-api-664546579058.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(HerbAPI::class.java)
    }

    private fun providesRecommendationApi () : RecommendationAPI {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .build()
            chain.proceed(requestHeader)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://herbamate-recommed-664546579058.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RecommendationAPI::class.java)
    }

    fun providesRecommendationRepository () : RecommendationRepository {
        return RecommendationRepository(providesRecommendationApi())
    }

    fun providesHerbRepository () : HerbRepository {
        return HerbRepository(providesHerbApi())
    }
}