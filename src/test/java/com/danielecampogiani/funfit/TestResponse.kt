package com.danielecampogiani.funfit

import org.junit.Assert.*
import org.junit.Test


class TestResponse {

    val api: GitHubAPI = Retrofit.instance.create(GitHubAPI::class.java)

    @Test
    fun success() {
        val call = api.getStargazers("dcampogiani", "AndroidFunctionalValidation")
        val response = call.execute()

        when (response) {
            is Response.Success -> {
                assertEquals(200, response.code)
                assertTrue(response.body.isNotEmpty())
            }
            is Response.Error -> fail()
        }
    }

    @Test
    fun error() {
        val call = api.getStargazers("dcampogiani", "NotValid")
        val response = call.execute()

        when (response) {
            is Response.Success -> fail()
            is Response.Error -> {
                assertEquals(404, response.code)
            }
        }
    }

    @Test
    fun mapSuccess() {
        val call = api.getStargazers("dcampogiani", "AndroidFunctionalValidation")
        val response = call.execute()
        val mappedResponse = response.map { it.map { it.copy(userName = it.userName.toUpperCase()) } }

        when (mappedResponse) {
            is Response.Success -> {
                assertEquals(200, mappedResponse.code)
                assertEquals("AJOZ", mappedResponse.body.first().userName)
            }
            is Response.Error -> fail()
        }
    }

    @Test
    fun mapError() {
        val call = api.getStargazers("dcampogiani", "NotValid")
        val response = call.execute()
        val mappedResponse = response.map { "Dummy Mapping" }

        when (response) {
            is Response.Success -> fail()
            is Response.Error -> {
                assertEquals(404, mappedResponse.code)
            }
        }
    }

}