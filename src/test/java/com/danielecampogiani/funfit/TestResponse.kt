package com.danielecampogiani.funfit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class TestResponse {

    val api: GitHubAPI = Retrofit.instance.create(GitHubAPI::class.java)

    @Test
    fun success() {
        val call = api.getStargazers("dcampogiani", "AndroidFunctionalValidation")
        val response = call.execute()
        response as Response.Success<List<Stargazer>>
        assertEquals(200, response.code)
        assertTrue(response.body.isNotEmpty())
    }

    @Test
    fun error() {
        val call = api.getStargazers("dcampogiani", "NotValid")
        val response = call.execute()
        val responseIsError = response is Response.Error
        assertTrue(responseIsError)
        assertEquals(404, response.code)
    }

    @Test
    fun mapSuccess() {
        val call = api.getStargazers("dcampogiani", "AndroidFunctionalValidation")
        val response = call.execute()
        val mappedResponse = response.map { it.map { it.copy(userName = it.userName.toUpperCase()) } }
        mappedResponse as Response.Success<List<Stargazer>>
        assertEquals(200, mappedResponse.code)
        assertEquals("AJOZ", mappedResponse.body.first().userName)
    }

    @Test
    fun mapError() {
        val call = api.getStargazers("dcampogiani", "NotValid")
        val response = call.execute()
        val mappedResponse = response.map { "Dummy Mapping" }
        val responseIsError = response is Response.Error
        val mappedResponseIsError = mappedResponse is Response.Error
        assertTrue(mappedResponseIsError)
        assertTrue(responseIsError)
        assertEquals(404, mappedResponse.code)
        assertEquals(404, response.code)
    }

}