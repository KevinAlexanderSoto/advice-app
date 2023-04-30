package com.kalex.adviceapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalex.adviceapp.R
import com.kalex.adviceapp.presentation.viewmodels.AdviceViewModel

@Composable
fun Advice(name: String) {
    Text(modifier = Modifier.padding(15.dp), text = name)
}

@Composable
fun GetAdviceButton(
    adviceViewModel: AdviceViewModel,
    function: (String) -> Unit,
) {
    val context = LocalContext.current
    Button(
        onClick = {
            adviceViewModel.getAdvice()
        },
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth(0.8f),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(23.dp),
        contentPadding = PaddingValues(12.dp),

    ) {
        Text(
            text = context.resources.getString(R.string.Get_advice_button_title),
            fontSize = 20.sp,

        )
        if (!adviceViewModel.advice.value.isLoading) {
            function(adviceViewModel.advice.value.Advice)
        }
        // INTERNET ERROR
        if (adviceViewModel.advice.value.isError) {
            Toast.makeText(
                context,
                adviceViewModel.advice.value.Error,
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}
