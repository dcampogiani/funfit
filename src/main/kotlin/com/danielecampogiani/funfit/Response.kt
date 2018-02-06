package com.danielecampogiani.funfit

import okhttp3.Headers


sealed class Response<out T>(
        open val code: Int,
        open val headers: okhttp3.Headers,
        open val message: String,
        open val raw: okhttp3.Response
) {

    fun <R> map(f: (T) -> R): Response<R> {
        return when (this) {
            is Response.Success -> Response.Success(f(body), code, headers, message, raw)
            is Response.Error -> Response.Error(errorBody, code, headers, message, raw)
        }
    }

    data class Success<T>(
            val body: T,
            override val code: Int,
            override val headers: Headers,
            override val message: String,
            override val raw: okhttp3.Response
    ) : Response<T>(code, headers, message, raw)

    data class Error<T>(
            val errorBody: okhttp3.ResponseBody,
            override val code: Int,
            override val headers: Headers,
            override val message: String,
            override val raw: okhttp3.Response
    ) : Response<T>(code, headers, message, raw)
}