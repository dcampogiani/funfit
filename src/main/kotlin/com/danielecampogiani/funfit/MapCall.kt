package com.danielecampogiani.funfit

class MapCall<A, B>(
        private val originalCall: Call<A>,
        private val f: (A) -> B) : Call<B> {

    override fun execute(): Response<B> {
        val response = originalCall.execute()
        return response.map(f)
    }

}