package com.kalex.adviceapp.model.data

import com.kalex.adviceapp.model.data.dto.splip
import retrofit2.http.GET

interface UserRetroApi {

    @GET("advice")
    suspend fun getAdvice(
    ): splip

}
