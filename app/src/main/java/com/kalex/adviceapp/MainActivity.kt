package com.kalex.adviceapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kalex.adviceapp.ui.theme.AdviceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserRetroApi::class.java)

        setContent {
            AdviceAppTheme {
                var result by mutableStateOf("")

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),// para hacer scroll
                        verticalArrangement = Arrangement.spacedBy(11.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Advice APP",
                            fontSize = 10.em,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = Bold,
                        )

                        Advice(result)
                        GetAdviceButton(client) {
                            result = it
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Advice(name: String) {
    Text(modifier = Modifier.padding(15.dp), text = name)
}

@Composable
fun GetAdviceButton(client: UserRetroApi, function: (String) -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            client.getUser().enqueue(object : Callback<splip> {
                override fun onResponse(call: Call<splip>, response: Response<splip>) {
                    val adviceResponse = response.body()?.slip
                    function(adviceResponse?.advice.toString())

                }

                override fun onFailure(call: Call<splip>, t: Throwable) {
                    Toast.makeText(
                        context,
                        t.message ?: "ERROR",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        },
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth(0.8f),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(23.dp),
        contentPadding = PaddingValues(12.dp),

        ) {
        Text(
            text = "Get Advice ",
            fontSize = 20.sp,

            )
    }
}

interface UserRetroApi {

    @GET("advice")
    fun getUser(
    ): Call<splip>

}

data class splip(
    val slip: Advicedto = Advicedto("1", "ADVICE 1")
)

data class Advicedto(
    val id: String = "",
    val advice: String = "",
)