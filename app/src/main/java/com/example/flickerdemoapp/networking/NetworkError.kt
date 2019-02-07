package com.example.flickerdemoapp.networking

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class NetworkError(private val error: Throwable?):Throwable(error) {
    val isAuthFailure: Boolean
        get() = error is HttpException && error.code() == HttpURLConnection.HTTP_UNAUTHORIZED

    val isResponseNull: Boolean
        get() = error is HttpException && error.response() == null

    val appErrorMessage: String
        get() {
            if (this.error is IOException) return NETWORK_ERROR_MESSAGE
            if (this.error !is HttpException) return DEFAULT_ERROR_MESSAGE
            val response = this.error.response()
            if (response != null) {

                val headers = response.headers().toMultimap()
                if (headers.containsKey(ERROR_MESSAGE_HEADER))
                    return headers[ERROR_MESSAGE_HEADER]?.get(0)!!
            }

            return DEFAULT_ERROR_MESSAGE
        }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as NetworkError?

        return if (error != null) error == that!!.error else that!!.error == null

    }

    override fun hashCode(): Int {
        return error?.hashCode() ?: 0
    }

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
        const val NETWORK_ERROR_MESSAGE = "No Internet Connection!"
        private const val ERROR_MESSAGE_HEADER = "Error-Message"
    }
}