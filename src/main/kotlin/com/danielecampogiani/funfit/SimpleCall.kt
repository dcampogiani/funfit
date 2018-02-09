package com.danielecampogiani.funfit

import okhttp3.Request

class SimpleCall<A>(
        private val retrofitCall: retrofit2.Call<A>
) : Call<A> {

    override fun cancel() = retrofitCall.cancel()

    override fun clone() = SimpleCall(retrofitCall.clone())

    override fun execute(): Response<A> {
        val response = retrofitCall.execute()
        return if (response.isSuccessful) {
            Response.Success(response.body()!!, response.code(), response.headers(), response.message(), response.raw())
        } else {
            Response.Error(response.errorBody()!!, response.code(), response.headers(), response.message(), response.raw())
        }
    }

    override val cancelled: Boolean
        get() = retrofitCall.isCanceled

    override val executed: Boolean
        get() = retrofitCall.isExecuted

    override val request: Request
        get() = retrofitCall.request()

}