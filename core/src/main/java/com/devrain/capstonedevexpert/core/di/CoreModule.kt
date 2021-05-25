package com.devrain.capstonedevexpert.core.di

import androidx.room.Room
import com.devrain.capstonedevexpert.core.data.source.MovieRepository
import com.devrain.capstonedevexpert.core.data.source.local.LocalDataSource
import com.devrain.capstonedevexpert.core.data.source.local.entity.MovieDatabase
import com.devrain.capstonedevexpert.core.data.source.remote.RemoteDataSource
import com.devrain.capstonedevexpert.core.data.source.remote.network.ApiService
import com.devrain.capstonedevexpert.core.domain.repository.IMovieRepository
import com.devrain.capstonedevexpert.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<com.devrain.capstonedevexpert.core.data.source.local.entity.MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            com.devrain.capstonedevexpert.core.data.source.local.entity.MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { com.devrain.capstonedevexpert.core.data.source.local.LocalDataSource(get()) }
    single { com.devrain.capstonedevexpert.core.data.source.remote.RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        com.devrain.capstonedevexpert.core.data.source.MovieRepository(
            get(),
            get(),
            get()
        )
    }
}