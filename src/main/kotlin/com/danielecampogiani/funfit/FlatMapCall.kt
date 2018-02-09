package com.danielecampogiani.funfit

class FlatMapCall<A, B>(
        private val originalCall: Call<A>,
        private val f: (A) -> Call<B>
) : Call<B> {

    override fun execute(): Response<B> {
        val response = originalCall.execute()

        return response.fold(
                { Response.Error(it.errorBody, it.code, it.headers, it.message, it.raw) },
                { f(it.body).execute() }
        )
    }
}