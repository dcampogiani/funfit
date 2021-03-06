package com.danielecampogiani.funfit

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.CountDownLatch


class TestCall {

    val api: GitHubAPI = Retrofit.instance.create(GitHubAPI::class.java)

    val dcampogiani = User("dcampogiani", "https://api.github.com/users/dcampogiani/followers")

    @Test
    fun executeSuccess() {
        val call = api.getUser("dcampogiani")
        val response = call.execute()

        response.fold(
                { fail() },
                { assertEquals(dcampogiani, it.body) })

    }

    @Test
    fun executeAsyncSuccess() {
        val latch = CountDownLatch(1)

        val call = api.getUser("dcampogiani")
        call.executeAsync(
                onResponse = { _, response ->
                    response.fold(
                            { fail() },
                            {
                                assertEquals(dcampogiani, it.body)
                                latch.countDown()
                            })
                },
                onFailure = { _, _ ->
                    latch.countDown()
                    fail()
                }
        )

        latch.await()
    }

    @Test
    fun mapSuccess() {
        val call = api.getUser("dcampogiani").map { it.login.toUpperCase() }
        val response = call.execute()

        response.fold(
                { fail() },
                { assertEquals("DCAMPOGIANI", it.body) })

    }

    @Test
    fun mapError() {
        val call = api.getStargazers("dcampogiani", "NotValid").map { "Dummy Mapping" }
        val response = call.execute()

        response.fold(
                { assertEquals(404, response.code) },
                { fail() })
    }

    @Test
    fun flatMapSuccess() {
        val call = api.getUser("dcampogiani").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()

        response.fold(
                { fail() },
                { assertEquals("mattpoggi", it.body.first().login) })

    }

    @Test
    fun flatMapError() {
        val call = api.getUser("-1").flatMap {
            api.getFollowers(it.followersUrl)
        }
        val response = call.execute()

        response.fold(
                { assertEquals(404, response.code) },
                { fail() })
    }
}