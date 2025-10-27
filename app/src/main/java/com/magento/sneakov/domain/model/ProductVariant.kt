package com.magento.sneakov.domain.model

data class ProductVariant(
    val id: Int,
    val sku: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val size: String?,
    val color: String?
)
