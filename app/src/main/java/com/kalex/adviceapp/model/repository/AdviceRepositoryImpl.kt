package com.kalex.adviceapp.model.repository

import com.kalex.adviceapp.model.data.UserRetroApi
import com.kalex.adviceapp.model.data.dto.Splip
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor(
    private val api: UserRetroApi
) : AdviceRepository {

    override suspend fun getAdvice(): Splip {
        return api.getAdvice()
    }
}