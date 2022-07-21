package com.example.composepitfalls

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composepitfalls.ui.theme.ComposePitFallsTheme

var counter = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var itemModel by remember {
                mutableStateOf(
                    ItemModel(
                        "someId",
                        "someTitle",
                        "someSubtitle"
                    )
                )
            }

            ComposePitFallsTheme {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SomeCompose(
                        itemModel = itemModel,
                        onClick = { id ->
                            // we want to do something with the id here,
                            // for example does not matter what, we simply log it
                            Log.d("TEST", "id that we want to perform the action $id")
                        }
                    )
                    Button(
                        onClick = {
                            counter++
                            itemModel = itemModel.copy(subTitle = "someSubtitle $counter")
                        },
                        content = { Text(text = "Click me") })
                }
            }
        }
    }
}

@Composable
fun SomeCompose(itemModel: ItemModel, onClick: (String) -> Unit) {
    Log.d("TEST", "SomeCompose is composed")
    Column {
        //TODO: This wil cause the recomposition, when itemModel changes,
        // even though title stays the same, because of the lambda creation
        SomeTitleWidget(someTitle = itemModel.title, onTitleClick = {
            // we want to propagate an id of the item clicked to a upper composable
            // we cannot do it inside SomeTitleWidget because it takes someTitle
            // as a String only, therefore it has not reference to a id
            onClick(itemModel.id)
        })
        SomeSubtitleWidget(itemModel.subTitle)
    }
}

@Composable
fun SomeTitleWidget(someTitle: String, onTitleClick: () -> Unit) {
    Log.d("TEST", "SomeTitleWidget is composed")
    Text(text = someTitle, modifier = Modifier.clickable { onTitleClick() })
}

@Composable
fun SomeSubtitleWidget(someSubtitle: String) {
    Log.d("TEST", "SomeSubtitleWidget is composed")
    Text(text = someSubtitle)
}

data class ItemModel(val id: String, val title: String, val subTitle: String)

