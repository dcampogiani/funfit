package com.danielecampogiani.funfit

class FlatMapCall<A, B>(
        private val originalCall: Call<A>,
        private val f: (A) -> Call<B>
) : Call<B> {

    override fun execute(): Response<B> {
        val response = originalCall.execute()
        return when (response) {
            is Response.Error -> Response.Error(response.errorBody, response.code, response.headers, response.message, response.raw)
            is Response.Success -> f(response.body).execute()
        }
    }
}