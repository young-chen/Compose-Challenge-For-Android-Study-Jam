package net.chenyoung.example.composechallengeforandroidstudyjam

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import net.chenyoung.example.composechallengeforandroidstudyjam.ui.theme.ComposeChallengeForAndroidStudyJamTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengeForAndroidStudyJamTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android", viewModel)
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String, viewModel: MainViewModel) {
    viewModel.searchByCategory()
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeChallengeForAndroidStudyJamTheme {
        Greeting("Android", MainViewModel())
    }
}