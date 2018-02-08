package com.danielecampogiani.funfit

fun <A, B> Call<A>.map(f: (A) -> B): Call<B> = MapCall(this, f)

fun <A, B> Call<A>.flatMap(f: (A) -> Call<B>): Call<B> = FlatMapCall(this, f)