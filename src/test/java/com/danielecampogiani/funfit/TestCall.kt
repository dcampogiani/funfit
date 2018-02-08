package com.danielecampogiani.funfit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class TestCall {

    val api: GitHubAPI = Retrofit.instance.create(GitHubAPI::class.java)

    @Test
    fun mapSuccess() {
        val call = api.getUser("dcampogiani").map { it.login.toUpperCase() }
        val response = call.execute()
        val successfulResponse = response as Response.Success<String>
        assertEquals("DCAMPOGIANI", successfulResponse.body)
    }

    @Test
    fun mapError() {
        val call = api.getStargazers("dcampogiani", "NotValid").map { "Dummy Mapping" }
        val response = call.execute()
        val responseIsError = response is Response.Error
        assertTrue(responseIsError)
        assertEquals(404, response.code)
    }

    @Test
    fun flatMapSuccess() {
        val call = api.getUser("dcampogiani").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()
        val successfulResponse = response as Response.Success<List<User>>

        assertEquals("mattpoggi", successfulResponse.body.first().login)
    }

    @Test
    fun flatMapError() {
        val call = api.getUser("-1").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()
        val responseIsError = response is Response.Error
        assertTrue(responseIsError)
        assertEquals(404, response.code)
    }
}