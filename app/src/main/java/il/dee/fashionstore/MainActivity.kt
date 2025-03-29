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
                /*var itemNb = 0
                for (clothe in Clothe.list_of_clothes) {
                    val temp_carousel_item = CarouselItem(resources.getIdentifier("clothe$itemNb", "drawable", packageName), clothe.name)
                    itemNb++
                }
                */

                val brand1 = Brand("Zara", "zara_logo", "Zara is a Spanish apparel retailer based in Arteixo in Galicia. The company specializes in fast fashion, and products include clothing, accessories, shoes, swimwear, beauty, and perfumes.")
                val brand2 = Brand("H&M", "hm_logo", "H&M is a Swedish multinational clothing-retail company known for its fast-fashion clothing for men, women, teenagers, and children.")
                val brand3 = Brand("Gap", "gap_logo", "Gap is an American worldwide clothing and accessories retailer.")
                val brand4 = Brand("Forever 21", "forever21_logo", "Forever 21 is an American fast fashion retailer headquartered in Los Angeles, California.")

                val clothe1 = Clothe("Flower Dress", 100.0, "White", "Man", brand1, "clothe1", Clothe.Size.S, Clothe.Size.M, Clothe.Size.XXL)
                val clothe2 = Clothe("Scrapped Jeans", 200.0, "Blue", "Man", brand2, "clothe2", Clothe.Size.S)
                val clothe3 = Clothe("Nice Shirt", 199.99, "Blue", "Man", brand4, "clothe3", Clothe.Size.S)
                val clothe4 = Clothe("Elegant Coat", 199.99, "Blue", "Man", brand3, "clothe4", Clothe.Size.XS, Clothe.Size.XL)
                val clothe5 = Clothe("Casual Jeans", 50.0, "Gray", "Woman", brand1, "clothe5", Clothe.Size.S, Clothe.Size.M, Clothe.Size.L, Clothe.Size.XXL)
                val clothe6 = Clothe("Winter Coat", 289.90, "Red", "Woman", brand2, "clothe6", Clothe.Size.L, Clothe.Size.XL)

                //val carouselItems = Clothe.list_of_clothes.map { it.toCarouselItem(resources, packageName) }.toMutableList()

                val clotheList = Clothe.list_of_clothes
                val carouselItems = clotheList.map { it.toCarouselItem(resources, packageName) }

                val carouselToClotheMap0 = carouselItems.zip(Clothe.list_of_clothes).toMap()


                val clotheToCarouselMap = clotheList.zip(carouselItems).toMap()

                val carouselToClotheMap = mutableMapOf<CarouselItem, Clothe>()
                carouselToClotheMap[clothe1.toCarouselItem(resources, packageName)] = clothe1
                carouselToClotheMap[clothe2.toCarouselItem(resources, packageName)] = clothe2
                carouselToClotheMap[clothe3.toCarouselItem(resources, packageName)] = clothe3
                carouselToClotheMap[clothe4.toCarouselItem(resources, packageName)] = clothe4
                carouselToClotheMap[clothe5.toCarouselItem(resources, packageName)] = clothe5
                carouselToClotheMap[clothe6.toCarouselItem(resources, packageName)] = clothe6

                Scaffold(
                    topBar = { // title on the top of the screen
                        TopAppBar(
                            title = { Text("The Clothes Shop", style = TextStyle(fontFamily = amoriaFontFamily, fontSize = 32.sp, textAlign = TextAlign.Center, color = Color(0xFFE84FFC)), textDecoration = TextDecoration.Underline) }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {//the carrousel place
                        ClotheDialog(carouselItemsMap = carouselToClotheMap)
                        CheckoutButton(onClick = { /* TODO CREATE NEW ACTIVITY FOR CHECKING OUT */ })
                    }
                }
            }
        }
    }
}