package com.kalex.adviceapp.model.data

import com.kalex.adviceapp.model.data.dto.Splip
import retrofit2.http.GET

interface UserRetroApi {
    @GET("advice")
    suspend fun getAdvice(): Splip
}
