package il.dee.fashionstore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.time.delay
import java.time.Duration


data class CarouselItem(val imageResId: Int, val description: String)


//CLOTHE DIALOG COMPOSABLE

@Composable
fun ClotheDialog(carouselItemsMap: Map<CarouselItem, Clothe>) {
    var showDialog by remember { mutableStateOf(false) }
    var showValPopup by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<CarouselItem?>(null) }

    println(carouselItemsMap)
    val dummie_clothe=Clothe("Unknown",0.0, "Unknown", "Unknown", Brand("Unknown", "Unknown", "Unknown"), "Unknown", Clothe.Size.XS)

    var selectedClothe =carouselItemsMap.get(selectedItem) ?: dummie_clothe

    Carousel(items = carouselItemsMap.keys.toList(), onItemClick = { showDialog = true })

    if (showDialog) {
        CustomDialog(
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false; selectedClothe.addToBag(); showValPopup = true },
            clothe = selectedClothe
        )
    }

    if (showValPopup) {
        ValPopup({ showValPopup = false}, selectedClothe.name)
    }
}
//CARROUSSEL

@Composable
fun CarouselItemView(item: CarouselItem, onClick: (CarouselItem) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable{onClick(item)}
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.description,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = item.description,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(top = 8.dp, start = 60.dp, end = 60.dp)
        )
    }
}
@Composable
fun Carousel(items: List<CarouselItem>, onItemClick: (CarouselItem) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { item ->
            CarouselItemView(item = item, onClick = { onItemClick(item) })
        }
    }
}

//BUTTON COMPOSABLE

@Composable
fun CheckoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 520.dp, start = 200.dp)
    ) {
        Text(text = "Checkout")
    }
}

//DIALOG COMPOSABLE

@Composable
fun CustomDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, clothe: Clothe) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Add to the bag")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("X")
            }
        },
        title = {
            Text(text = clothe.name)
        },
        text = {
            Text(clothe.formatDescription())
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}


//VALIDATION POPUP COMPOSABLE

@Composable
fun ValPopup(onDismiss: () -> Unit, art_name:String) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(Duration.ofMillis(2500))
        onDismiss()
    }

    Dialog(
        onDismissRequest = { showDialog = false },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("${art_name} has been added to your bag!")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}











