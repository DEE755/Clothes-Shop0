package il.dee.fashionstore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import il.dee.fashionstore.ui.theme.FashionStoreExerciceTheme
import il.dee.fashionstore.ui.theme.amoriaFontFamily
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FashionStoreExerciceTheme {
                //creating brands objects (not fully used in the drill but useful for future implementations)
                val brand1 = Brand(
                    "Zara",
                    "zara_logo",
                    ""
                )
                val brand2 = Brand(
                    "H&M",
                    "hm_logo",
                    ""
                )
                val brand3 = Brand(
                    "Gap",
                    "gap_logo",
                    ""
                )
                val brand4 = Brand(
                    "Forever 21",
                    "forever21_logo",
                    ""
                )

                //creating clothes objects

                val clothe1 = ItemforSale(
                    "Flower Dress",
                    100.0,
                    "White",
                    "Man",
                    brand1,
                    "clothe1",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.S,
                    ItemforSale.Size.M,
                    ItemforSale.Size.XXL
                )
                val clothe2 = ItemforSale(
                    "Scrapped Jeans",
                    200.0,
                    "Blue",
                    "Man",
                    brand2,
                    "clothe2",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.S
                )
                val clothe3 = ItemforSale(
                    "Nice Shirt",
                    199.99,
                    "Blue",
                    "Man",
                    brand4,
                    "clothe3",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.S
                )
                val clothe4 = ItemforSale(
                    "Elegant Coat",
                    199.99,
                    "Blue",
                    "Man",
                    brand3,
                    "clothe4",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.XS,
                    ItemforSale.Size.XL
                )
                val clothe5 = ItemforSale(
                    "Casual Jeans",
                    50.0,
                    "Gray",
                    "Woman",
                    brand1,
                    "clothe5",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.S,
                    ItemforSale.Size.M,
                    ItemforSale.Size.L,
                    ItemforSale.Size.XXL
                )
                val clothe6 = ItemforSale(
                    "Winter Coat",
                    289.90,
                    "Red",
                    "Woman",
                    brand2,
                    "clothe6",
                    0,
                    resources,
                    packageName,
                    ItemforSale.Size.L,
                    ItemforSale.Size.XL
                )

                var isPumped by remember { mutableStateOf(false) } //used to animate the text
                val color by animateColorAsState(
                    targetValue = if (isPumped) Color.Red else Color(0xFFE84FFC),
                    animationSpec = tween(durationMillis = 1000)
                )

                val fontSize by animateDpAsState(
                    targetValue = if (isPumped) 36.dp else 34.dp,
                    animationSpec = tween(durationMillis = 500)
                )

                var filteredClothes by remember { mutableStateOf(listOf(clothe1, clothe2, clothe3, clothe4, clothe5, clothe6)) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(//Hardcoded text because name of the brand and its slogan should stay in english
                                    text = if (isPumped) "The Clothes Shop" else "The best place to buy your clothes",
                                    style = TextStyle(
                                        fontFamily = amoriaFontFamily,
                                        fontSize = if (isPumped) fontSize.value.sp else 20.sp,
                                        textAlign = TextAlign.Center,
                                        color = color,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    modifier = Modifier.padding(end = 20.dp)
                                )
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 55.dp, start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {//filtering the clothes according to gender and preventing duplicates
                        FilterClothes(onFilteredClothes = { newFilteredClothes ->
                            val uniqueClothes = newFilteredClothes.distinctBy { it.description }
                            filteredClothes = uniqueClothes
                        })//displaying the carousel
                        MainScreen(carouselItemsList = filteredClothes)
                        var showDialog by remember { mutableStateOf(false) }

                        //displaying the checkout button
                        CheckoutButton(onClick = { showDialog = true })
                        if (showDialog) {
                            val context = LocalContext.current
                            CheckoutDialog(
                                onDismiss = { showDialog = false },
                                onConfirm = {
                                    showDialog = false
                                        GeneralFunctions.gotoPaymentActivity(context)
                                }
                            )
                        }
                    }
                }//animating the text
                LaunchedEffect(Unit) {
                    while (true) {
                        isPumped = !isPumped
                        delay(2000)
                    }
                }
            }
        }
    }
}