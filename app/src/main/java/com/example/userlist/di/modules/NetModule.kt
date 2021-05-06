package com.example.userlist.di.modules

import com.example.userlist.BuildConfig
import com.example.userlist.data.retrofit.UsersApi
import com.example.userlist.util.Constants.QUERY_TOKEN
import com.example.userlist.util.Constants.TOKEN_INTERCEPTOR
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
class NetModule {

    @Provides
    @Named(TOKEN_INTERCEPTOR)
    fun provideTokenInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        request.url().newBuilder()
            .addQueryParameter(QUERY_TOKEN, BuildConfig.API_TOKEN)
            .build().let {
                chain.proceed(request.newBuilder().url(it).build())
            }
    }

    @Provides
    fun provideClient(
        @Named(TOKEN_INTERCEPTOR) tokenInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}