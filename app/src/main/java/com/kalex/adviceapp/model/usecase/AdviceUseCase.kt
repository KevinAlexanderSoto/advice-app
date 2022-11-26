package com.kalex.adviceapp.model.usecase

import com.kalex.adviceapp.common.Resource
import com.kalex.adviceapp.model.data.UserRetroApi
import com.kalex.adviceapp.model.data.dto.splip
import com.kalex.adviceapp.model.repository.AdviceRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AdviceUseCase @Inject constructor(
    private val adviceRepoImpl : UserRetroApi
) {

    operator fun invoke() : Flow <Resource<splip>> = flow {
        try {
            emit(Resource.Loading<splip>())

            val response  = adviceRepoImpl.getAdvice()

            emit(Resource.Success<splip>(response))

        }catch (E : IOException){
            emit(Resource.Error<splip>("Error de internet"))
        }catch (E : HttpException){
            emit(Resource.Error<splip>("Server error"))
        }
    }
}

