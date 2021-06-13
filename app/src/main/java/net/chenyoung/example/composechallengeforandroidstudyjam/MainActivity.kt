package net.chenyoung.example.composechallengeforandroidstudyjam

import android.os.Bundle
import androidx.compose.foundation.Image
import com.google.accompanist.coil.rememberCoilPainter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import net.chenyoung.example.composechallengeforandroidstudyjam.ui.theme.ComposeChallengeForAndroidStudyJamTheme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

    @ExperimentalUnitApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChallengeForAndroidStudyJamTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppContent(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchByName()
    }
}

@ExperimentalFoundationApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@Composable
fun AppContent(viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            SearchAppBar(viewModel.query.value, viewModel) {
                viewModel.query.value = it
            }
        }
    ) {
        SearchResultContent(viewModel.cocktails.value)
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    viewModel: MainViewModel,
    onQueryChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {
        Row {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query,
                onValueChange = { onQueryChanged(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.searchByName(query)
                        keyboardController?.hideSoftwareKeyboard()
                    },
                ),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalUnitApi
@Composable
fun SearchResultContent(
    items: List<Cocktail>?
) {
    if (items != null) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
        ) {
            items(items.size) {
                ItemCard(cocktail = items[it])
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LinearProgressIndicator()
        }
    }
}

@ExperimentalUnitApi
@Composable
fun ItemCard(cocktail: Cocktail) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, start = 3.dp, end = 3.dp)
            .wrapContentWidth(),
        elevation = 8.dp
    ) {
        Column() {
            Image(
                painter = rememberCoilPainter(cocktail.image),
                contentDescription = cocktail.name,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = cocktail.name,
                    fontSize = TextUnit(10f, TextUnitType.Sp)
                )
                if (cocktail.category != null) {
                    Text(
                        text = cocktail.category,
                        fontSize = TextUnit(8f, TextUnitType.Sp)
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalUnitApi
@Preview
@Composable
fun SearchResultContentPreview() {
    SearchResultContent(
        listOf(
            Cocktail("12764", "Karsk", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"),
            Cocktail(
                "17835",
                "Abilene",
                "drawable://" + R.drawable.cocktail_image,
                "Ordinary Drink"
            ),
            Cocktail("17833", "A. J.", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"),
        )
    )
}

@ExperimentalUnitApi
@Preview
@Composable
fun ItemCardPreview() {
    ItemCard(
        cocktail = Cocktail(
            "12764",
            "Karsk",
            "drawable://" + R.drawable.cocktail_image,
            "Ordinary Drink"
        )
    )
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun SearchAppBarPreview() {
    SearchAppBar("margarita", viewModel()) {

    }
}