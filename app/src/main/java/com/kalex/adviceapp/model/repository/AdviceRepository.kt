package com.kalex.adviceapp.model.repository

import com.kalex.adviceapp.model.data.dto.Splip

interface AdviceRepository {
    suspend fun getAdvice(): Splip
}
