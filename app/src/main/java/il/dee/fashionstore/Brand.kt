package il.dee.fashionstore


// Brand class not fully used in the drill but useful for future implementations
class Brand(
    val name: String,
    val logo: String,
    val description: String
) {
    companion object {
        val list_of_brands = mutableListOf<Brand>()
    }

    init {
        list_of_brands.add(this)
    }

    override
    fun toString(): String {
        return this.name
    }
}