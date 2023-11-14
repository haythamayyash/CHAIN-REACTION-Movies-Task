package com.chainreaction.task.data.di

import com.chainreaction.task.data.BuildConfig
import com.chainreaction.task.data.api.MovieApi
import com.chainreaction.task.data.interceptor.HeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideHeaderInterceptor() = HeaderInterceptor()

    @Provides
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideHttpClient(
        headerInterceptor: HeaderInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.MOVIE_BASE_URL)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}