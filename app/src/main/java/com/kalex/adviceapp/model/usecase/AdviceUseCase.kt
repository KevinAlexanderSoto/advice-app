package com.kalex.adviceapp.model.usecase

import com.kalex.adviceapp.common.Resource
import com.kalex.adviceapp.model.data.UserRetroApi
import com.kalex.adviceapp.model.data.dto.Splip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AdviceUseCase @Inject constructor(
    private val adviceRepoImpl: UserRetroApi,
) {

    operator fun invoke(): Flow<Resource<Splip>> = flow {
        try {
            emit(Resource.Loading<Splip>())

            val response = adviceRepoImpl.getAdvice()

            emit(Resource.Success<Splip>(response))
        } catch (E: IOException) {
            emit(Resource.Error<Splip>("Error de internet"))
        } catch (E: HttpException) {
            emit(Resource.Error<Splip>("Server error"))
        }
    }
}
