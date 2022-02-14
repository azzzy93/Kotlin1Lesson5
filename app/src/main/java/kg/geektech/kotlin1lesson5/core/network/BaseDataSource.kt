package kg.geektech.kotlin1lesson5.core.network

import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {

        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) return Resource.success(body)
            } else {
                return Resource.error(response.message(), response.body(), response.code())
            }
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null, 429)
        }

        return Resource.error(null, null, 429)
    }
}