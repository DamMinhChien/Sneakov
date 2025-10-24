package com.magento.sneakov.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.magento.sneakov.data.local.DataStoreManager
import com.magento.sneakov.data.remote.api.SneakovApiService
import com.magento.sneakov.data.respository.AuthRepositoryImpl
import com.magento.sneakov.domain.respository.AuthRepository
import com.magento.sneakov.domain.usecase.LoginUseCase
import com.magento.sneakov.presentation.ui.screen.auth.LoginViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
private val BASE_URL = "https://overcontented-berniece-congressionally.ngrok-free.dev" + "/rest/all/V1/"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
val appModule = module {
    // ⚙️ DataStore
    single<DataStore<Preferences>> {
        val context: Context = get()
        context.dataStore
    }

    // ⚙️ DataStoreManager
    single { DataStoreManager(get()) }

    // Logging
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Moshi
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get())) // <-- inject Moshi
            .client(get()) // <-- inject OkHttpClient
            .build()
    }

    // ApiService
    single<SneakovApiService> {
        get<Retrofit>().create(SneakovApiService::class.java)
    }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    // UseCase
    factory{ LoginUseCase(get(), get()) }

    // ViewModel
    viewModel { LoginViewModel(get()) }

}