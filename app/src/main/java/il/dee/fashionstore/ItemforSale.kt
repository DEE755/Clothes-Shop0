package il.dee.fashionstore

import android.content.res.Resources

class ItemforSale(
    val description: String,
    val price: Double,
    val color: String,
    val gender: String,
    val brand: Brand,
    val photo: String,
    var imageResId: Int = 0,
    resources: Resources,
    packageName: String,
    vararg val availlable_sizes: Size
) {

    var isInbag: Boolean = false

    companion object {
        var list_of_clothes = mutableListOf<ItemforSale>()
        var buying_bag_list = mutableListOf<ItemforSale>()

        var totalPrice: Double = 0.0

        fun List<ItemforSale>.bagToString(): String {
            return this.joinToString { it.description }
        }
    }

    fun getListOfClothes(): List<ItemforSale> {
        return list_of_clothes
    }

    fun addToBag() {
        buying_bag_list.add(this)
        totalPrice += this.price
        this.isInbag=true
    }

    fun removeFromBag() {
        buying_bag_list.remove(this)
        totalPrice -= this.price
        this.isInbag=false
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
        this.imageResId = resources.getIdentifier(this.photo, "drawable", packageName)
        list_of_clothes.add(this)
    }

    enum class Size {
        XS, S, M, L, XL, XXL
    }
}