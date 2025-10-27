package com.magento.sneakov.data.remote.dto

data class SearchRespondDto(
    val items: List<ProductDto>?,
    val total_count: Int
)

data class ProductDto(
    val id: Int,
    val sku: String,
    val name: String,
    val price: Double,
    val custom_attributes: List<CustomAttribute>?,
    val extension_attributes: ExtensionAttributes?,
)

data class CustomAttribute(
    val attribute_code: String,
    val value: Any?
)

data class ExtensionAttributes(
    val configurable_product_links: List<Int>?
)