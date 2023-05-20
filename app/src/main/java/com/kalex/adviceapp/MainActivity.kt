package com.kalex.adviceapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.kalex.adviceapp.presentation.composable.BottomSheetLayout
import com.kalex.adviceapp.presentation.ui.Advice
import com.kalex.adviceapp.presentation.ui.GetAdviceButton
import com.kalex.adviceapp.theme.AdviceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdviceAppTheme {
                var showSheet by remember { mutableStateOf(false) }
                var hideSheet by remember { mutableStateOf(false) }
                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(
                        initialValue = BottomSheetValue.Collapsed,
                    ),
                )
                var result by mutableStateOf("")
                BottomSheetLayout(
                    scaffoldState = scaffoldState,
                    scaffoldContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()), // para hacer scroll
                            verticalArrangement = Arrangement.spacedBy(11.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = resources.getString(R.string.Advide_title),
                                fontSize = 10.em,
                                modifier = Modifier.padding(10.dp),
                                fontWeight = Bold,
                            )

                            Advice(result)
                            GetAdviceButton() {
                                result = it
                            }
                            Button(onClick = { showSheet = true }) {
                                Text("Show Bottom sheet")
                            }
                        }
                    },
                    sheetContent = {
                        SheetContent {
                            hideSheet = true
                        }
                    },
                    showBottomSheet = { if (showSheet) it() },
                    onBottomSheetHide = { hideSheet = false },
                    hideBottomSheet = { if (hideSheet) it.invoke() },
                    onBottomSheetShow = { showSheet = false },
                )
            }
        }
    }
}

@Composable
fun SheetContent(
    onHideClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(128.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text("Swipe up to expand sheet")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sheet content")
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onHideClick() },
        ) {
            Text("Click to collapse sheet")
        }
    }
}
