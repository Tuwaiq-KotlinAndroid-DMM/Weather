package sa.edu.twuaiq.weather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sa.edu.twuaiq.weather.model.WeatherModel

interface IWeatherApi {

    /***
     * REQUEST METHOD
    Every method must have an HTTP annotation that provides the request method and relative URL.
    There are eight built-in annotations: HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD.
    The relative URL of the resource is specified in the annotation.
     * */

    /***
     * Query parameters are added with the @Query annotation on a method parameter.
     * They are automatically added at the end of the URL.
     * */

    @GET("/data/2.5/weather?appid=0714414e3a9a082a2d80bf056a58e457&units=metric")

    /***
     * Suspend Function In Kotlin
    Suspend function is a function that could be started, paused, and resume.
    One of the most important points to remember about the suspend functions is that they are only allowed to be called from a coroutine or another suspend function
     * */

    // Use suspend to give the function the ability to be concurrent with other suspend function
    suspend fun getWeather(
        @Query("q") city: String
    ) : Response<WeatherModel>
}