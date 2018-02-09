FunFit
=======================================

A Retrofit 2 (Experimental) `CallAdapter.Factory` bringing map and flatmap to calls.

Usage
-----

Add `CallAdapterFactory` as a `Call` adapter when building your `Retrofit` instance:
```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://example.com/")
    .addCallAdapterFactory(CallAdapterFactory())
    .build()
```

Your service methods can now use `com.danielecampogiani.funfit.Call ` as their return type.
```kotlin
import com.danielecampogiani.funfit.Call

interface GitHubAPI {

    @GET("users/{username}")
    fun getUser(@Path("username") userName: String): Call<User>
    
    @GET
    fun getFollowers(@Url url: String): Call<List<User>>
}
```
Your can now map 

```kotlin
    api.getUser("dcampogiani").map { it.login.toUpperCase() }
```

And flatmap
```kotlin
    api.getUser("dcampogiani").flatMap {
            api.getFollowers(it.followersUrl)
        }
```


Thanks
-----

* Jake Wharton and Square for their [open](https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter) [source](https://github.com/square/retrofit/tree/master/retrofit-adapters) adapters
* [azanin](https://github.com/azanin) and [al333z](https://github.com/al333z) for teaching me functional programming
