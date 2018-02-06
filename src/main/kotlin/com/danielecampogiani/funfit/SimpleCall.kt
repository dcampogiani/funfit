package com.danielecampogiani.funfit

class SimpleCall<T>(
        private val retrofitCall: retrofit2.Call<T>
) : Call<T> {

    override fun execute(): Response<T> {
        val response = retrofitCall.execute()
        return if (response.isSuccessful) {
            Response.Success(response.body()!!, response.code(), response.headers(), response.message(), response.raw())
        } else {
            Response.Error(response.errorBody()!!, response.code(), response.headers(), response.message(), response.raw())
        }
    }

    override fun <R> map(f: (T) -> R): Call<R> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <R> flatMap(f: (T) -> Call<R>): Call<R> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}