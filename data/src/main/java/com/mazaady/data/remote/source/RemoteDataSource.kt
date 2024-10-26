package com.mazaady.data.remote.source

import com.mazaady.data.dto.BaseDto
import com.mazaady.data.dto.MainCatDto
import com.mazaady.data.dto.OptionChildDto
import com.mazaady.data.dto.PropertyDto
import com.mazaady.data.remote.service.ApiService
import com.mazaady.domain.exception.EmptyResponseException
import com.mazaady.domain.exception.InternalServerException
import com.mazaady.domain.exception.NoInternetException
import com.mazaady.domain.exception.ServerException
import com.mazaady.domain.exception.UnAuthException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) : IRemoteDataSource {
    override suspend fun getAllMainCats(): BaseDto<MainCatDto> {
        return wrapApiResponse {
            apiService.getAllMainCats()
        }
    }

    override suspend fun getProperties(catId: Int): BaseDto<List<PropertyDto>> {
        return wrapApiResponse {
            apiService.getProperties(catId)
        }
    }

    override suspend fun getOptionsChild(optionId: Int): BaseDto<List<OptionChildDto>> {
        return wrapApiResponse {
            apiService.getOptionsChild(optionId)
        }
    }

    private suspend fun <T> wrapApiResponse(
        request: suspend () -> Response<T>,
    ): T {
        try {
            val response = request()
            if (response.isSuccessful) {
                return response.body() ?: throw EmptyResponseException("No data")
            } else {
                throw when (response.code()) {

                    401 -> {
                        val jObjError = JSONObject(
                            response.errorBody()!!.string()
                        )
                        UnAuthException(jObjError.getString("msg"))
                    }

                    500 -> {
                        InternalServerException("Internal Server Error")
                    }

                    else -> ServerException("Server error")
                }
            }
        } catch (e: UnknownHostException) {
            throw NoInternetException("No Internet")
        } catch (e: HttpException) {
            throw NoInternetException("No Internet")
        } catch (io: IOException) {
            throw NoInternetException("No Internet")
        } catch (e: SocketTimeoutException) {
            throw NoInternetException("No Internet")
        } catch (e: SocketException) {
            throw NoInternetException("No Internet")
        }
    }

}