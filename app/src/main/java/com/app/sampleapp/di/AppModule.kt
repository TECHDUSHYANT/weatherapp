package com.app.sampleapp.di

import android.content.Context
import com.app.sampleapp.data.WeatherApis
import com.app.sampleapp.repository.AuthRepository
import com.app.sampleapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    fun provideAuthRepository() = AuthRepository()


    @Provides
    @Singleton
    fun provideApis(): WeatherApis {
        return Retrofit.Builder()
            .baseUrl("https://weatherbit-v1-mashape.p.rapidapi.com/forecast/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApis::class.java)
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
             readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
    }.build()



}