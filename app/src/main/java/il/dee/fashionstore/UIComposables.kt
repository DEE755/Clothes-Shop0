package il.dee.fashionstore

            import androidx.compose.foundation.Image
            import androidx.compose.foundation.gestures.detectTapGestures
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
            import androidx.compose.ui.input.pointer.pointerInput
            import androidx.compose.ui.platform.LocalContext
            import androidx.compose.ui.res.stringResource
            import androidx.compose.ui.window.Dialog
            import il.dee.fashionstore.ItemforSale.Companion.bagToString
            import kotlinx.coroutines.delay

            @Composable
            fun MainScreen(carouselItemsList: List<ItemforSale>) {
                var showDialog by remember { mutableStateOf(false) }
                var showValPopup by remember { mutableStateOf(false) }
                var selectedItem by remember { mutableStateOf<ItemforSale?>(null) }

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
            fun CarouselItemView(item: ItemforSale, onClick: (ItemforSale) -> Unit, onLongClick: (ItemforSale) -> Unit, isInbag: Boolean = false) {
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
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = { onClick(item) },
                                    onLongPress = {
                                        if (item.isInbag) onLongClick(item)
                                    }
                                )
                            }
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
            fun RemoveConfirmation(onDismiss: () -> Unit, onConfirm: () -> Unit, clothe: ItemforSale?) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    confirmButton = {
                        Button(onClick = onConfirm) {
                            Text(stringResource(R.string.remove_from_bag))
                        }
                    },
                    dismissButton = {
                        Button(onClick = onDismiss) {
                            Text(stringResource(R.string.return_to_shopping))
                        }
                    },
                    title = {
                        Text(stringResource(R.string.removal_confirmation))
                    },
                    text = {
                        Text(stringResource(R.string.remove_confirmation_text, clothe?.description ?: ""))
                    },
                    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
                )
            }

            @Composable
            fun Carousel(items: List<ItemforSale>, selectedItem: ItemforSale?, onItemClick: (ItemforSale) -> Unit) {
                var showRemoveConfirmation by remember { mutableStateOf(false) }
                var itemToRemove by remember { mutableStateOf<ItemforSale?>(null) }
                var showRemovePopup by remember { mutableStateOf(false) }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(items) { item ->
                        CarouselItemView(
                            item = item,
                            onClick = { onItemClick(item) },
                            onLongClick = {
                                itemToRemove = item
                                showRemoveConfirmation = true
                            },
                            isInbag = item.isInbag
                        )
                    }
                }

                if (showRemoveConfirmation) {
                    RemoveConfirmation(
                        onDismiss = { showRemoveConfirmation = false },
                        onConfirm = {
                            itemToRemove?.removeFromBag()
                            showRemoveConfirmation = false
                            showRemovePopup = true
                        },
                        clothe = itemToRemove
                    )
                }

                if (showRemovePopup) {
                    RemovPopup({ showRemovePopup = false }, itemToRemove!!.description)
                }
            }

            @Composable
            fun CheckoutButton(onClick: () -> Unit) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(top = 420.dp, start = 200.dp)
                ) {
                    Text(text = stringResource(R.string.checkout))
                }
            }

            @Composable
            fun ArticleDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, clothe: ItemforSale?) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    confirmButton = {
                        Button(onClick = {onConfirm()}) {
                            Text(stringResource(R.string.add_to_bag))
                        }
                    },
                    dismissButton = {
                        Button(onClick = onDismiss) {
                            Text(stringResource(R.string.close))
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
                LaunchedEffect(Unit) {
                    delay(2000)
                    onDismiss()
                }

                Dialog(
                    onDismissRequest = onDismiss,
                    properties = DialogProperties(dismissOnClickOutside = true)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(stringResource(R.string.added_to_bag, art_name))
                        }
                    }
                }
            }

            @Composable
            fun RemovPopup(onDismiss: () -> Unit, art_name: String) {
                LaunchedEffect(Unit) {
                    delay(2000)
                    onDismiss()
                }

                Dialog(
                    onDismissRequest = onDismiss,
                    properties = DialogProperties(dismissOnClickOutside = true)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(stringResource(R.string.removed_from_bag, art_name))
                        }
                    }
                }
            }

            @Composable
            fun CheckoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    confirmButton = {
                        Button(onClick = onConfirm
                        ) {
                            Text(stringResource(R.string.process_to_payment))
                        }
                    },
                    dismissButton = {
                        Button(onClick = onDismiss) {
                            Text(stringResource(R.string.return_to_shopping))
                        }
                    },
                    title = {
                        Text(text = stringResource(R.string.articles))
                    },
                    text = {
                        Text(stringResource(R.string.total_price, ItemforSale.buying_bag_list.bagToString(), ItemforSale.totalPrice))
                    },
                    properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
                )
            }

            @Composable
            fun FilterClothes(onFilteredClothes: (List<ItemforSale>) -> Unit) {
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
                            Text(stringResource(R.string.men))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isWomenChecked,
                                onCheckedChange = { isWomenChecked = it }
                            )
                            Text(stringResource(R.string.women))
                        }
                    }
                    val filteredClothes = ItemforSale.list_of_clothes.filter {
                        (isMenChecked && it.gender =="Man") || (isWomenChecked && it.gender == "Woman")
                    }

                    onFilteredClothes(filteredClothes)

                    if (filteredClothes.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_clothes_found),
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

