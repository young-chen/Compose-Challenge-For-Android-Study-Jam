package net.chenyoung.example.composechallengeforandroidstudyjam

import android.os.Bundle
import androidx.compose.foundation.Image
import com.google.accompanist.coil.rememberCoilPainter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import net.chenyoung.example.composechallengeforandroidstudyjam.ui.theme.ComposeChallengeForAndroidStudyJamTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

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
}

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
                    .fillMaxWidth(.9f)
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

@Composable
fun SearchResultContent(
    items: List<Cocktail>?
) {
    if (items != null) {
        LazyColumn {
            itemsIndexed(
                items = items
            ) { index, item ->
                ItemCard(cocktail = item)
            }
        }
    }
}

@Composable
fun ItemCard(cocktail: Cocktail) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 1.dp, bottom = 1.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Image(
            painter = rememberCoilPainter(cocktail.image),
            contentDescription = cocktail.name,
            modifier = Modifier.scale(1.0f, 1.0f)
        )
        Column {
            Text(text = cocktail.name)
            if (cocktail.category != null) {
                Text(text = cocktail.category)
            }
        }
    }
}

@Preview
@Composable
fun SearchResultContentPreview() {
    SearchResultContent(
        listOf(
            Cocktail("12764", "Karsk", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"),
            Cocktail("17835", "Abilene", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"),
            Cocktail("17833", "A. J.", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"),
        )
    )
}

@Preview
@Composable
fun ItemCardPreview() {
    ItemCard(cocktail = Cocktail("12764", "Karsk", "drawable://" + R.drawable.cocktail_image, "Ordinary Drink"))
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun SearchAppBarPreview() {
    SearchAppBar("margarita", viewModel()) {

    }
}