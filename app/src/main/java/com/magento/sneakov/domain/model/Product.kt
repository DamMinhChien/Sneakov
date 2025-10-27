package com.magento.sneakov.domain.model

data class Product(
    val id: Int,
    val sku: String,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val childIds: List<Int> = emptyList(),
    var priceRange: Pair<Double, Double>? = null
)
