package com.danielecampogiani.funfit

class SimpleCall<A>(
        private val retrofitCall: retrofit2.Call<A>
) : Call<A> {

    override fun execute(): Response<A> {
        val response = retrofitCall.execute()
        return if (response.isSuccessful) {
            Response.Success(response.body()!!, response.code(), response.headers(), response.message(), response.raw())
        } else {
            Response.Error(response.errorBody()!!, response.code(), response.headers(), response.message(), response.raw())
        }
    }


}