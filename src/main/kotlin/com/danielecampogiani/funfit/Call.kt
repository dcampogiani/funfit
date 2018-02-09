package com.danielecampogiani.funfit

interface Call<A> {

    fun cancel()

    fun clone(): Call<A>

    fun execute(): Response<A>

    val cancelled: Boolean

    val executed: Boolean

    val request: okhttp3.Request
}