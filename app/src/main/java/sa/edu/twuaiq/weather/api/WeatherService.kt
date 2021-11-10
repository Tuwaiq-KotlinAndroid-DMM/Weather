package sa.edu.twuaiq.weather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org"
object WeatherService {

    /***
     *
     * To work with Retrofit you basically need the following three classes:

    Model class which is used as a JSON model

    Interfaces that define the possible HTTP operations

    Retrofit.Builder class - Instance which uses the interface and the Builder API to allow defining the URL end point for the HTTP operations.
     * */

    // Retrofit.Builder
    // And we need to specify a factory for deserializing the response using the Gson library

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //  Builder API
    private val weatherApi = retrofitService.create(IWeatherApi::class.java)

    suspend fun getWeather(city: String)  =  weatherApi.getWeather(city)

}