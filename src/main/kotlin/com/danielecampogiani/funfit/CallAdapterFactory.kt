package com.danielecampogiani.funfit

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = CallAdapterFactory()
    }

    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {
        val parameterizedReturnType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        return ResponseCallAdapter<Any>(parameterizedReturnType)
    }
}

private class ResponseCallAdapter<T>(
        private val responseType: Type
) : CallAdapter<T, Call<T>> {

    override fun responseType() = responseType

    override fun adapt(call: retrofit2.Call<T>): Call<T> = SimpleCall(call)


}