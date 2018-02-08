package com.danielecampogiani.funfit

interface Call<A> {

    fun execute(): Response<A>

}