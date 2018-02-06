package com.danielecampogiani.funfit

interface Call<T> {

    fun execute(): Response<T>

    fun <R> map(f: (T) -> R): Call<R>

    fun <R> flatMap(f: (T) -> Call<R>): Call<R>

}