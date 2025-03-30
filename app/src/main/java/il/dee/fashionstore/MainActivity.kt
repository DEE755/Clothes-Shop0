// In your MainActivity.kt
package il.dee.fashionstore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import il.dee.fashionstore.ui.theme.FashionStoreExerciceTheme
import il.dee.fashionstore.ui.theme.amoriaFontFamily

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            FashionStoreExerciceTheme {

                val brand1 = Brand("Zara", "zara_logo", "Zara is a Spanish apparel retailer based in Arteixo in Galicia. The company specializes in fast fashion, and products include clothing, accessories, shoes, swimwear, beauty, and perfumes.")
                val brand2 = Brand("H&M", "hm_logo", "H&M is a Swedish multinational clothing-retail company known for its fast-fashion clothing for men, women, teenagers, and children.")
                val brand3 = Brand("Gap", "gap_logo", "Gap is an American worldwide clothing and accessories retailer.")
                val brand4 = Brand("Forever 21", "forever21_logo", "Forever 21 is an American fast fashion retailer headquartered in Los Angeles, California.")

                val clothe1 = CarouselItem("Flower Dress", 100.0, "White", "Man", brand1, "clothe1", 0, resources, packageName, CarouselItem.Size.S, CarouselItem.Size.M, CarouselItem.Size.XXL)
                val clothe2 = CarouselItem("Scrapped Jeans", 200.0, "Blue", "Man", brand2, "clothe2", 0, resources, packageName, CarouselItem.Size.S)
                val clothe3 = CarouselItem("Nice Shirt", 199.99, "Blue", "Man", brand4, "clothe3", 0, resources, packageName, CarouselItem.Size.S)
                val clothe4 = CarouselItem("Elegant Coat", 199.99, "Blue", "Man", brand3, "clothe4", 0, resources, packageName, CarouselItem.Size.XS, CarouselItem.Size.XL)
                val clothe5 = CarouselItem("Casual Jeans", 50.0, "Gray", "Woman", brand1, "clothe5", 0, resources, packageName, CarouselItem.Size.S, CarouselItem.Size.M, CarouselItem.Size.L, CarouselItem.Size.XXL)
                val clothe6 = CarouselItem("Winter Coat", 289.90, "Red", "Woman", brand2, "clothe6", 0, resources, packageName, CarouselItem.Size.L, CarouselItem.Size.XL)


                Scaffold(
                    topBar = { // title on the top of the screen
                        TopAppBar(
                            title = { Text("The Clothes Shop", style = TextStyle(fontFamily = amoriaFontFamily, fontSize = 32.sp, textAlign = TextAlign.Center, color = Color(0xFFE84FFC)), textDecoration = TextDecoration.Underline) }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    var filteredClothes by remember { mutableStateOf(CarouselItem.list_of_clothes) }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {//the carrousel place
                        FilterClothes(onFilteredClothes = { filteredClothes = it.toMutableList() })
                        ClotheDialog(carouselItemsList = filteredClothes)
                        var showDialog by remember { mutableStateOf(false) }
                        CheckoutButton(onClick = { showDialog = true })
                        if (showDialog) {
                            CheckoutDialog(
                                onDismiss = { showDialog = false; },
                                onConfirm = { showDialog = false; }
                            )
                        }
                    }
                }
            }
        }
    }
}