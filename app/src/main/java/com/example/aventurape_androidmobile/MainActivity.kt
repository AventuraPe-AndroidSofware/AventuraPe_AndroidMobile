package com.example.aventurape_androidmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.aventurape_androidmobile.navigation.Navigation
import com.example.aventurape_androidmobile.ui.theme.AventuraPe_AndroidMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AventuraPe_AndroidMobileTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    val navController= rememberNavController()
    Scaffold (
        bottomBar ={
            //BottomNavigationAdventurer(navController)
        }
    ){
        padding->
        Box (
            modifier=Modifier.padding(padding)
                .fillMaxSize()
        ){
            Navigation(navController= navController)
        }
    }
}