package com.kalex.adviceapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.kalex.adviceapp.presentation.ui.Advice
import com.kalex.adviceapp.presentation.ui.GetAdviceButton
import com.kalex.adviceapp.presentation.ui.theme.AdviceAppTheme
import com.kalex.adviceapp.presentation.viewmodels.AdviceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AdviceAppTheme {
                var result by mutableStateOf("")
                val adviceViewModel: AdviceViewModel by viewModels()
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
                            text = resources.getString(R.string.Advide_title),
                            fontSize = 10.em,
                            modifier = Modifier.padding(10.dp),
                            fontWeight = Bold,
                        )

                        Advice(result)
                        GetAdviceButton( adviceViewModel) {
                            result = it
                        }
                    }

                }
            }
        }
    }
}

