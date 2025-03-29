package il.dee.fashionstore

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