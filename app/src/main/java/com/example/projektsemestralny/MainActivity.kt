// W pliku: MainActivity.kt
package com.example.projektsemestralny

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.projektsemestralny.ui.navigation.NavGraph
import com.example.projektsemestralny.ui.theme.ProjektSemestralnyTheme
import com.example.projektsemestralny.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSemestralnyTheme {
                // MainActivity tylko wywołuje NavGraph
                NavGraph(homeViewModel = homeViewModel)
            }
        }
    }
}
