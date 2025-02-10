package com.example.kotlincoroutine.Api

import com.example.kotlincoroutine.Repo.ProductRepo
import com.example.kotlincoroutine.Repo.ProductRepoImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Singleton
    @Provides
    fun provideRetrofit(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            .build()
            .create(ApiInterface::class.java)
    }


    @Provides
    fun provideProductRepo(apiInterface: ApiInterface) : ProductRepo{
        return ProductRepoImpl(apiInterface = apiInterface)
    }


}