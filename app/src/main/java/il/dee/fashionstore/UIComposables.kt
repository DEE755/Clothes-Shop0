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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import il.dee.fashionstore.CarouselItem.Companion.bagToString
import kotlinx.coroutines.time.delay
import java.time.Duration





@Composable
fun ClotheDialog(carouselItemsList: List<CarouselItem>) {
    var showDialog by remember { mutableStateOf(false) }
    var showValPopup by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<CarouselItem?>(null) }

    Carousel(items = carouselItemsList, selectedItem = selectedItem, onItemClick = { item ->
        selectedItem = item
        showDialog = true
    })

    if (showDialog) {
        ArticleDialog(
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false; selectedItem?.addToBag(); showValPopup = true },
            clothe = selectedItem
        )
    }

    if (showValPopup) {
        ValPopup({ showValPopup = false }, selectedItem!!.description)
    }
}

@Composable
fun CarouselItemView(item: CarouselItem, onClick: (CarouselItem) -> Unit, isInbag: Boolean = false) {
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
                .clickable { onClick(item) }
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.description,
                modifier = Modifier.fillMaxSize()
            )

            if (isInbag) {
                Image(
                    painter = painterResource(id = R.drawable.bagicon),
                    contentDescription = "Selected",
                    modifier = Modifier.fillMaxSize()
                )
            }
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
fun Carousel(items: List<CarouselItem>, selectedItem: CarouselItem?, onItemClick: (CarouselItem) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items) { item ->
            CarouselItemView(
                item = item,
                onClick = { onItemClick(item) },
                isInbag = item.isInbag
            )
        }
    }
}

@Composable
fun CheckoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 420.dp, start = 200.dp)
    ) {
        Text(text = "Checkout")
    }
}

@Composable
fun ArticleDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, clothe: CarouselItem?) {
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
            Text(text = clothe!!.description)
        },
        text = {
            Text(clothe!!.formatDescription())
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}

@Composable
fun ValPopup(onDismiss: () -> Unit, art_name: String) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(Duration.ofMillis(2000))
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

            }
        }
    }
}

@Composable
fun CheckoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Process to Payment")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Return to Shopping")
            }
        },
        title = {
            Text(text = "Those are your articles: ")
        },
        text = {
            Text("${CarouselItem.buying_bag_list.bagToString()} \n Total Price: ${CarouselItem.totalPrice} $")
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}


//FILTERS

@Composable
fun FilterClothes(onFilteredClothes: (List<CarouselItem>) -> Unit) {
    var isMenChecked by remember { mutableStateOf(true) }
    var isWomenChecked by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isMenChecked,
                    onCheckedChange = { isMenChecked = it }
                )
                Text("Men")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isWomenChecked,
                    onCheckedChange = { isWomenChecked = it }
                )
                Text("Women")
            }


        }
        val filteredClothes = CarouselItem.list_of_clothes.filter {
            (isMenChecked && it.gender == "Man") || (isWomenChecked && it.gender == "Woman")
        }

        onFilteredClothes(filteredClothes)

        if (filteredClothes.isEmpty()) {
            Text(
                text = "No clothes found",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}