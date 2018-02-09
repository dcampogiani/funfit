package com.danielecampogiani.funfit

import okhttp3.Request

class FlatMapCall<A, B>(
        private val originalCall: Call<A>,
        private val f: (A) -> Call<B>
) : Call<B> {

    override fun cancel() = originalCall.cancel()

    override fun clone() = this

    override fun execute(): Response<B> {
        val response = originalCall.execute()

        return buildResponse(response)
    }

    override fun executeAsync(onResponse: (Call<B>, Response<B>) -> Unit, onFailure: (Call<B>, Throwable) -> Unit) {
        originalCall.executeAsync({ _, response ->
            onResponse(this, buildResponse(response))
        }, { _, throwable ->
            onFailure(this, throwable)
        })
    }

    private fun buildResponse(response: Response<A>): Response<B> {
        return response.fold(
                { Response.Error(it.errorBody, it.code, it.headers, it.message, it.raw) },
                { f(it.body).execute() })
    }

    override val cancelled: Boolean
        get() = originalCall.cancelled

    override val executed: Boolean
        get() = originalCall.executed

    override val request: Request
        get() = originalCall.request

}