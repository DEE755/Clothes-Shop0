package il.dee.fashionstore

import android.content.res.Resources

class CarouselItem(
    val description: String,
    val price: Double,
    val color: String,
    val gender: String,
    val brand: Brand,
    val photo: String,
    var imageResId: Int = 0,
    val ressources: Resources,
    val packageName: String,
    vararg val availlable_sizes: Size
) {

    var isInbag: Boolean = false

    companion object {
        var list_of_clothes = mutableListOf<CarouselItem>()
        var buying_bag_list = mutableListOf<CarouselItem>()

        var totalPrice: Double = 0.0

        fun List<CarouselItem>.bagToString(): String {
            return this.joinToString { it.description }
        }
    }

    fun getListOfClothes(): List<CarouselItem> {
        return list_of_clothes
    }

    fun addToBag() {
        buying_bag_list.add(this)
        totalPrice += this.price
        this.isInbag=true
    }

    fun formatDescription(): String {
        var carr_description = """
        Available Sizes:  ${this.availlable_sizes.joinToString()}
        Price: ${this.price} $
        Color: ${this.color}
        Gender:${this.gender}
        Brand: ${this.brand}
        """
        return carr_description
    }



    init {
        this.imageResId = ressources.getIdentifier(this.photo, "drawable", packageName)
        list_of_clothes.add(this)
    }

    enum class Size {
        XS, S, M, L, XL, XXL
    }
}