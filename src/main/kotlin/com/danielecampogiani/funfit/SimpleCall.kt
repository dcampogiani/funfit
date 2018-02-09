package com.danielecampogiani.funfit

import okhttp3.Request
import retrofit2.Callback

class SimpleCall<A>(
        private val retrofitCall: retrofit2.Call<A>
) : Call<A> {

    override fun cancel() = retrofitCall.cancel()

    override fun clone() = SimpleCall(retrofitCall.clone())

    override fun execute(): Response<A> {
        val response = retrofitCall.execute()
        return buildResponse(response)
    }

    override fun executeAsync(onResponse: (Call<A>, Response<A>) -> Unit, onFailure: (Call<A>, Throwable) -> Unit) {
        retrofitCall.enqueue(object : Callback<A> {

            override fun onResponse(call: retrofit2.Call<A>, response: retrofit2.Response<A>) {
                onResponse(this@SimpleCall, buildResponse(response))
            }

            override fun onFailure(call: retrofit2.Call<A>, t: Throwable) {
                onFailure(this@SimpleCall, t)
            }
        })
    }

    override val cancelled: Boolean
        get() = retrofitCall.isCanceled

    override val executed: Boolean
        get() = retrofitCall.isExecuted

    override val request: Request
        get() = retrofitCall.request()

    private fun buildResponse(response: retrofit2.Response<A>): Response<A> {
        return if (response.isSuccessful) {
            Response.Success(response.body()!!, response.code(), response.headers(), response.message(), response.raw())
        } else {
            Response.Error(response.errorBody()!!, response.code(), response.headers(), response.message(), response.raw())
        }
    }
}