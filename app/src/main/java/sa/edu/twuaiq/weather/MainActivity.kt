package sa.edu.twuaiq.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sa.edu.twuaiq.weather.api.WeatherService

class MainActivity : AppCompatActivity() {

    // Views declaration
    private lateinit var tempTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windSpeedTextView: TextView
    private lateinit var cityEditText: EditText
    private lateinit var weatherImageView: ImageView
    private lateinit var weatherButton: ImageButton
    private lateinit var informationLayout: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views assignment
        tempTextView = findViewById(R.id.temp_textview)
        humidityTextView = findViewById(R.id.humidity_textview)
        windSpeedTextView = findViewById(R.id.wind_textview)
        cityEditText = findViewById(R.id.city_edittext)
        weatherImageView = findViewById(R.id.weather_image)
        weatherButton = findViewById(R.id.weather_button)
        informationLayout = findViewById(R.id.info_layout)
        progressBar = findViewById(R.id.progressBar)


        weatherButton.setOnClickListener {
            getWeather()
        }
    }


    fun getWeather() {

        val city = cityEditText.text.toString()

        if (city.isNotEmpty() && city.isNotBlank()) {
            informationLayout.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE



            /***
             * Scope in Kotlin’s coroutines can be defined as the restrictions within which the Kotlin coroutines are being executed
             * Scopes help to predict the lifecycle of the coroutines. There are basically 4 scopes in Kotlin coroutines:
             * */

            /***
             * 1. Global Scope
            Global Scope is one of the ways by which coroutines are launched.
            When Coroutines are launched within the global scope, they live long as the application does.
            If the coroutines finish it’s a job, it will be destroyed and will not keep alive until the application dies
             * */

            /**
             * 2. LifeCycle Scope
            The lifecycle scope is the same as the global scope,
            but the only difference is that when we use the lifecycle scope,
            all the coroutines launched within the activity also dies when the activity dies.
             * */

            /***
             * 3. ViewModel Scope
            It is also the same as the lifecycle scope,
            only difference is that the coroutine in this scope will live as long the view model is alive.
             * */

            /***
             * 4. Coroutine Scope
             *  It creates a new scope and does not complete until all children’s coroutines complete.
             *  So we are creating a scope, we are running coroutines and inside the scope,
             *  we can create other coroutines.
             *  This coroutine that starts here does not complete until all the inner coroutines complete as well.
             * */

            CoroutineScope(Dispatchers.IO).launch {
                // Coroutines Dispatchers
                /**
                 * Kotlin coroutines use dispatchers to determine which threads are used for coroutine execution.
                 * To run code outside of the main thread, you can tell Kotlin coroutines to perform work on either the Default or IO dispatcher.
                 * In Kotlin, all coroutines must run in a dispatcher, even when they're running on the main thread.
                 * Coroutines can suspend themselves, and the dispatcher is responsible for resuming them.
                 * */

                // To specify where the coroutines should run, Kotlin provides three dispatchers that you can use:

                /**
                 * Dispatchers.Main -
                 * Use this dispatcher to run a coroutine on the main Android thread.
                 * This should be used only for interacting with the UI and performing quick work.
                 * */

                /***
                 * Dispatchers.IO -
                 * This dispatcher is optimized to perform disk or network I/O outside of the main thread.
                 * Examples include using the Room component, reading from or writing to files,
                 * and running any network operations.
                 * */

                /**
                 * Dispatchers.Default -
                 * This dispatcher is optimized to perform CPU-intensive work outside of the main thread.
                 * */


                // Use try and catch for handling http exceptions
                try {

                    // Calling the API Methods and handles the result
                    val response = WeatherService.getWeather(city)

                    withContext(Dispatchers.Main) {

                        if (response.isSuccessful){

                            response.body()?.run {

                                informationLayout.visibility = View.VISIBLE
                                progressBar.visibility = View.INVISIBLE

                                tempTextView.text = "${main.temp}°"
                                humidityTextView.text = "${main.humidity} %"
                                windSpeedTextView.text = wind.speed.toString()
                                val imageUrl = "https://openweathermap.org/img/wn/${weather[0].icon}@4x.png"

                                Picasso.get().load(imageUrl).into(weatherImageView)
                            }
                        } else {

                            progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (e: Exception) {
                    // Handle Api error here
                }
            }
        }
    }

}