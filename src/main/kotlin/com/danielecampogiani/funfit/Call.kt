package com.danielecampogiani.funfit

interface Call<A> {

    fun cancel()

    fun clone(): Call<A>

    fun execute(): Response<A>

    fun executeAsync(onResponse: (Call<A>, Response<A>) -> Unit, onFailure: (Call<A>, Throwable) -> Unit)

    val cancelled: Boolean

    val executed: Boolean

    val request: okhttp3.Request
}