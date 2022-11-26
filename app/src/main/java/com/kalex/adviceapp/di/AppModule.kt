package com.kalex.adviceapp.di


import com.kalex.adviceapp.model.data.UserRetroApi
import com.kalex.adviceapp.model.repository.AdviceRepository
import com.kalex.adviceapp.model.repository.AdviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun retrofitProvide() : UserRetroApi {
        return Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserRetroApi::class.java)
    }

    @Provides
    @Singleton
    fun userRepositoryProvide(api : UserRetroApi): AdviceRepository {
        return AdviceRepositoryImpl(api)
    }
}