package com.kalex.adviceapp.model.repository

import com.kalex.adviceapp.model.data.dto.splip

interface AdviceRepository {
    suspend fun getAdvice() : splip
}
