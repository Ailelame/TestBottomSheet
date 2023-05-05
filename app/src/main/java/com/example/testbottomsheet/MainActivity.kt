package com.example.testbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testbottomsheet.ui.theme.TestBottomSheetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(it)
                    ) {
                        // Bar that expand to 100% height
                        SystemBarComposable()

                        // NavHost and screen with the bottom sheet layout
                        val navHostController = rememberNavController()
                        NavHost(navController = navHostController, startDestination = "home") {
                            composable(route = "home") {
                                HomeScreen()
                            }
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun SystemBarComposable() {
    var expanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = expanded, label = "dropdownTransition")

    val height = transition.animateDp(
        transitionSpec = { tween(durationMillis = 250) },
        label = "height"
    ) { isExpanded ->
        if (isExpanded)
            LocalConfiguration.current.screenHeightDp.dp //- 1.dp
        else
            24.dp
    }

    Column(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .background(Color.Black)
            .height(height.value)
            .padding(start = 8.dp, end = 8.dp)
            .clickable {
                expanded = !expanded
            }, verticalArrangement = Arrangement.Top) {
        Text(text = "Click me to expand", color = Color.White)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                Modifier.fillMaxHeight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "BottomSheet")
            }
        },
        sheetState = modalState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "TestScreen")
        }
    }
}

