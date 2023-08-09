package com.kalex.adviceapp.di

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kalex.adviceapp.model.data.UserRetroApi
import com.kalex.adviceapp.model.repository.AdviceRepository
import com.kalex.adviceapp.model.repository.AdviceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun retrofitProvide(): UserRetroApi {
        return Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserRetroApi::class.java)
    }

    @Provides
    @Singleton
    fun userRepositoryProvide(api: UserRetroApi): AdviceRepository {
        return AdviceRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthorizationIntent(context: Context): Intent {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://keycloak.sitbogota.com/auth/realms/smm-qa-env/protocol/openid-connect/auth"), // authorization endpoint
            Uri.parse(""), // token endpoint
        )

        val clientId = "smm-qa-test"
        val redirectUri = Uri.parse("com.kalex.adviceapp:/oauth2redirect")
        val builder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri,
        )
        builder.setScopes("openid profile email")

        val authRequest = builder.build()

        val authService = AuthorizationService(context)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        return authIntent
    }
}
