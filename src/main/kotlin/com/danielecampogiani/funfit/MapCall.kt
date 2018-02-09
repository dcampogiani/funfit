package com.danielecampogiani.funfit

import okhttp3.Request

class MapCall<A, B>(
        private val originalCall: Call<A>,
        private val f: (A) -> B) : Call<B> {

    override fun cancel() = originalCall.cancel()

    override fun clone() = this

    override fun execute(): Response<B> {
        val response = originalCall.execute()
        return response.map(f)
    }

    override fun executeAsync(onResponse: (Call<B>, Response<B>) -> Unit, onFailure: (Call<B>, Throwable) -> Unit) {
        originalCall.executeAsync({ _, response ->
            onResponse(this, response.map(f))
        }, { _, throwable ->
            onFailure(this, throwable)
        })
    }


    override val cancelled: Boolean
        get() = originalCall.cancelled

    override val executed: Boolean
        get() = originalCall.executed

    override val request: Request
        get() = originalCall.request
}