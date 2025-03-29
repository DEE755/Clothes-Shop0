package il.dee.fashionstore

import android.content.res.Resources

class Clothe(
    val name: String,
    val price: Double,
    val color: String,
    val gender: String,
    val brand: Brand,
    val photo: String,
    vararg val availlable_sizes: Size,


    var isAddedToBag: Boolean = false
) {
    companion object {


        public var list_of_clothes = mutableListOf<Clothe>()

        public var buying_bag_list= mutableListOf<Clothe>()
    }

    init {
        list_of_clothes.add(this)
    }

    public fun getListOfClothes(): List<Clothe> {
        return list_of_clothes
    }



    public fun addToBag() {

        buying_bag_list.add(this)
    }




    enum class Size {
        XS, S, M, L, XL, XXL
    }


    fun toCarouselItem(resources: Resources, packageName: String): CarouselItem
    {
        val carr_photo=resources.getIdentifier(this.photo, "drawable", packageName)

        return CarouselItem(carr_photo, this.name)
    }


    fun formatDescription():String
    {
        var carr_description=""" 
        Available Sizes:  ${this.availlable_sizes.joinToString()}
        Price: ${this.price} $
        Color: ${this.color}
        Gender:${this.gender}
        Brand: ${this.brand}
        """
        return carr_description
    }
}