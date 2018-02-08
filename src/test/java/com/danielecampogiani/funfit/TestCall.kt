package com.danielecampogiani.funfit

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test


class TestCall {

    val api: GitHubAPI = Retrofit.instance.create(GitHubAPI::class.java)

    @Test
    fun mapSuccess() {
        val call = api.getUser("dcampogiani").map { it.login.toUpperCase() }
        val response = call.execute()

        when (response) {
            is Response.Success -> {
                assertEquals("DCAMPOGIANI", response.body)
            }
            is Response.Error -> fail()
        }
    }

    @Test
    fun mapError() {
        val call = api.getStargazers("dcampogiani", "NotValid").map { "Dummy Mapping" }
        val response = call.execute()

        when (response) {
            is Response.Success -> fail()
            is Response.Error -> {
                assertEquals(404, response.code)
            }
        }
    }

    @Test
    fun flatMapSuccess() {
        val call = api.getUser("dcampogiani").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()

        when (response) {
            is Response.Success -> {
                assertEquals("mattpoggi", response.body.first().login)
            }
            is Response.Error -> fail()
        }
    }

    @Test
    fun flatMapError() {
        val call = api.getUser("-1").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()

        when (response) {
            is Response.Success -> fail()
            is Response.Error -> {
                assertEquals(404, response.code)
            }
        }
    }
}