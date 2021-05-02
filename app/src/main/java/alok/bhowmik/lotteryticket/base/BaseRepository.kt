package alok.bhowmik.lotteryticket.base

import alok.bhowmik.lotteryticket.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {


    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (t: Throwable) {
                t.printStackTrace()
                when (t) {
                    is HttpException -> {
                        Resource.Failure(false, t.code(), t.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}