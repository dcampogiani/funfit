package com.danielecampogiani.funfit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GitHubAPI {

    @GET("repos/{owner}/{repoName}/stargazers")
    fun getStargazers(@Path("owner") owner: String, @Path("repoName") repoName: String): Call<List<Stargazer>>

    @GET("users/{username}")
    fun getUser(@Path("username") userName: String): Call<User>

    @GET
    fun getFollowers(@Url url: String): Call<List<User>>

}