package com.example.news.dependencyinjection

import android.util.Log
import com.example.news.api.NewsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LangInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient) = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build()

    @Singleton
    @Provides
    fun provideBaseUrl() = "https://newsapi.org/v2/"

    @Singleton
    @Provides
    fun provideConvertFactory() = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @LangInterceptor langInterceptor: Interceptor,
        @AuthInterceptor authInterceptor: Interceptor) : OkHttpClient{
            return OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(langInterceptor)
                .addInterceptor(authInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("OkHttp", message)
            }
        }
    ).setLevel(HttpLoggingInterceptor.Level.BODY)

    @LangInterceptor
    @Singleton
    @Provides
    fun ProvideLangInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url =
                request
                    .url
                    .newBuilder()
                    .addQueryParameter("language", "en")
                    .build()
            request = request
                .newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }

    @AuthInterceptor
    @Singleton
    @Provides
    fun ProvideAuthInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val url =
                request
                    .url
                    .newBuilder()
                    .addQueryParameter("apiKey", "37ab926cc6a64fce98bc8fd614fc39dd")
                    .build()
            request = request
                .newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }
}